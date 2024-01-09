package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Itm01Item;
import kr.co.kfs.assetedu.model.Jnl01Journal;
import kr.co.kfs.assetedu.model.Jnl02JournalTmp;
import kr.co.kfs.assetedu.model.Jnl13TrMap;
import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Itm01ItemRepository;
import kr.co.kfs.assetedu.repository.Jnl01JournalRepository;
import kr.co.kfs.assetedu.repository.Jnl02JournalTmpRepository;
import kr.co.kfs.assetedu.repository.Jnl13TrMapRepository;
import kr.co.kfs.assetedu.repository.Jnl14RealAcntMapRepository;
import kr.co.kfs.assetedu.servlet.exception.AssetException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Jnl01JournalService {
	@Autowired
	Jnl01JournalRepository jnlRepository ;
	@Autowired
	Jnl02JournalTmpRepository jnlTmpRepository;
	Jnl02JournalTmp jnlTmpModel;
	
	@Autowired
	Jnl13TrMapRepository jnl13TrMapRepository;
	@Autowired
	Itm01ItemRepository itemRepository;
	@Autowired
	Jnl14RealAcntMapRepository realAcntMapRepository; 
	
	public List<Jnl01Journal> selectList(QueryAttr queryAttr){
		return jnlRepository.selectList(queryAttr);
	}
	
	@Transactional
	public String createJournal(Opr01Cont cont) throws Exception{
		
		String resultMsg = "Y";
		int procCnt;
		Long lastSeq = 0l;
		
		//임시분개장, 실분개장 정리
		procCnt = jnlTmpRepository.deleteByContId(cont.getOpr01ContId());
		procCnt = jnlRepository.deleteByContId(cont.getOpr01ContId());
		///////
		//임시분개장 생성
		//////
	
		
		//거래코드별 분개map get
		List<Jnl13TrMap> trMapList = jnl13TrMapRepository.selectByTrCd(cont.getOpr01ContId());
		for(Jnl13TrMap trMapModel : trMapList){
			jnlTmpModel = new Jnl02JournalTmp();									
			jnlTmpModel.setJnl02ContId(cont.getOpr01ContId());						//체결ID
			jnlTmpModel.setJnl02Seq(trMapModel.getJnl13Seq());						//순번
			jnlTmpModel.setJnl02DrcrType(trMapModel.getJnl13DrcrType());			//차대구분
			
			//대표계정코드
			//대표계정코드가 미수(1200)/미지급금(2200)인 경우 '체결일=결제일'을 예금(8888)으로 변경 
			jnlTmpModel.setJnl02ReprAcntCd(trMapModel.getJnl13ReprAcntCd());		
			if("1200".equals(jnlTmpModel.getJnl02ReprAcntCd()) ||
				"2200".equals(jnlTmpModel.getJnl02ReprAcntCd()) ) {
				if(cont.getOpr01ContDate().equals(cont.getOpr01SettleDate())) {
					jnlTmpModel.setJnl02ReprAcntCd("8888");
				}
			}
			
			//금액 Get
			
			      QueryAttr amtCondition = new QueryAttr();
			      amtCondition.put("contId", cont.getOpr01ContId());
			      amtCondition.put("formula", trMapModel.getJnl13Formula());
			      
			      Long amt = jnlRepository.getAmt(amtCondition);
			      jnlTmpModel.setJnl02Amt(amt);
			      
			      log.debug("-------sql : {}"+ amt);
			      
			      if(amt != 0) {
			    	  procCnt=jnlTmpRepository.insert(jnlTmpModel);
			    	  lastSeq = jnlTmpModel.getJnl02Seq();
			      }
		}
		
		//차대차익금액 확인 (매수 , 평가 => 에러처리 , 매도 => 처분손익 생성)
		Long diffAmt = jnlTmpRepository.selectDiffAmt(cont.getOpr01ContId());
		if(diffAmt == null) {
			diffAmt = 0l;
		}
		if(diffAmt != 0 ) {
			if(!"2001".equals(cont.getOpr01TrCd()) &&
				!"2002".equals(cont.getOpr01TrCd())) {
				resultMsg = "차/대 금액이 다릅니다.";
				throw new AssetException(resultMsg);
			}
			
			//처분손익 생성
			jnlTmpModel = new Jnl02JournalTmp();
			jnlTmpModel.setJnl02ContId(cont.getOpr01TrCd());
			jnlTmpModel.setJnl02Seq(lastSeq + 1);
			
		
			//차이금액 > 0       처분이익 발생 
			if(diffAmt > 0) {
			jnlTmpModel.setJnl02ReprAcntCd("4100");//4100 처분이익
			jnlTmpModel.setJnl02DrcrType("C"); //대변
			jnlTmpModel.setJnl02Amt(diffAmt);
			procCnt = jnlTmpRepository.insert(jnlTmpModel);
			}
			
			//차이금액 < 0       처분손실 발생
			else {
				jnlTmpModel.setJnl02ReprAcntCd("5100");//5100 처분손실
				jnlTmpModel.setJnl02DrcrType("D"); //차변
				jnlTmpModel.setJnl02Amt(diffAmt * -1);
		
			}
			procCnt = jnlTmpRepository.insert(jnlTmpModel);
		}
		/////
		//실 분개장 생성
		/////

		//종목정보 가져오기
		Itm01Item item = new Itm01Item();
		item.setItm01ItemCd(cont.getOpr01ItemCd());
		Itm01Item itmInfo = itemRepository.selectOne(item);
		
		//상장구분 입력 체크 
		if(itmInfo.getItm01ListType() == null || "".equals(itmInfo.getItm01ListType())) {
			resultMsg = "상장구분이 없습니다. 종목정보를 확인하세요.";
			throw new AssetException(resultMsg);
		}
		//시장구분 입력 체크
		if(itmInfo.getItm01MarketType() == null || "".equals(itmInfo.getItm01MarketType())) {
			resultMsg = "시장구분이 없습니다. 종목정보를 확인하세요.";
			throw new AssetException(resultMsg);
		}
		QueryAttr realAcntCondition = new QueryAttr();
		realAcntCondition.put("listType", itmInfo.getItm01ListType());
		realAcntCondition.put("marketType", itmInfo.getItm01MarketType());
		
		
		//임시분개장
		Long drSeq=0l;
		Long crSeq=0l;
		String dmlType = "I";
		Jnl01Journal jnlModel;
		
		List<Jnl02JournalTmp> jnlTmpList = jnlTmpRepository.selectByContId(cont.getOpr01TrCd());
		for(Jnl02JournalTmp jnlTmp : jnlTmpList) {
			
			jnlModel = new Jnl01Journal();
			jnlModel.setJnl01ContId(cont.getOpr01ContId());
			//대표계정코드 => 실계정 코드
			realAcntCondition.put("reprAcntCd", jnlTmp.getJnl02ReprAcntCd());
			String acntCd = realAcntMapRepository.selectByRealAcntCd(realAcntCondition);
			
			if(acntCd == null || "".equals(acntCd)) {
				resultMsg = "실계정 과목을 가져올 수 없습니다. 관리팀에 문의하세요.";
					throw new AssetException(resultMsg);
			}
			
			//차변 대변 자리 맞춰서 실분개장 생성
			if("D".equals(jnlTmp.getJnl02DrcrType())) {
				drSeq++;
				dmlType="I";
				
				jnlModel.setJnl01Seq(drSeq);
				jnlModel.setJnl01DrAcntCd(acntCd);
				jnlModel.setJnl01DrAmt(jnlTmp.getJnl02Amt());
			}
			else {
				crSeq++;
				jnlModel.setJnl01Seq(crSeq);
				if(drSeq >= crSeq) {
					dmlType = "U";
					jnlModel = jnlRepository.selectOne(jnlModel);
					
				}else {
					dmlType="I";
				}
				jnlModel.setJnl01CrAcntCd(acntCd);
				jnlModel.setJnl01CrAmt(jnlTmp.getJnl02Amt());
			}
			
			if("I".equals(dmlType)) {
				procCnt = jnlRepository.insert(jnlModel);
			}else {
				procCnt = jnlRepository.update(jnlModel);
					
				}
				
				
				
			
			
			
			
			
			
		
		
		}
		
		
		
		return resultMsg;
		
	}
}

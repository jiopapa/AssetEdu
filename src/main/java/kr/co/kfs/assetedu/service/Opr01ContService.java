package kr.co.kfs.assetedu.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Bok01Book;
import kr.co.kfs.assetedu.model.Com03Date;
import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Bok01BookRepository;
import kr.co.kfs.assetedu.repository.Com03DateRepository;
import kr.co.kfs.assetedu.repository.Opr01ContRepository;
import kr.co.kfs.assetedu.servlet.exception.AssetException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Opr01ContService {
	@Autowired
	Opr01ContRepository contRepository;
	@Autowired
	Bok01BookRepository bookRepository;
	@Autowired
	Com03DateRepository dateRepository;
	@Autowired
	Bok01BookService bookService;
	
	public List<Opr01Cont> selectList(QueryAttr queryAttr){
		return contRepository.selectList(queryAttr);
	}
	public Opr01Cont selectOne(Opr01Cont cont) {
		return contRepository.selectOne(cont);
	}
	@Transactional
	public String insert(Opr01Cont cont) throws Exception{

		// 체결ID SET
		cont.setOpr01ContId(contRepository.getNewSeq());
		
		// 보유원장ID SET
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("holdDate", cont.getOpr01ContDate());
		queryAttr.put("fundCode", cont.getOpr01FundCd());
		queryAttr.put("itemCode", cont.getOpr01ItemCd());

		String	bookId = "";
		
		Bok01Book book = bookRepository.selectByItemCode(queryAttr);
		if(book == null) { //거래가 없으면 순번 새로 
			bookId = contRepository.getNewSeq();
		} else {
			bookId = book.getBok01BookId();
		}
		cont.setOpr01BookId(bookId);
		
		
		// 체결상태 SET
		cont.setOpr01StatusCd("0"); // 미처리 :0
		System.out.println("처리상태 미처리로 변환!@!@!@#!");
		// 체결내역 INSERT
		int insertCnt = contRepository.insert(cont);
		log.debug("인서트카운트 : { }" , insertCnt);
		
		log.debug("------------------------------------");
		
		// 처리MAIN 호출 (보유원장 생성, 분개장 생성)
		String resultMsg = this.procMain("P", cont);
		
		return resultMsg;
	}

	@Transactional
	public String eval(String procType, String evalDate)throws Exception{
		//평가리스트 가져오기
		int resultCnt = 0;
		String resultMsg = "Y";
		String trCode = "3001";
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("evalDate",evalDate);
		queryAttr.put("searchText", "%");
		
		List<Bok01Book> evalList = bookRepository.selectEvalList(queryAttr);
		for(Bok01Book book : evalList) {
			//평가 처리  ( 미평가건만 해당)
			if("P".equals(procType) && "false".equals(book.getBok01EvalYn())) {
				// 평가내역 생성 (opr01 insert)
				Opr01Cont insertModel = new Opr01Cont();
				insertModel.setOpr01ContId(contRepository.getNewSeq());
				insertModel.setOpr01FundCd(book.getBok01FundCd());
				insertModel.setOpr01ItemCd(book.getBok01ItemCd());
				insertModel.setOpr01ContDate(book.getBok01HoldDate());
				insertModel.setOpr01TrCd("3001");
				insertModel.setOpr01Qty(book.getBok01HoldQty());
				
				//평가단가
				insertModel.setOpr01Price(book.getBok01EvalPrice());
				
				//평가금액 보유수량 * 평가단가
				Long evalAmt = book.getBok01HoldQty() * book.getBok01EvalPrice();
				insertModel.setOpr01ContAmt(evalAmt);
				
				//평가손익 = 평가금액 - 장부금액
				insertModel.setOpr01TrPl(evalAmt - book.getBok01BookAmt());
				
				insertModel.setOpr01BookId(book.getBok01BookId());
				insertModel.setOpr01BookAmt(book.getBok01BookAmt());
				insertModel.setOpr01StatusCd("0");//0 : 미처리
				
				int procCnt = contRepository.insert(insertModel);
				resultCnt++;
				// 처리&취소 main 호출
				resultMsg = procMain("P", insertModel);
				
			}
			//평가 취소  (평가완료건만 해당)
			else if("C".equals(procType) && "true".equals(book.getBok01EvalYn()) ) {
				// 거래 내역 가져오기
				Opr01Cont cont = new Opr01Cont();
				cont.setOpr01ContId(book.getBok01ContId());
				cont = contRepository.selectOne(cont);
				resultCnt++;

				
				// 처리&취소 main 호출
				resultMsg = procMain("C", cont);
			}
			else {
				resultMsg = "처리구분을 확인해 주세요 .";
				throw new AssetException(resultMsg);
			}
		}
		if(resultCnt ==0) {
			if("P".equals(procType)) {
				resultMsg = "평가처리 대상이 없습니다.";
			} else { 
				resultMsg = "평가취소 대상이 없습니다.";
			}throw new AssetException(resultMsg);
		}
	
		return resultMsg;
}
	@Transactional
	public String delete(Opr01Cont cont)throws Exception{
		
		// 프로세스 메인 호출 보유원장 정리
		String resultMsg = this.procMain("C", cont);
		return resultMsg;
		
	}
	@Transactional
	public String procMain(String procType,Opr01Cont cont) throws Exception {
		String resultMsg = "Y";
		log.debug(resultMsg);
		log.debug(procType);
		int procCnt; 
		
		//------------------
		//처리 P & 원장이월 A
		//------------------
		if("P".equals(procType) || "A".equals(procType)) {
			
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			//-------------------
			//원장 이월
			//-----------------
			//최근 보유일자 GET
			String lastDateStr = bookRepository.getLastHoldDate();
			if(lastDateStr != null) {
				
				Date lastDate = transFormat.parse(lastDateStr);
				Date conDate = transFormat.parse(cont.getOpr01ContDate());
				
				//최근 보유일자 < 체결일인 경우에는 원장이월처리\
				if(lastDate.compareTo(conDate) < 0 ) {
					
					//원장 이월 시작일자 = 최근보유일자 + 1 
					Calendar cal = Calendar.getInstance();
					cal.setTime(lastDate);
					cal.add(Calendar.DATE, 1);
					String startDate = dateFormat.format(cal.getTime());
					//원장 이월 종료일자 = 체결일자 
					String closeDate = cont.getOpr01ContDate();
					
					//이월기간 GET
					QueryAttr dateCondition = new QueryAttr();
					dateCondition.put("startDate", startDate);
					dateCondition.put("closeDate", closeDate);
					List<Com03Date> dateList = dateRepository.selectListByPeriod(dateCondition);
					
					//원장이월
					for(Com03Date dateModel : dateList) {
						QueryAttr insertCondition = new QueryAttr();
						insertCondition.put("holdDate", dateModel.getCom03Day());
						procCnt = bookRepository.insertByDayBefor(insertCondition);
					}
				}
			} //원장 이월 END
			if("P".equals(procType)){
				//원장모듈 호출
				resultMsg = bookService.createBook(cont);
				
				//처리상태 '완료' Update
				cont.setOpr01StatusCd("1"); //1 = 처리완료\
				procCnt = contRepository.update(cont);
				System.out.println("처리상태 완료로 변환!@!@!@#!");
			}
		}
		//------------------
		//취소 C
		//------------------
		else if("C".equals(procType)) {
			//취소대상 거래내역 9로 변경  9:취소
			log.debug("cont : {} ", cont);
			cont.setOpr01StatusCd("9");
			cont.setOpr01ContId(cont.getOpr01ContId());
			procCnt = contRepository.statusChange9(cont);
			
			//보유일자 = 오늘  조건 
			QueryAttr setHoldDateSetId =new QueryAttr();
			log.debug("cont : {} ", cont);
			setHoldDateSetId.put("holdDate", cont.getOpr01ContDate());
			setHoldDateSetId.put("bookId", cont.getOpr01BookId());
			log.debug("(보유일자 = 오늘)   조건에 넣음");
			
			//금일 보유 원장 삭제
			int dltCnt = bookRepository.deleteByHoldDate(setHoldDateSetId);
			log.debug("오늘 보유 원장 모두 삭제 : {}",dltCnt);
			
			//취소했던 ID의 원장 다시 이월 
			int insertCnt = bookRepository.insertByDayBefor(setHoldDateSetId);
			log.debug("취소했던ID 원장 다시 이월(취소분 제외하고) : {}",insertCnt);
			
			//보유 ID의 다른거래도 처리 ( 처리 한번 했던 것들 )
			QueryAttr setOtherId = new QueryAttr();
			setOtherId.put("bookId", cont.getOpr01BookId());
			setOtherId.put("contDate", cont.getOpr01ContDate());
			
			List<Opr01Cont> otherContList = contRepository.selectByOtherCont(setOtherId);
			for(Opr01Cont contList :otherContList ) {
				resultMsg = bookService.createBook(contList);
			}
			
		}
		return resultMsg;
	}
	
	
}

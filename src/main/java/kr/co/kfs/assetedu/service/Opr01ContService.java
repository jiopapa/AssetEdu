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
	@Autowired
	Opr01ContRepository opr01ContRepository;
	
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
		
		// 체결내역 INSERT
		int insertCnt = contRepository.insert(cont);
		log.debug("인서트카운트 : { }" , insertCnt);
		
		log.debug("------------------------------------");
		
		// 처리MAIN 호출 (보유원장 생성, 분개장 생성)
		String resultMsg = this.procMain("P", cont);
		
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
			procCnt = opr01ContRepository.statusChange9(cont);
			
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

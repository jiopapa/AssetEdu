package kr.co.kfs.assetedu.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Com03Date;
import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Bok01BookRepository;
import kr.co.kfs.assetedu.repository.Com03DateRepository;
import kr.co.kfs.assetedu.repository.Opr01ContRepository;

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
	@Transactional
	public String insert(Opr01Cont cont) throws Exception{
		
		// 체결ID SET
		cont.setOpr01ContId(contRepository.getNewSeq());
		
		// 보유원장ID SET
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("holdDate", cont.getOpr01ContDate());
		queryAttr.put("fundCode", cont.getOpr01FundCd());
		queryAttr.put("itemCode", cont.getOpr01ItemCd());
		
		String bookId = bookRepository.getBookId(queryAttr);
		if(bookId == null) {
			bookId = contRepository.getNewSeq();
		}
		cont.setOpr01BookId(bookId);
		
		// 체결상태 SET
		cont.setOpr01StatusCd("0"); // 미처리
		// 체결내역 INSERT
		int insertCnt = contRepository.insert(cont);
		
		// 처리MAIN 호출 (보유원장 생성, 분개장 생성)
		String resultMsg = this.procMain("P", cont);
		
		return resultMsg;
	}
	@Transactional
	public String procMain(String procType,Opr01Cont cont) throws Exception {
		String resultMsg = "Y";
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
			
		}
		
		return resultMsg;
		
	}
}

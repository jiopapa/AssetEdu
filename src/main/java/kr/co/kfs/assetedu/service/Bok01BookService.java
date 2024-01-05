package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Bok01Book;
import kr.co.kfs.assetedu.model.Jnl12Tr;
import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Bok01BookRepository;
import kr.co.kfs.assetedu.repository.Jnl12TrRepository;
import kr.co.kfs.assetedu.repository.Opr01ContRepository;
import kr.co.kfs.assetedu.servlet.exception.AssetException;

@Service
public class Bok01BookService {
	@Autowired
	Bok01BookRepository bookRepository;
	
	@Autowired
	Jnl12TrRepository trRepository;
	
	@Autowired
	Opr01ContRepository contRepository;
	
	@Autowired
	Jnl01JournalService jnlService;
	
	
	
	public List<Bok01Book> selectList(QueryAttr queryAttr){
		return bookRepository.selectList(queryAttr);
	}
	
	@Transactional
	public String createBook(Opr01Cont cont) throws Exception {
		String resultMsg = "Y";
		int procCnt; 
		//보유원장 GET
		QueryAttr bookCondition = new QueryAttr();
		bookCondition.put("bookId", cont.getOpr01BookId());
		bookCondition.put("holdDate", cont.getOpr01ContDate());
		Bok01Book book = bookRepository.selectByBookId(bookCondition);
		
		if(book == null) { //최초 구매 
			book = new Bok01Book();
			
			book.setBok01BookId(cont.getOpr01BookId());
			book.setBok01HoldDate(cont.getOpr01ContDate());
			book.setBok01FundCd(cont.getOpr01FundCd());
			book.setBok01ItemCd(cont.getOpr01ItemCd());
			book.setBok01EvalAmt(0l);
			book.setBok01EvalPl(0l);
			book.setBok01EvalYn("false");
		}
		//거래유형정보 GET ( jnl12 inoutType)
		Jnl12Tr trInfo = trRepository.selectByTrCd(cont.getOpr01TrCd());
		if(trInfo.getJnl12InOutType()==null) {
			resultMsg = "원장입출고구분 값이 없습니다. 분개맵핑 정보를 확인하세요.";
			throw new AssetException(resultMsg);
		}
		//--------------------------
		//1:입고 데이터 set 
		//--------------------------
		if("1".equals(trInfo.getJnl12InOutType())) {
			cont.setOpr01BookAmt(cont.getOpr01ContAmt());
			
			//보유수량
			Long holdQty = 0l;
			if(book.getBok01HoldQty() == null) {
				holdQty = book.getBok01HoldQty();
			}
			book.setBok01HoldQty(holdQty + cont.getOpr01Qty());
			
			Long bookAmt = 0l;
			if(book.getBok01BookAmt() == null) {
				bookAmt = book.getBok01BookAmt();
			}
			book.setBok01BookAmt(bookAmt + cont.getOpr01BookAmt());
			
			Long purAmt = 0l;
			if(book.getBok01PurAmt() == null) {
				purAmt = book.getBok01PurAmt();
			}
			book.setBok01PurAmt(purAmt + cont.getOpr01BookAmt());
		}


		//--------------------------
		//2:출고
		//--------------------------
		else if("2".equals(trInfo.getJnl12InOutType())) {
			
		}
		//--------------------------
		//3:원장변경 (평가)
		//--------------------------
		else if("3".equals(trInfo.getJnl12InOutType())) {
			
		}
		//-------
		else {
			resultMsg = "정해지지 않은 처리코드입니다. 관리팀에 문의하세요.";
			throw new AssetException(resultMsg);
		}
		//원장 반영 insert update
		procCnt = bookRepository.upsert(book);
		
		
		//체결내역 장부금액 UPDATE
		procCnt = contRepository.update(cont);
		
		//분개모듈 호출 
		resultMsg = jnlService.createJournal(cont);
		
		return resultMsg;
	}
}

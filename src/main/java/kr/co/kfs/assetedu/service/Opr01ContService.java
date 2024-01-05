package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Com02Code;
import kr.co.kfs.assetedu.model.Itm01Item;
import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Bok01BookRepository;
import kr.co.kfs.assetedu.repository.Com02CodeRepository;
import kr.co.kfs.assetedu.repository.Itm01ItemRepository;
import kr.co.kfs.assetedu.repository.Opr01ContRepository;

@Service
public class Opr01ContService {
	@Autowired
	Opr01ContRepository contRepository;
	@Autowired
	Bok01BookRepository bookRepository;
	
	public List<Opr01Cont> selectList(QueryAttr queryAttr){
		return contRepository.selectList(queryAttr);
	}
	@Transactional
	public String insert(Opr01Cont cont) {
		
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
	public String procMain(String procType,Opr01Cont cont) {
		String resultMsg = "Y";
		
		
		
		
		return resultMsg;
		
	}
}

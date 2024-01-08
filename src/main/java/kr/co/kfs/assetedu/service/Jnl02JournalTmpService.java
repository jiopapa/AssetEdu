package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Jnl01Journal;
import kr.co.kfs.assetedu.model.Jnl02JournalTmp;
import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Jnl01JournalRepository;
import kr.co.kfs.assetedu.repository.Jnl02JournalTmpRepository;

@Service
public class Jnl02JournalTmpService {
	@Autowired
	Jnl02JournalTmpRepository jnlRepository ;
	
	
//	public List<Jnl02JournalTmp> selectList(QueryAttr queryAttr){
//		return jnlRepository.selectList(queryAttr);
//	}
	
	@Transactional
	public String createJournal(Opr01Cont cont) throws Exception{
		
		String resultMsg = "Y";
		int procCnt;
		
		//임시분개장, 실분개장 정리
		
		
		
		
		//거래코드별 분개map get
		
		//금액 Get
		
		//대표계정코드가 미수/미지급금인 경우 '체결일=결제일' > 예금으로 변경 
		
		//차대차익금액 확인 (매수 , 평가 => 에러처리 , 매도 => 처분손익 생성)
		//손실 발생시  - 제거 후 반대계정 처리
		
		
		return resultMsg;
		
	}
}

package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Jnl01Journal;
import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Jnl01JournalRepository;

@Service
public class Jnl01JournalService {
	@Autowired
	Jnl01JournalRepository jnlRepository ;
	
	
	public List<Jnl01Journal> selectList(QueryAttr queryAttr){
		return jnlRepository.selectList(queryAttr);
	}
	
	@Transactional
	public String createJournal(Opr01Cont cont) throws Exception{
		
		String resultMsg = "Y";
		int procCnt;
		
		
		return resultMsg;
		
	}
}

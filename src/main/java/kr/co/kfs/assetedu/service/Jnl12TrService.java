package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Jnl11ReprAcnt;
import kr.co.kfs.assetedu.model.Jnl12Tr;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Jnl12TrRepository;

@Service
public class Jnl12TrService {
	@Autowired
	Jnl12TrRepository jnl12TrRepository ;
	
	public List<Jnl12Tr> selectList(QueryAttr queryAttr){
		return jnl12TrRepository.selectList(queryAttr);
	}
	public Long selectCount(QueryAttr queryAttr) {
		return jnl12TrRepository.selectCount(queryAttr);
	}

	
	@Transactional
	public Integer insert(Jnl12Tr jnl12Tr) {
		return jnl12TrRepository.insert(jnl12Tr);
	}
	public Jnl12Tr selectOne(Jnl12Tr jnl12Tr) {
		return jnl12TrRepository.selectOne(jnl12Tr);
	}

	
	@Transactional
	public Integer update(Jnl12Tr jnl12Tr) {
		return jnl12TrRepository.update(jnl12Tr);
	}
	@Transactional
	public Integer delete(String jnl12TrCd) {
		return jnl12TrRepository.delete(jnl12TrCd);
	}
  public Jnl11ReprAcnt selectOne(String jnl12TrCd) {
	  Jnl12Tr tr = new Jnl12Tr();
	  tr.setJnl12TrCd(jnl12TrCd);
		return this.selectOne(jnl12TrCd);
		}
		
	

	
}

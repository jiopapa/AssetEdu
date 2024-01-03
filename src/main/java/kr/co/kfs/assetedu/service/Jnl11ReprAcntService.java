package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Jnl11ReprAcnt;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Jnl11ReprAcntRepository;

@Service
public class Jnl11ReprAcntService {
	@Autowired
	Jnl11ReprAcntRepository jnl11ReprAcntRepository ;
	
	public List<Jnl11ReprAcnt> selectList(QueryAttr queryAttr){
		return jnl11ReprAcntRepository.selectList(queryAttr);
	}
	public Long selectCount(QueryAttr queryAttr) {
		return jnl11ReprAcntRepository.selectCount(queryAttr);
	}

	
	@Transactional
	public Integer insert(Jnl11ReprAcnt reprAcnt) {
		return jnl11ReprAcntRepository.insert(reprAcnt);
	}
	public Jnl11ReprAcnt selectOne(Jnl11ReprAcnt reprAcnt) {
		return jnl11ReprAcntRepository.selectOne(reprAcnt);
	}

	
	@Transactional
	public Integer update(Jnl11ReprAcnt reprAcnt) {
		return jnl11ReprAcntRepository.update(reprAcnt);
	}
	@Transactional
	public Integer delete(String jnl11ReprAcntCd) {
		return jnl11ReprAcntRepository.delete(jnl11ReprAcntCd);
	}
  public Jnl11ReprAcnt selectOne(String jnl11ReprAcntCd) {
	  Jnl11ReprAcnt jnl11ReprAcnt = new Jnl11ReprAcnt();
	  jnl11ReprAcnt.setJnl11ReprAcntCd(jnl11ReprAcntCd);
		return this.selectOne(jnl11ReprAcnt);
		}
		
	

	
}

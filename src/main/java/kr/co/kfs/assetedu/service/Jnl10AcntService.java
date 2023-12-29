package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Jnl10Acnt;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Jnl10AcntRepository;

@Service
public class Jnl10AcntService {
	@Autowired
	Jnl10AcntRepository jnl10AcntRepository;
	
	public List<Jnl10Acnt> selectList(QueryAttr queryAttr){
		return jnl10AcntRepository.selectList(queryAttr);
	}
	public Long selectCount(QueryAttr queryAttr) {
		return jnl10AcntRepository.selectCount(queryAttr);
	}

	
	@Transactional
	public Integer insert(Jnl10Acnt acnt) {
		return jnl10AcntRepository.insert(acnt);
	}
	public Jnl10Acnt selectOne(Jnl10Acnt acnt) {
		return jnl10AcntRepository.selectOne(acnt);
	}
	@Transactional
	public Integer update(Jnl10Acnt acnt) {
		return jnl10AcntRepository.update(acnt);
	}
	@Transactional
	public Integer delete(Jnl10Acnt acnt) {
		return jnl10AcntRepository.delete(acnt);
	}
	
}

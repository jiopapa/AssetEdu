package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Jnl13TrMap;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Jnl13TrMapRepository;

@Service
public class Jnl13TrMapService {
	@Autowired
	Jnl13TrMapRepository jnl13TrMapRepository ;
	
	public List<Jnl13TrMap> selectList(QueryAttr queryAttr){
		return jnl13TrMapRepository.selectList(queryAttr);
	}
	public Long selectCount(QueryAttr queryAttr) {
		return jnl13TrMapRepository.selectCount(queryAttr);
	}

	
	@Transactional
	public Integer insert(Jnl13TrMap trmap) {
		return jnl13TrMapRepository.insert(trmap);
	}
	public Jnl13TrMap selectOne(Jnl13TrMap trmap) {
		trmap.setJnl13TrCd(null);
		return jnl13TrMapRepository.selectOne(trmap);
	}

	
	@Transactional
	public Integer update(Jnl13TrMap trmap) {
		return jnl13TrMapRepository.update(trmap);
	}
	@Transactional
	public Integer delete(String jnl13TrCd) {
		return jnl13TrMapRepository.delete(jnl13TrCd);
	}
  public Jnl13TrMap selectOne(String jnl13TrCd) {
	  Jnl13TrMap tr = new Jnl13TrMap();
	  tr.setJnl13TrCd(jnl13TrCd);
		return this.selectOne(jnl13TrCd);
		}
		
	

	
}

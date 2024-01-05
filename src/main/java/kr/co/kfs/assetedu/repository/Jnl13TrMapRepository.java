package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Jnl11ReprAcnt;
import kr.co.kfs.assetedu.model.Jnl12Tr;
import kr.co.kfs.assetedu.model.Jnl13TrMap;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Jnl13TrMapRepository {
	List<Jnl13TrMap> selectList (QueryAttr queryAttr);
	Long selectCount(QueryAttr queryAttr);
	Jnl13TrMap selectOne(Jnl13TrMap trMap);
	
	
	Integer insert(Jnl13TrMap trMap);
	Integer update(Jnl13TrMap trMap);
	Integer delete(String jnl13TrCd);
}

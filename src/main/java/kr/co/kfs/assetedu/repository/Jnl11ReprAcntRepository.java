package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Jnl11ReprAcnt;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Jnl11ReprAcntRepository {
	List<Jnl11ReprAcnt> selectList (QueryAttr queryAttr);
	Long selectCount(QueryAttr queryAttr);
	Jnl11ReprAcnt selectOne(Jnl11ReprAcnt reprAcnt);
	
	
	Integer insert(Jnl11ReprAcnt reprAcnt);
	Integer update(Jnl11ReprAcnt reprAcnt);
	Integer delete(String jnl11ReprAcntCd);
}

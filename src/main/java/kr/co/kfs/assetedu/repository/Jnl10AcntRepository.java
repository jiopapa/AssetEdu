package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Jnl10Acnt;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Jnl10AcntRepository {
	List<Jnl10Acnt> selectList (QueryAttr queryAttr);
	Long selectCount(QueryAttr queryAttr);
	Jnl10Acnt selectOne(Jnl10Acnt acnt);
	
	
	Integer insert(Jnl10Acnt acnt);
	Integer update(Jnl10Acnt acnt);
	Integer delete(String jnl10AcntCd);
}

package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Jnl01Journal;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Jnl01JournalRepository {
	List<Jnl01Journal> 		selectList (QueryAttr queryAttr);
	Jnl01Journal selectOne(Jnl01Journal jnl01Journal);
	
	
	Long getAmt(QueryAttr amtCondition);
	Long selectCount(QueryAttr queryAttr);
	
	int insert(Jnl01Journal jnl01Journal);
	int update(Jnl01Journal jnl01Journal);
	int deleteByContId(String contId);

}

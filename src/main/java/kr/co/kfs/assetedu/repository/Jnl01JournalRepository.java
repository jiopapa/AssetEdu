package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Jnl01Journal;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Jnl01JournalRepository {
	List<Jnl01Journal> 		selectList (QueryAttr queryAttr);
}

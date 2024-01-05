package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Jnl11ReprAcnt;
import kr.co.kfs.assetedu.model.Jnl12Tr;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Jnl12TrRepository {
	List<Jnl12Tr> 		selectList (QueryAttr queryAttr);
	Long 				selectCount(QueryAttr queryAttr);
	Jnl12Tr 			selectOne(Jnl12Tr tr);
	Jnl12Tr 			selectByTrCd(String trCd);
	
	
	Integer 			insert(Jnl12Tr tr);
	Integer 			update(Jnl12Tr tr);
	Integer 			delete(String jnl12TrCd);
}

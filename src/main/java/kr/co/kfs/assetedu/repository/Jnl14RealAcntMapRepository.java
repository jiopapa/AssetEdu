package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Jnl14RealAcntMap;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Jnl14RealAcntMapRepository {
	List<Jnl14RealAcntMap> 			selectList (QueryAttr queryAttr);
	Long 						selectCount(QueryAttr queryAttr);
	
	String selectByRealAcntCd(QueryAttr queryAttr);
	
	
}

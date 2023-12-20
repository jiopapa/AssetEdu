package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Com01Corp;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Com01CorpRepository {
	List<Com01Corp> selectList (QueryAttr queryAttr);
	Integer selectCount(QueryAttr queryAttr);
	Com01Corp selectOne(Com01Corp corp);
	
	
	Integer insert(Com01Corp corp);
	Integer update(Com01Corp corp);
}

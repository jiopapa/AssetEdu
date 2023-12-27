package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Fnd01Fund;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Fnd01FundRepository {

	List<Fnd01Fund> selectList (QueryAttr queryAttr);
	Long selectCount(QueryAttr queryAttr);
	Fnd01Fund selectOne (Fnd01Fund fund);
	

	Integer insert(Fnd01Fund fund);
	Integer update(Fnd01Fund fund);
	Integer delete(Fnd01Fund fund);
}

package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Fnd01Fund;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Fnd01FundRepository {

	List<Fnd01Fund> selectList (QueryAttr queryAttr);
	Integer selectCount(QueryAttr queryAttr);
}

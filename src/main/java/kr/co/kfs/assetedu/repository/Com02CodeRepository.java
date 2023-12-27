package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Com02Code;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Com02CodeRepository {
	List<Com02Code> selectList (QueryAttr queryAttr);
	Long selectCount(QueryAttr queryAttr);
}

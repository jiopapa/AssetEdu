package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Opr01ContRepository {
	List<Opr01Cont> selectList (QueryAttr queryAttr);
	String getNewSeq();
	Opr01Cont selectOne(Opr01Cont cont);
	List<Opr01Cont> selectByOtherCont(QueryAttr queryAttr);
	
	
	int insert(Opr01Cont cont);
	int update(Opr01Cont cont);
	int statusChange9(Opr01Cont cont);
}

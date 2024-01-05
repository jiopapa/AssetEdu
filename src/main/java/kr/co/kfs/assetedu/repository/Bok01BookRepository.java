package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Bok01Book;
import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Bok01BookRepository {
	List<Bok01Book> selectList (QueryAttr queryAttr);
	String getBookId(QueryAttr queryAttr);
	
	
	int insert(Bok01Book book);
}

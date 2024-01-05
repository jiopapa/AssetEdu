package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Bok01Book;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Bok01BookRepository {
	List<Bok01Book> 	selectList (QueryAttr queryAttr);
	String 				getBookId(QueryAttr queryAttr);
	String 				getLastHoldDate();
	Bok01Book 			selectByBookId(QueryAttr queryAttr);
	
	int insert(Bok01Book book);
	int insertByDayBefor(QueryAttr dateCondition);
	int upsert(Bok01Book book);
	
}

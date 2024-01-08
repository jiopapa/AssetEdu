package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Com01Corp;
import kr.co.kfs.assetedu.model.Com02Code;
import kr.co.kfs.assetedu.model.Itm01Item;
import kr.co.kfs.assetedu.model.QueryAttr;

public interface Itm01ItemRepository {
	List<Itm01Item> selectList (QueryAttr queryAttr);
	Long selectCount(QueryAttr queryAttr);
	Itm01Item selectOne(Itm01Item item);
	
	
	Integer insert(Itm01Item item);
	Integer update(Itm01Item item);
	Integer delete(Itm01Item item);
}

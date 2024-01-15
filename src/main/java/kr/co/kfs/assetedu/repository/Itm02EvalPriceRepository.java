package kr.co.kfs.assetedu.repository;

import java.util.List;

import kr.co.kfs.assetedu.model.Com01Corp;
import kr.co.kfs.assetedu.model.Com02Code;
import kr.co.kfs.assetedu.model.Itm01Item;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.model.Itm02EvalPrice;

public interface Itm02EvalPriceRepository {
	List<Itm02EvalPrice> selectList (QueryAttr queryAttr);
	Long selectCount(QueryAttr queryAttr);
	Itm01Item selectOne(Itm02EvalPrice item);
	
	int upsert(Itm02EvalPrice price);
	Integer insert(Itm02EvalPrice item);
	Integer update(Itm02EvalPrice item);
	Integer delete(Itm02EvalPrice item);
}

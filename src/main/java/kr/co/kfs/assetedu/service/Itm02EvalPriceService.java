package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Com02Code;
import kr.co.kfs.assetedu.model.Itm01Item;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.model.Itm02EvalPrice;
import kr.co.kfs.assetedu.repository.Com02CodeRepository;
import kr.co.kfs.assetedu.repository.Itm01ItemRepository;
import kr.co.kfs.assetedu.repository.Itm02EvalPriceRepository;

@Service
public class Itm02EvalPriceService {
	@Autowired
	Itm02EvalPriceRepository itm02EvalPriceRepository;
	@Autowired
	Com02CodeRepository com02CodeRepository;
	
	public List<Itm02EvalPrice> selectList(QueryAttr queryAttr){
		return itm02EvalPriceRepository.selectList(queryAttr);
	}
	public Long selectCount(QueryAttr queryAttr) {
		return itm02EvalPriceRepository.selectCount(queryAttr);
	}
	
	@Transactional
	public Integer insert(Itm02EvalPrice item) {
		return itm02EvalPriceRepository.insert(item);
	}
	public Itm01Item selectOne(Itm02EvalPrice item) {
		return itm02EvalPriceRepository.selectOne(item);
	}
	@Transactional
	public Integer update(Itm02EvalPrice item) {
		return itm02EvalPriceRepository.upsert(item);
	}
	@Transactional
	public Integer delete(Itm02EvalPrice item) {
		return itm02EvalPriceRepository.delete(item);
	}
}

package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kfs.assetedu.model.Com02Code;
import kr.co.kfs.assetedu.model.Itm01Item;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Com02CodeRepository;
import kr.co.kfs.assetedu.repository.Itm01ItemRepository;

@Service
public class Itm01ItemService {
	@Autowired
	Itm01ItemRepository itm01ItemRepository;
	@Autowired
	Com02CodeRepository com02CodeRepository;
	
	public List<Itm01Item> selectList(QueryAttr queryAttr){
		return itm01ItemRepository.selectList(queryAttr);
	}
	
	
	public List<Com02Code> corpTypeList(String com02Cd){
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("com02ComCd"   , com02Cd);
		queryAttr.put("com02CodeType", "D");
		queryAttr.put("com02UseYn"   , "true");
		return com02CodeRepository.selectList(queryAttr);
	}
	
	@Transactional
	public Integer insert(Itm01Item item) {
		return itm01ItemRepository.insert(item);
	}
	public Itm01Item selectOne(Itm01Item item) {
		return itm01ItemRepository.selectOne(item);
	}
	@Transactional
	public Integer update(Itm01Item item) {
		return itm01ItemRepository.update(item);
	}
	@Transactional
	public Integer delete(Itm01Item item) {
		return itm01ItemRepository.delete(item);
	}
	
}

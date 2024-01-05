package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kfs.assetedu.model.Com02Code;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Com02CodeRepository;

@Service
public class Com02CodeService {
	@Autowired
	Com02CodeRepository com02CodeRepository;
	
	public List<Com02Code> selectList(QueryAttr queryAttr){
		return com02CodeRepository.selectList(queryAttr);
	}
	public List<Com02Code> codeList(String com02Cd){
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("com02ComCd"   , com02Cd);
		queryAttr.put("com02CodeType", "D");
		queryAttr.put("com02UseYn"   , "true");
		return com02CodeRepository.selectList(queryAttr);
	}
	
	public List<Com02Code> trCodeList(String trType){
		return com02CodeRepository.selectTrList(trType);
	}
	
	public Long selectCount(QueryAttr queryAttr) {
		return com02CodeRepository.selectCount(queryAttr);
	}
	
	public Com02Code selectOne(Com02Code com02Code) {
		return com02CodeRepository.selectOne(com02Code);
	}
	
	public Integer insert(Com02Code com02Code) {
		return com02CodeRepository.insert(com02Code);
	}
	
	public Integer update(Com02Code com02Code) {
		return com02CodeRepository.update(com02Code);
	}
	
	public Integer delete(Com02Code com02Code) {
		return com02CodeRepository.delete(com02Code);
	}
}

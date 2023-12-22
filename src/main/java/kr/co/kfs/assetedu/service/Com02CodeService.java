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
}

package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kfs.assetedu.model.Com01Corp;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Com01CorpRepository;

@Service
public class Com01CorpService {
	@Autowired
	Com01CorpRepository com01CorpRepository;
	
	public List<Com01Corp> selectList(QueryAttr queryAttr){
		return com01CorpRepository.selectList(queryAttr);
	}
	public Integer insert(Com01Corp corp) {
		return com01CorpRepository.insert(corp);
	}
	public Com01Corp selectOne(Com01Corp corp) {
		return com01CorpRepository.selectOne(corp);
	}
	
	public Integer update(Com01Corp corp) {
		return com01CorpRepository.update(corp);
	}
	
	public Integer delete(Com01Corp corp) {
		return com01CorpRepository.delete(corp);
	}
	
}

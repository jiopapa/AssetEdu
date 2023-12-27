package kr.co.kfs.assetedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kfs.assetedu.model.Fnd01Fund;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Fnd01FundRepository;

@Service
public class Fnd01FundService {
	@Autowired
	Fnd01FundRepository fnd01FundRepository;
	
	public List<Fnd01Fund> selectList(QueryAttr queryAttr) {
		return fnd01FundRepository.selectList(queryAttr);
	}
	public Long selectCount(QueryAttr queryAttr) {
		return fnd01FundRepository.selectCount(queryAttr);
	}
	public Fnd01Fund selectOne(Fnd01Fund fund) {
		return fnd01FundRepository.selectOne(fund);
	}
	
	public Integer insert(Fnd01Fund fund) {
		return fnd01FundRepository.insert(fund);
	}
	public Integer update(Fnd01Fund fund) {
		return fnd01FundRepository.update(fund);
	}
	public Integer delete(Fnd01Fund fund) {
		return fnd01FundRepository.delete(fund);
	}
}

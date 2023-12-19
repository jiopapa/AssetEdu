package kr.co.kfs.assetedu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.kfs.assetedu.model.Com02Code;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com02CodeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/code")
public class CodeController {
	@Autowired
	Com02CodeService com02CodeService;
	
	@GetMapping("list")
	public String list(String searchText, Model model) {
		log.debug("공통코드리스트");
		
		QueryAttr queryAttr = new QueryAttr();
	
		queryAttr.put("searchText", searchText);
		List<Com02Code>listCategory = selectComCds(queryAttr);
		model.addAttribute("CodeList", listCategory);
		return "/code/codeList";
	}
	private List<Com02Code>selectComCds(QueryAttr queryAttr){
		Com02Code code = new Com02Code();
		code.setCom02CodeType("C");
		queryAttr.putClass(code);
		List<Com02Code>list = com02CodeService.selectList(queryAttr);
		return list;
	}

}

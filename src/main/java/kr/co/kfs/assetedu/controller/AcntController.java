package kr.co.kfs.assetedu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kfs.assetedu.model.Jnl10Acnt;
import kr.co.kfs.assetedu.model.PageAttr;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com02CodeService;
import kr.co.kfs.assetedu.service.Jnl10AcntService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/jnl/acnt")
public class AcntController {
	@Autowired
	Jnl10AcntService jnl10AcntService;
	@Autowired
	Com02CodeService com02CodeService;
	
	@GetMapping("list")
	public String list(Model model
			, @RequestParam(value = "currentPageNumber",defaultValue = "1" ,required = false) Integer currentPageNumber
			,	@RequestParam(value = "pageSize", defaultValue ="10" ,required = false) Integer pageSize) {
		
		log.debug("계정과목 리스트");
		QueryAttr queryAttr = new QueryAttr();
		
		com02CodeService.codeList(null);
		Long totalItemCount = jnl10AcntService.selectCount(queryAttr);
		PageAttr pageAttr = new PageAttr(totalItemCount, currentPageNumber, pageSize);
		List<Jnl10Acnt> list = jnl10AcntService.selectList(queryAttr);
		model.addAttribute("list", list);
		
		
		return "/jnl/acnt/acntList";
	}
}

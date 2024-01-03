package kr.co.kfs.assetedu.controller.jnl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.kfs.assetedu.model.Jnl11ReprAcnt;
import kr.co.kfs.assetedu.model.PageAttr;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com02CodeService;
import kr.co.kfs.assetedu.service.Jnl11ReprAcntService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/jnl/repr-acnt")
public class ReprAcntController {
	@Autowired
	Jnl11ReprAcntService jnl11ReprAcntService;
	
	@Autowired
	Com02CodeService com02CodeService;
	
	@GetMapping("list")
	public String list(String searchText
			, @RequestParam(value="pageSize", defaultValue="10", required=false) Integer pageSize
			, @RequestParam(value="currentPageNo" ,defaultValue="1", required = false) Integer currentPageNo
			, Model model) {
		log.debug("대표계정 리스트");
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText",searchText);
		
		com02CodeService.codeList(searchText);
		
		Long totalItemCount = jnl11ReprAcntService.selectCount(queryAttr);
		PageAttr pageAttr = new PageAttr(totalItemCount, pageSize, currentPageNo);
		queryAttr.put("pageAttr", pageAttr);
		log.debug("pageAttr : {} " ,pageAttr);
		log.debug("pageAttr : {} " ,pageAttr.getOffset());
		log.debug("pageAttr : {} " ,pageAttr.getLimit());
		List<Jnl11ReprAcnt> list = jnl11ReprAcntService.selectList(queryAttr);
		model.addAttribute("list", list);
		model.addAttribute("pageAttr", pageAttr);
		model.addAttribute("searchText", searchText);
		return "/jnl/repr-acnt/repr_acnt_list";
	}
	
	@GetMapping("insert")
	public String insert_form(Model model) {
		model.addAttribute("jnl11ReprAcnt", new Jnl11ReprAcnt());
		model.addAttribute("acntAttrCodeList", com02CodeService.codeList("AcntAttrCode"));
		return "/jnl/repr-acnt/insert_form";
	}
	
	@PostMapping("insert")
	public String insert(@Valid @ModelAttribute("jnl11ReprAcnt") Jnl11ReprAcnt jnl11ReprAcnt, BindingResult bindingResult
						, RedirectAttributes redirectAttributes, Model model){
		log.debug("대표계정코드 등록");
		String msg;
		Jnl11ReprAcnt reprAcntCheck = jnl11ReprAcntService.selectOne(jnl11ReprAcnt);
		
		if(reprAcntCheck != null) {
			log.debug("대표계정코드 중복");
			msg = String.format("\"%s\" 코드는 이미 \"%s\" 대표계정으로 등록되어 있습니다.", jnl11ReprAcnt.getJnl11ReprAcntCd()
								, reprAcntCheck.getJnl11ReprAcntNm());
			model.addAttribute("jnl11ReprAcnt", jnl11ReprAcnt);
			model.addAttribute("acntAttrCodeList", com02CodeService.codeList("AcntAttrCode"));
			bindingResult.addError(new FieldError("", "", msg));
			return "/jnl/repr-acnt/insert_form";
		}else {
			int insertCount = jnl11ReprAcntService.insert(jnl11ReprAcnt);
			log.debug("입력된 대표계정 {} ", insertCount);
			redirectAttributes.addFlashAttribute("mode", "insert");
			redirectAttributes.addFlashAttribute("jnl11ReprAcnt", jnl11ReprAcnt);
			log.debug("jnl11ReprAcnt : {} ", jnl11ReprAcnt);
			return "redirect:/jnl/repr-acnt/success";
		}
	}
	@GetMapping("success")
	public String success(Model model) {
		return "/jnl/repr-acnt/success";
	}
	@GetMapping("/update/{jnl11ReprAcntCd}")
	public String update_form(@PathVariable("jnl11ReprAcntCd") String jnl11ReprAcntCd, Model model) {
		Jnl11ReprAcnt reprAcnt = jnl11ReprAcntService.selectOne(jnl11ReprAcntCd);
		model.addAttribute("jnl11ReprAcnt", reprAcnt);
		model.addAttribute("acntAttrCodeList", com02CodeService.codeList("AcntAttrCode"));
		return "/jnl/repr-acnt/update_form";	
	}
	@PostMapping("update")
	public String update(String msg, @ModelAttribute("jnl11ReprAcnt") Jnl11ReprAcnt jnl11ReprAcnt, Model model
						, RedirectAttributes redirectAttributes) {
		int count = jnl11ReprAcntService.update(jnl11ReprAcnt);
		log.debug("수정 카운트  : {} ", count);
		msg = String.format("\"%s\"대표계정코드가 수정되었습니다.", jnl11ReprAcnt.getJnl11ReprAcntNm());
		redirectAttributes.addFlashAttribute("mode", "update");
		redirectAttributes.addFlashAttribute("jnl11ReprAcnt", jnl11ReprAcnt);
		return "redirect:/jnl/repr-acnt/success";
	}
	@GetMapping("/delete/{jnl11ReprAcntCd}")
	public String delete(@PathVariable("jnl11ReprAcntCd") String jnl11ReprAcntCd) {
		int count = jnl11ReprAcntService.delete(jnl11ReprAcntCd);
		log.debug("삭제 카운트 : {} ", count);
		return "redirect:/jnl/repr-acnt/list";
	}
	
/*

	
	
		return "redirect:/jnl/acnt/success";
	}
	@GetMapping("/delete/{jnl10AcntCd}")
	public String delete(@PathVariable("jnl10AcntCd") String jnl10AcntCd ) {
		int deleteCount = jnl10AcntService.delete(jnl10AcntCd);
		log.debug("삭제된 계정과목 : {} ", deleteCount);
		return "redirect:/jnl/acnt/list";
	}
	*/
}

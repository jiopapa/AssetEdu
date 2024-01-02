package kr.co.kfs.assetedu.controller;

import java.io.UnsupportedEncodingException;
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
	public String list(Model model, String searchText
			, @RequestParam(value = "pageSize", defaultValue ="10" ,required = false) Integer pageSize
			,@RequestParam(value = "currentPageNumber",defaultValue = "1" ,required = false) Integer currentPageNumber
	) {
		
		log.debug("계정과목 리스트");
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText",searchText);
		
		com02CodeService.codeList(searchText);
		
		Long totalItemCount = jnl10AcntService.selectCount(queryAttr);
		PageAttr pageAttr = new PageAttr(totalItemCount, pageSize, currentPageNumber);
		queryAttr.put("pageAttr", pageAttr);
		log.debug("pageAttr : {} " ,pageAttr);
		log.debug("pageAttr : {} " ,pageAttr.getOffset());
		log.debug("pageAttr : {} " ,pageAttr.getLimit());
		List<Jnl10Acnt> list = jnl10AcntService.selectList(queryAttr);
		model.addAttribute("list", list);
		model.addAttribute("pageAttr", pageAttr);
		model.addAttribute("searchText", searchText);
		return "/jnl/acnt/acntList";
	}
	
	@GetMapping("insert")
	public String insert_form(Model model) {
		model.addAttribute("jnl10Acnt"     ,new Jnl10Acnt());
		model.addAttribute("acntAttrCodeList", com02CodeService.codeList("AcntAttrCode"));
		model.addAttribute("drcrTypeList", com02CodeService.codeList("DrcrType"));
		return "/jnl/acnt/insert_form";
	}
	@PostMapping("insert")
	public String insert( @Valid @ModelAttribute("jnl10Acnt") Jnl10Acnt jnl10Acnt, BindingResult bindingResult
						, RedirectAttributes redirectAttributes, Model model)throws UnsupportedEncodingException{
		log.debug("계정과목 등록");
		String msg;
		Jnl10Acnt acntCheck = jnl10AcntService.selectOne(jnl10Acnt);
		
		if(acntCheck != null) {
			log.debug("계정과목 중복");
			msg = String.format("\"%s\" 코드는 이미 \"%s\" 계정과목으로 등록되어 있습니다.", acntCheck.getJnl10AcntCd()
					,	acntCheck.getJnl10AcntNm());
			model.addAttribute("jnl10Acnt",jnl10Acnt);
			model.addAttribute("acntAttrCodeList", com02CodeService.codeList("AcntAttrCode"));
			model.addAttribute("drcrTypeList", com02CodeService.codeList("DrcrType"));
			bindingResult.addError(new FieldError("", "", msg));
			return "/jnl/acnt/insert_form";
		}else {
			int insertCount = jnl10AcntService.insert(jnl10Acnt);
			log.debug("등록된 종목정보 : {} ", insertCount);
			msg = String.format("\"%s\" 종목정보가 등록되었습니다", jnl10Acnt.getJnl10AcntNm());
			redirectAttributes.addAttribute("mode", "insert");
			redirectAttributes.addAttribute("msg", msg);
			return "redirect:/jnl/acnt/success";
		}	
	}
	@GetMapping("success")
	public String success(String msg, String jnl10AcntCd, String mode, Model model) {
		model.addAttribute("pageTitle", "펀드등록 /수정완료");
		model.addAttribute("msg", msg);
		model.addAttribute("mode", mode);
		model.addAttribute("jnl10AcntCd", jnl10AcntCd);
		log.debug("펀드 등록 및 수정 성공화면");
		return "/jnl/acnt/success";
	}
	@GetMapping("/update/{jnl10AcntCd}")
	public String update_form(@PathVariable("jnl10AcntCd") String jnl10AcntCd, Model model){
		 Jnl10Acnt acnt = jnl10AcntService.selectOne(jnl10AcntCd);
		 model.addAttribute("acntAttrCodeList", com02CodeService.codeList("AcntAttrCode"));
		 model.addAttribute("drcrTypeList", com02CodeService.codeList("DrcrType"));
		 model.addAttribute("jnl10Acnt", acnt);
		return "/jnl/acnt/update_form";
	}
	
	@PostMapping("update")
	public String update(String msg, @ModelAttribute("jnl10Acnt") Jnl10Acnt jnl10Acnt, Model model,
							RedirectAttributes redirectAttributes) {
		int insertCount = jnl10AcntService.update(jnl10Acnt);
		log.debug("수정된 계정과목 : {} ", insertCount);
		msg = String.format("\"%s\" 계정과목이 수정되었습니다", jnl10Acnt.getJnl10AcntNm());
		redirectAttributes.addAttribute("mode", "update");
		redirectAttributes.addAttribute("msg", msg);
		redirectAttributes.addAttribute("jnl10AcntCd", jnl10Acnt.getJnl10AcntCd());
		return "redirect:/jnl/acnt/success";
	}
	@GetMapping("/delete/{jnl10AcntCd}")
	public String delete(@PathVariable("jnl10AcntCd") String jnl10AcntCd ) {
		int deleteCount = jnl10AcntService.delete(jnl10AcntCd);
		log.debug("삭제된 계정과목 : {} ", deleteCount);
		return "redirect:/jnl/acnt/list";
	}
}

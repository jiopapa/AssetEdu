package kr.co.kfs.assetedu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.kfs.assetedu.model.Fnd01Fund;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com02CodeService;
import kr.co.kfs.assetedu.service.Fnd01FundService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/fund")
public class FundController {
	@Autowired
	Fnd01FundService fnd01FundService;
	@Autowired
	Com02CodeService com02CodeService;


	@GetMapping("list")
	public String list(String searchText, Model model) {
		log.debug("펀드정보");

		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText", searchText);
		List<Fnd01Fund> fundList = fnd01FundService.selectList(queryAttr);
		model.addAttribute("fundList", fundList);
		return "/fund/fundList";
	}

	@GetMapping("insert")
	public String insert(Model model) {
		log.debug("펀드정보 입력페이지");
		model.addAttribute("fund", new Fnd01Fund());
		model.addAttribute("fnd01FundTypeNmList", com02CodeService.codeList("FundType"));
		model.addAttribute("PublicCodeList", com02CodeService.codeList("PublicCode"));
		model.addAttribute("FundUnitCodeList", com02CodeService.codeList("FundUnitCode"));
		model.addAttribute("FundParentCodeList", com02CodeService.codeList("FundParentCode"));
		return "/fund/insert_form";
	}
	@PostMapping("insert")
	public String insert(@Valid @ModelAttribute("fund")	Fnd01Fund fund, BindingResult bindingResult
						, RedirectAttributes redirectAttributes, Model model)throws UnsupportedEncodingException{
		log.debug("펀드 등록");
		String msg;
		Fnd01Fund fundCheck=fnd01FundService.selectOne(fund);
		
		if(fundCheck != null) {
			log.debug("펀드 중복");
			msg = String.format("\"%s\" 코드는 이미 \"%s\" 펀드종목으로 등록되어 있습니다.", fundCheck.getFnd01FundCd()
					,	fundCheck.getFnd01FundNm());
			bindingResult.addError(new FieldError("", "", msg));
			return "/fund/insert_form";
		}else {
			int insertCount = fnd01FundService.insert(fund);
			log.debug("등록된 종목정보 : {} ", insertCount);
			msg = String.format("\"%s\" 종목정보가 등록되었습니다", fund.getFnd01FundNm());
			redirectAttributes.addAttribute("mode", "insert");
			redirectAttributes.addAttribute("msg", msg);
			return "redirect:/fund/success";
		}
		
	}
	@GetMapping("success")
	public String success(String msg, String fundCd, String mode, Model model) {
		model.addAttribute("pageTitle", "펀드등록 /수정완료");
		model.addAttribute("msg", msg);
		model.addAttribute("mode", mode);
		model.addAttribute("fundCd", fundCd);
		log.debug("펀드 등록 및 수정 성공화면");
		return "/fund/success";
	}
	@GetMapping("update")
	public String update_form(@ModelAttribute("fund")Fnd01Fund fund, Model model)throws UnsupportedEncodingException{
		log.debug("펀드종목 수정페이지 이동");
		fund = fnd01FundService.selectOne(fund);
		model.addAttribute("fund", fund);
		model.addAttribute("fnd01FundTypeNmList", com02CodeService.codeList("FundType"));
		model.addAttribute("PublicCodeList", com02CodeService.codeList("PublicCode"));
		model.addAttribute("FundUnitCodeList", com02CodeService.codeList("FundUnitCode"));
		model.addAttribute("FundParentCodeList", com02CodeService.codeList("FundParentCode"));
		return "/fund/update_form";
	}
	@PostMapping("update")
	public String update(@ModelAttribute("fund")Fnd01Fund fund, Model model)throws UnsupportedEncodingException{
		log.debug("펀드종목정보 수정1");
		fnd01FundService.update(fund);
		String msg;
		msg = String.format("\"%s\" 펀드종목의 정보가 수정되었습니다.", fund.getFnd01FundNm());
		log.debug("펀드종목정보 수정완료");
		return "redirect:/fund/success?mode=update&fundCd=" + fund.getFnd01FundCd()+"&msg=" + URLEncoder.encode(msg,"UTF-8");
		
	}
	@GetMapping("delete")
	public String delete(@ModelAttribute("fund") Fnd01Fund fund)throws UnsupportedEncodingException{
		log.debug("펀드종목 삭제");
		fnd01FundService.delete(fund);
		return "redirect:/fund/list";
	}
	
}


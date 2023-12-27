package kr.co.kfs.assetedu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kfs.assetedu.model.Com01Corp;
import kr.co.kfs.assetedu.model.Fnd01Fund;
import kr.co.kfs.assetedu.model.PageAttr;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com01CorpService;
import kr.co.kfs.assetedu.service.Com02CodeService;
import kr.co.kfs.assetedu.service.Fnd01FundService;
import kr.co.kfs.assetedu.service.Sys01UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/popup")
public class PopupController {
	@Autowired
	Com01CorpService com01CorpService;
	
	@Autowired
	Fnd01FundService fnd01FundService;
	
	@Autowired
	Com02CodeService com02CodeService;
	
	@Autowired
	Sys01UserService sys01UserService;
	

	@GetMapping("corp")
	public String corp(String searchText
			,
			@RequestParam(value = "pageSize", defaultValue = "10", required=false) Integer pageSize
						, @RequestParam(value="currentPageNo" ,defaultValue="1", required = false) Integer currentPageNo 
						, @RequestParam(value="selectCorpType", required=false) String selectCorpType, Model model) {
		log.debug("기관정보(팝업)");
		log.debug("selectCorpType : { } " ,selectCorpType);
		
		QueryAttr queryAttr = new QueryAttr(); //검색조건
		queryAttr.put("selectCorpType", selectCorpType);
		queryAttr.put("searchText", searchText);
		Long totalItemCount = com01CorpService.selectCount(queryAttr);
		PageAttr pageAttr = new PageAttr(totalItemCount, pageSize, currentPageNo);
		queryAttr.put("pageAttr", pageAttr);
		List<Com01Corp> corpList = com01CorpService.selectList(queryAttr);
		model.addAttribute("corpList", corpList);
		model.addAttribute("pageAttr", pageAttr);
		model.addAttribute("searchText", searchText);
		model.addAttribute("selectCorpType", selectCorpType);
		return "/popup/popup_corp";
	}
	@GetMapping("fund")
	public String fund(String searchText
						,@RequestParam(value="pageSize", defaultValue= "10", required=false) Integer pageSize
						,@RequestParam(value="currentPageNo", defaultValue="1", required= false) Integer currentPageNo
						,@RequestParam(value="fundParentCode",  required=false) String fundParentCode, Model model) {
		log.debug("펀드정보(팝업)");
		QueryAttr queryAttr = new QueryAttr(); //검색조건
		queryAttr.put("searchText", searchText);
		queryAttr.put("fundParentCode", fundParentCode);
		Long totalItemCount = fnd01FundService.selectCount(queryAttr);
		PageAttr pageAttr = new PageAttr(totalItemCount, pageSize, currentPageNo);
		queryAttr.put("pageAttr", pageAttr);
		List<Fnd01Fund> fundList = fnd01FundService.selectList(queryAttr);
		model.addAttribute("fundList", fundList);
		model.addAttribute("pageAttr", pageAttr);
		model.addAttribute("searchText", searchText);
		return "/popup/popup_fund";
		
	}
/*	@GetMapping("insert")
	public String insert(Model model) {
		log.debug("종목정보 입력페이지");
		model.addAttribute("item", new Itm01Item());
		model.addAttribute("stkListTypeList", com02CodeService.codeList("ListType"));
		model.addAttribute("marketTypeList", com02CodeService.codeList("MarketType"));
		model.addAttribute("stkTypeList", com02CodeService.codeList("StkType"));
		
		return "/item/insert_form";
	}

	@PostMapping("insert")
	public String insert(@Valid @ModelAttribute("corp") Com01Corp corp, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) throws UnsupportedEncodingException {
		log.debug("기관정보등록");

	if (bindingResult.hasErrors()) {
		model.addAttribute("corpTypeList", com02CodeService.corpTypeList("CorpType"));
			return "/corp/insert_form";
		}
		String msg;

		// 유효성 검사//
	Com01Corp corpCheck = com01CorpService.selectOne(corp);
	if (corpCheck != null) {
			log.debug("기관코드 중복.");
			msg = String.format("\"%s\" 코드는 이미 \"%s\" 기관으로 등록되어 있습니다.", corpCheck.getCom01CorpCd(),
		bindingResult.addError(new FieldError("", "", msg));
			model.addAttribute("corpTypeList", com02CodeService.corpTypeList("CorpType"));
			return "/corp/insert_form";

		} else {
			int affectedCount = com01CorpService.insert(corp);
			log.debug("등록된 기관정보 숫자 : {}", affectedCount);

			msg = String.format("\"%s\" 기관정보가 등록되었습니다", corp.getCom01CorpNm());
			redirectAttr.addAttribute("mode", "insert");
			redirectAttr.addAttribute("msg", msg);
			return "redirect:/corp/success";
		}

	}

/*	@GetMapping("success")
	public String success(String msg, String mode, String corpCd, Model model) {
		model.addAttribute("pageTitle", "기관정보등록/수정완료");
		model.addAttribute("msg", msg);
		model.addAttribute("mode", mode);
		model.addAttribute("corpCd", corpCd);
		log.debug("기관정보등록 화면");
		return "/corp/success";
	}
	@GetMapping("update")
	public String update( @ModelAttribute("corp") Com01Corp corp, Model model)throws UnsupportedEncodingException {
		log.debug("업데이트페이지로이동");
		corp = com01CorpService.selectOne(corp);
		model.addAttribute("corpTypeList", com02CodeService.corpTypeList("CorpType"));
		model.addAttribute("corp", corp);
		return "/corp/update_form";
	}
	@PostMapping("update")
	public String update_form(@ModelAttribute("corp") Com01Corp corp,  Model model)throws UnsupportedEncodingException {
		log.debug("기관정보수정");
		com01CorpService.update(corp);
		String msg;
		msg = String.format("\"%s\" 기관정보가 수정되었습니다", corp.getCom01CorpNm());
		log.debug("업데이트 수정 완료");
		return "redirect:/corp/success?mode=update&corpCd=" + corp.getCom01CorpCd()+"&msg=" + URLEncoder.encode(msg,"UTF-8");
		
	}
	@GetMapping("delete")
	public String delete(@ModelAttribute("corp") Com01Corp corp){
		int deletedCount = com01CorpService.delete(corp);
 		log.debug("삭제된 기관정보 숫자 : {}" , deletedCount);
		
		if(deletedCount > 0) {
			log.debug("{}의  기관정보가 삭제되었습니다.", corp.getCom01CorpNm());
		}
		return "redirect:/corp/list";
		}*/
	
	}

package kr.co.kfs.assetedu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

/*	@PostMapping("insert")
	public String insert(@Valid @ModelAttribute("item") Itm01Item item, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) throws UnsupportedEncodingException {
		log.debug("종목정보등록");
		String msg;
		// 유효성 검사//
		Itm01Item itemCheck = itm01ItemService.selectOne(item);
	if (itemCheck != null) {
			log.debug("종목코드 중복.");
			msg = String.format("\"%s\" 코드는 이미 \"%s\" 기관으로 등록되어 있습니다.", itemCheck.getItm01ItemCd()
					,	itemCheck.getItm01ItemNm());
			bindingResult.addError(new FieldError("", "", msg));
			return "/item/insert_form";

		} else {
			log.debug("등록된 종목정보 : {} ", item);
			log.debug("등록된 종목정보 : {} ", item.getItm01ListType());
			log.debug("등록된 종목정보 : {} ", item.getItm01MarketType());
			log.debug("등록된 종목정보 : {} ", item.getItm01StkType());
			log.debug("등록된 종목정보 : {} ", item.getItm01IssType());
			int affectedCount = itm01ItemService.insert(item);
			log.debug("등록된 종목정보 : {} ", item);
			log.debug("등록된 종목정보 숫자 : {}", affectedCount);
			msg = String.format("\"%s\" 종목정보가 등록되었습니다", item.getItm01ItemNm());
			redirectAttr.addAttribute("mode", "insert");
			redirectAttr.addAttribute("msg", msg);
			return "redirect:/item/success";
		}

	}

	@GetMapping("success")
	public String success(String msg, String mode, String itemCd, Model model) {
		model.addAttribute("pageTitle", "기관정보등록 / 수정완료");
		model.addAttribute("msg", msg);
		model.addAttribute("mode", mode);
		model.addAttribute("itemCd", itemCd);
		log.debug("기관정보완료 화면");
		return "/item/success";
	}
	@GetMapping("update")
	public String update(@ModelAttribute("item") Itm01Item item, Model model)throws UnsupportedEncodingException {
		log.debug("업데이트페이지로이동");
		log.debug("등록된 종목정보 : {} ", item.getItm01ItemCd());
		item = itm01ItemService.selectOne(item);
		model.addAttribute("corpTypeList", com02CodeService.codeList("CorpType"));
		model.addAttribute("stkListTypeList", com02CodeService.codeList("ListType"));
		model.addAttribute("marketTypeList", com02CodeService.codeList("MarketType"));
		model.addAttribute("stkTypeList", com02CodeService.codeList("StkType"));
		model.addAttribute("item", item);
		return "/item/update_form";
	}
	@PostMapping("update")
	public String update_form(@ModelAttribute("item") Itm01Item item,  Model model)throws UnsupportedEncodingException {
		log.debug("기관정보수정");
		itm01ItemService.update(item);
		String msg;
		msg = String.format("\"%s\" 기관정보가 수정되었습니다", item.getItm01ItemNm());
		log.debug("업데이트 수정 완료");
		return "redirect:/item/success?mode=update&itemCd=" + item.getItm01ItemCd()+"&msg=" + URLEncoder.encode(msg,"UTF-8");
		
	}
	@GetMapping("delete")
	public String delete(@ModelAttribute("item") Itm01Item item){
		int deletedCount = itm01ItemService.delete(item);
 		log.debug("삭제된 기관정보 숫자 : {}" , deletedCount);
		if(deletedCount > 0) {
			log.debug("{}의  기관정보가 삭제되었습니다.", item.getItm01ItemNm());
		}
		return "redirect:/item/list";
		}*/
	
	}

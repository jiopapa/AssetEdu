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

import kr.co.kfs.assetedu.model.Com01Corp;
import kr.co.kfs.assetedu.model.Itm01Item;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com01CorpService;
import kr.co.kfs.assetedu.service.Com02CodeService;
import kr.co.kfs.assetedu.service.Itm01ItemService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/item")
public class ItemController {
	@Autowired
	Itm01ItemService itm01ItemService;
	@Autowired
	Com02CodeService com02CodeService;


	@GetMapping("list")
	public String list(String searchText, Model model) {
		log.debug("종목정보");

		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText", searchText);
		List<Itm01Item> itemList = itm01ItemService.selectList(queryAttr);
		model.addAttribute("itemList", itemList);
		return "/item/itemList";
	}

	@GetMapping("insert")
	public String insert(Model model) {
		log.debug("종목정보 입력페이지");
		model.addAttribute("item", new Itm01Item());
		model.addAttribute("stkListTypeList", com02CodeService.codeList("ListType"));
		model.addAttribute("marketTypeList", com02CodeService.codeList("MarketType"));
		model.addAttribute("stkTypeList", com02CodeService.codeList("StkType"));
		
		return "/item/insert_form";
	}

	@PostMapping("insert")
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

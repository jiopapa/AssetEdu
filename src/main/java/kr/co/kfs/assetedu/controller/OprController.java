package kr.co.kfs.assetedu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

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
import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com01CorpService;
import kr.co.kfs.assetedu.service.Com02CodeService;
import kr.co.kfs.assetedu.service.Opr01ContService;
import kr.co.kfs.assetedu.servlet.exception.AssetException;
import kr.co.kfs.assetedu.utils.AssetUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/opr")
public class OprController {
	@Autowired
	Opr01ContService contService;
	
	@Autowired
	Com02CodeService codeService;

	@GetMapping("buy_list")
	public String list(String frDate, String toDate, String searchText, Model model) {
		log.debug("매수 운용지시 리스트");
		
		model.addAttribute("pageTitle", "매수운용지시 - 리스트");
		
		if(Objects.isNull(frDate)) {
			frDate = AssetUtil.today();
		}else {
			frDate = AssetUtil.removeDash(frDate);
		}
		if(Objects.isNull(toDate)) {
			toDate = AssetUtil.today();
		}else {
			toDate = AssetUtil.removeDash(toDate);
		}
		
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText", searchText);
		queryAttr.put("frDate", frDate);
		queryAttr.put("toDate", toDate);
		queryAttr.put("pageType", "BUY");
		
		List<Opr01Cont> list = contService.selectList(queryAttr);
		model.addAttribute("list", list);
		model.addAttribute("frDate", AssetUtil.displayYmd(frDate));
		model.addAttribute("toDate", AssetUtil.displayYmd(toDate));
		return "/opr/buy_list";
	}

	@GetMapping("buy_insert")
	public String insert(Model model) {
		log.debug("매수 운용지시 등록 폼");
		Opr01Cont cont = new Opr01Cont();
		cont.setOpr01ContDate(AssetUtil.today());//체결일자 초기 set = today

		model.addAttribute("pageTitle", "매수운용지시 - 리스트");
		model.addAttribute("cont", cont);
		model.addAttribute("trCdLIst", codeService.trCodeList("BUY"));
		
		return "/Opr/buy_insert";
	}

	@PostMapping("buy_insert")
	public String insert(@Valid @ModelAttribute("cont") Opr01Cont cont, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) throws UnsupportedEncodingException {
		log.debug("매수운용지시 등록");

		if (bindingResult.hasErrors()) {
				model.addAttribute("trCdList", codeService.trCodeList("BUY"));
				return "/opr/buy_insert";
		}
		String msg;
		String resultMsg;
		
		try {
		resultMsg = contService.insert(cont);
		}
		catch(AssetException e) {
			resultMsg = e.getMessage();
		}
		catch(Exception e) {
			resultMsg = e.getMessage();
		}
		return resultMsg;
	}
//
//	@GetMapping("success")
//	public String success(String msg, String mode, String itemCd, Model model) {
	
//		model.addAttribute("pageTitle", "기관정보등록 / 수정완료");
//		model.addAttribute("msg", msg);
//		model.addAttribute("mode", mode);
//		model.addAttribute("itemCd", itemCd);
//		log.debug("기관정보완료 화면");
//		return "/item/success";
//	}

	}

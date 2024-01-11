package kr.co.kfs.assetedu.controller;

import java.io.UnsupportedEncodingException;
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

import kr.co.kfs.assetedu.model.Opr01Cont;
import kr.co.kfs.assetedu.model.QueryAttr;
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
	public String buyList(String frDate, String toDate, String searchText, Model model) {
		log.debug("매수 운용지시 리스트");

		model.addAttribute("pageTitle", "매수운용지시 - 리스트");

		if (Objects.isNull(frDate)) {
			frDate = AssetUtil.today();
		} else {
			frDate = AssetUtil.removeDash(frDate);
		}
		if (Objects.isNull(toDate)) {
			toDate = AssetUtil.today();
		} else {
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
	public String buyInsert(Model model) {
		log.debug("매수 운용지시 등록 폼");
		Opr01Cont cont = new Opr01Cont();
		cont.setOpr01ContDate(AssetUtil.today());// 체결일자 초기 set = today

		model.addAttribute("pageTitle", "매수운용지시 - 리스트");
		model.addAttribute("cont", cont);
		model.addAttribute("trCdLIst", codeService.trCodeList("BUY"));
		return "/opr/buy_insert_form";
	}

	@PostMapping("buy_insert")
	public String buyInsert(@Valid @ModelAttribute("cont") Opr01Cont cont, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) throws UnsupportedEncodingException {
		log.debug("매수운용지시 등록");

		if (bindingResult.hasErrors()) {
			model.addAttribute("trCdList", codeService.trCodeList("BUY"));
			model.addAttribute("pageTitle", "매수운용지시 - 리스트");
			model.addAttribute("cont", cont);
			log.debug("에러1");
			return "/opr/buy_insert_form";
		}
		String resultMsg = "";

		try {
			resultMsg = contService.insert(cont);
			log.debug(resultMsg);
		} catch (AssetException e) {
			log.debug("err----------AssetException");
			resultMsg = e.getMessage();
			log.debug(resultMsg);
		} catch (Exception e) {
			log.debug("err---------Exception");
			resultMsg = e.getMessage();
			if(resultMsg == null || resultMsg.length()< 1) {
				resultMsg = e.toString();
			}
			log.debug(resultMsg);
			
		}
		if (!"Y".equals(resultMsg)) {
			model.addAttribute("trCdLIst", codeService.trCodeList("BUY"));
			bindingResult.addError(new FieldError("", "", resultMsg+""));
			return "/opr/buy_insert_form";
		} else {
			String msg = String.format("\"%s %s주\" 매수처리가 완료되었습니다.", cont.getOpr01ItemNm(), cont.getOpr01Qty());
			redirectAttr.addAttribute("mode", "insert");
			redirectAttr.addAttribute("msg", msg);
			log.debug("완료페이지이동");
			return "redirect:/opr/buy_success";
		}

	}

//	@GetMapping("update")
//	public String update(@ModelAttribute("item") Itm01Item item, Model model) throws UnsupportedEncodingException {
//
//	}

	@GetMapping("buy_delete")
	public String buyDelete(@ModelAttribute("cont") Opr01Cont cont, Model model) throws UnsupportedEncodingException {
		cont = contService.selectOne(cont);
		model.addAttribute("cont", cont);
		return "/opr/buy_delete_form";

	}

	@PostMapping("buy_delete")
	public String buyDelete(@ModelAttribute("cont") Opr01Cont cont, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) throws UnsupportedEncodingException {
		log.debug("매수운용지시 취소처리");

		if (bindingResult.hasErrors()) {
			model.addAttribute("trCdList", codeService.trCodeList("BUY"));
			return "/opr/buy_delete";
		}
		String resultMsg = "";

		try {
		 cont = contService.selectOne(cont);
		 resultMsg = contService.delete(cont);
		} catch (AssetException e) {
			resultMsg = e.getMessage();
		} catch (Exception e) {
			resultMsg = e.getMessage();

		}
		if (!"Y".equals(resultMsg)) {
			model.addAttribute("trCdList", codeService.trCodeList("BUY"));
			bindingResult.addError(new FieldError("", "", resultMsg));
			return "/opr/buy_delete";
		} else {
			String msg = String.format("\"%s %s주\" 매수 취소처리가 완료되었습니다.", cont.getOpr01ItemNm(), cont.getOpr01Qty());
			redirectAttr.addAttribute("mode", "delete");
			redirectAttr.addAttribute("msg", msg);
			return "redirect:/opr/buy_success";
		}

	}



	@GetMapping("buy_success")
	public String buySuccess(String msg, String mode, String contId, Model model) {
		log.debug("매수 운용지시 성공화면 ");
		log.debug(msg);
		model.addAttribute("pageTitle", "매수처리");
		model.addAttribute("msg", msg);
		model.addAttribute("mode", mode);
		model.addAttribute("contId", contId);
		
		return "/opr/buy_success";
	}
	
	
	@GetMapping("sell_list")
	public String sellList(String searchText, String frDate, String toDate, Model model) {
		log.debug("매도운용지시 - 리스트");
		if (Objects.isNull(frDate)) {
			frDate = AssetUtil.today();
		} else {
			frDate = AssetUtil.removeDash(frDate);
		}
		if (Objects.isNull(toDate)) {
			toDate = AssetUtil.today();
		} else {
			toDate = AssetUtil.removeDash(toDate);
		}
		
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText", searchText);
		queryAttr.put("frDate", frDate);
		queryAttr.put("toDate", toDate);
		queryAttr.put("pageType", "SELL");
		
		List<Opr01Cont> list = contService.selectList(queryAttr);
		 
		model.addAttribute("pageTitle", "매도운용지시 - 리스트");
		model.addAttribute("searchText", searchText);
		model.addAttribute("toDate",  AssetUtil.displayYmd(toDate));
		model.addAttribute("frDate",  AssetUtil.displayYmd(frDate));
		model.addAttribute("list", list);
		
		return "/opr/sell_list";
	}
	@GetMapping("sell_insert")
	public String sellInsert(Model model) {
		log.debug("매도 운용지시 등록 폼");
		Opr01Cont cont = new Opr01Cont();
		cont.setOpr01ContDate(AssetUtil.today());// 체결일자 오늘로 셋팅

		model.addAttribute("pageTitle", "매도운용지시 - 리스트");
		model.addAttribute("cont", cont);
		model.addAttribute("trCdLIst", codeService.trCodeList("SELL"));
		
		return "/opr/sell_insert_form";
	}
	@PostMapping("sell_insert")
	public String sellInsert(@Valid @ModelAttribute("cont") Opr01Cont cont, BindingResult bindingResult
			,RedirectAttributes redirectAttr
			, Model model) throws UnsupportedEncodingException {
		log.debug("매도운용지시 등록");

		if (bindingResult.hasErrors()) {
			model.addAttribute("trCdList", codeService.trCodeList("SELL"));
			model.addAttribute("pageTitle", "매도운용지시 - 리스트");
			model.addAttribute("cont", cont);
			log.debug("cont : {}", cont);
			log.debug("에러1");
			return "redirect:opr/sell_insert";
		}
		String resultMsg = "";
		try {
			resultMsg = contService.insert(cont);
			log.debug(resultMsg);
		} catch (AssetException e) {
			log.debug("err----------AssetException");
			resultMsg = e.getMessage();
			log.debug(resultMsg);
		} catch (Exception e) {
			log.debug("err---------Exception");
			resultMsg = e.getMessage();
			if(resultMsg == null || resultMsg.length()< 1) {
				resultMsg = e.toString();
			}
			log.debug(resultMsg);
			
		}
		if (!"Y".equals(resultMsg)) {
			model.addAttribute("trCdLIst", codeService.trCodeList("SELL"));
			bindingResult.addError(new FieldError("", "", resultMsg+""));
			return "/opr/sell_insert_form";
		} else {
			String msg = String.format("\"%s %s주\" 매도처리가 완료되었습니다.", cont.getOpr01ItemNm(), cont.getOpr01Qty());
			redirectAttr.addAttribute("mode", "insert");
			redirectAttr.addAttribute("msg", msg);
			log.debug("완료페이지이동");
			
			return "redirect:/opr/sell_success";
		}

	}
	@GetMapping("sell_success")
	public String sellSuccess(String msg, String mode, Long contId, Model model) {
		log.debug("매도 완료 화면");
		model.addAttribute("pageTitle","매도처리");
		model.addAttribute("msg", msg);
		model.addAttribute("mode", mode);
		model.addAttribute("contId", contId);
		return "/opr/sell_success";
	}
	@GetMapping("sell_delete")
	public String sellDelete(@ModelAttribute("cont") Opr01Cont cont, Model model) throws UnsupportedEncodingException {
		cont = contService.selectOne(cont);
		model.addAttribute("cont", cont);
		return "/opr/sell_delete_form";

	}
	@PostMapping("sell_delete")
	public String sellDelete(@ModelAttribute("cont") Opr01Cont cont, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) throws UnsupportedEncodingException {
		log.debug("매도운용지시 - 취소처리");

		if (bindingResult.hasErrors()) {
			model.addAttribute("trCdList", codeService.trCodeList("SELL"));
			return "/opr/sell_delete";
		}
		String resultMsg = "";

		try {
		 cont = contService.selectOne(cont);
		 resultMsg = contService.delete(cont);
		} catch (AssetException e) {
			resultMsg = e.getMessage();
		} catch (Exception e) {
			resultMsg = e.getMessage();

		}
		if (!"Y".equals(resultMsg)) {
			model.addAttribute("trCdList", codeService.trCodeList("SELL"));
			bindingResult.addError(new FieldError("", "", resultMsg));
			return "/opr/buy_delete";
		} else {
			String msg = String.format("\"%s %s주\" 매도 취소처리가 완료되었습니다.", cont.getOpr01ItemNm(), cont.getOpr01Qty());
			redirectAttr.addAttribute("mode", "delete");
			redirectAttr.addAttribute("msg", msg);
			return "redirect:/opr/sell_success";
		}

	}

}

//
//	

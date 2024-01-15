package kr.co.kfs.assetedu.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.kfs.assetedu.model.Bok01Book;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Bok01BookService;
import kr.co.kfs.assetedu.service.Opr01ContService;
import kr.co.kfs.assetedu.servlet.exception.AssetException;
import kr.co.kfs.assetedu.utils.AssetUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	Bok01BookService bookService;
	@Autowired
	private Opr01ContService contService;
	

	@GetMapping("list")
	public String  main(String searchText
						,String frHoldDate 
						,String toHoldDate
						,Model model) throws Exception {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(frHoldDate == null) {
			frHoldDate = LocalDate.now().format(dateFormatter);
		}else {
			frHoldDate = LocalDate.parse(frHoldDate, dateFormatter2).format(dateFormatter);
		}
		if(toHoldDate == null){
		    toHoldDate = LocalDate.now().format(dateFormatter);
		}else {
			toHoldDate = LocalDate.parse(toHoldDate, dateFormatter2).format(dateFormatter);
		}
		
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText",searchText );
		queryAttr.put("frHoldDate", frHoldDate);
		queryAttr.put("toHoldDate", toHoldDate);
		List<Bok01Book>list = bookService.selectList(queryAttr);
		String frHoldDate2 = LocalDate.parse(frHoldDate, dateFormatter).format(dateFormatter2);
		String toHoldDate2 = LocalDate.parse(toHoldDate, dateFormatter).format(dateFormatter2);
		
		model.addAttribute("list", list);
		model.addAttribute("frHoldDate", frHoldDate2);
		model.addAttribute("toHoldDate", toHoldDate2);
		return "/book/list";
	}
	@GetMapping("eval_list")
	public String  eval_list(String searchText
							,String evalDate
							,Model model) throws Exception {
		if(Objects.isNull(evalDate)) {
			evalDate = AssetUtil.today();
		} else {
			evalDate = AssetUtil.removeDash(evalDate);
		}
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText",searchText );
		queryAttr.put("evalDate", evalDate);
		log.debug("evalDate : {} ", evalDate);
		
		List<Bok01Book>list = bookService.selectEvalList(queryAttr);
		
		model.addAttribute("list", list);
		model.addAttribute("evalDate",AssetUtil.displayYmd(evalDate));
		return "/book/eval_list";
	}
	
	@GetMapping("eval_insert")
	public String evalInsert(String searchText, String evalDate, Model model) {
		log.debug("보유주식 평가처리");
		log.debug("평가일자 : {}", evalDate);
		
		String resultMsg = "";
		
		if(Objects.isNull(evalDate) || "".equals(evalDate)){
			resultMsg = "평가일자를 입력하세요.";
			model.addAttribute("resultErrMsg", resultMsg);
			return "/book/eval_list";
		}else {
			evalDate = AssetUtil.removeDash(evalDate);
		}
		//평가처리
		try {
			resultMsg = contService.eval("P", evalDate);
		} catch (AssetException e) {
			model.addAttribute("resultErrMsg", e.getMessage());
		}
		catch (Exception e) {
			if(e.getMessage() == null) {
				model.addAttribute("resultErrMsg", e.toString());
			}
			model.addAttribute("resultErrMsg", e.getMessage());
		}
		
		// 처리결과 조회
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("evalDate",evalDate);
		queryAttr.put("searchText", searchText);
		
		List<Bok01Book> list = bookService.selectEvalList(queryAttr);
		
		model.addAttribute("evalDate",AssetUtil.displayYmd(evalDate));
		model.addAttribute("list", list);
		
		return"/book/eval_list";
		
	}
	
	@GetMapping("eval_cancel")
	public String eval_cancel(String searchText, String evalDate, Model model) {
		log.debug("보유주식 평가 취소처리");
		log.debug("평가일자 : {}", evalDate);
		
		String resultMsg = "";
		
		if(Objects.isNull(evalDate) || "".equals(evalDate)){
			resultMsg = "평가일자를 입력하세요.";
			model.addAttribute("resultErrMsg", resultMsg);
			return "/book/eval_list";
		}else {
			evalDate = AssetUtil.removeDash(evalDate);
		}
		//평가처리
		try {
			resultMsg = contService.eval("C", evalDate);
		} catch (AssetException e) {
			model.addAttribute("resultErrMsg", e.getMessage());
		}
		catch (Exception e) {
			if(e.getMessage() == null) {
				model.addAttribute("resultErrMsg", e.toString());
			}
			model.addAttribute("resultErrMsg", e.getMessage());
		}
		
		// 처리결과 조회
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("evalDate",evalDate);
		queryAttr.put("searchText", searchText);
		
		List<Bok01Book> list = bookService.selectEvalList(queryAttr);
		
		model.addAttribute("evalDate",AssetUtil.displayYmd(evalDate));
		model.addAttribute("list", list);
		
		return"/book/eval_list";
		
	}
	
}

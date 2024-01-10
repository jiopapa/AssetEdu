package kr.co.kfs.assetedu.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.kfs.assetedu.model.Bok01Book;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.repository.Bok01BookRepository;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	Bok01BookRepository bookRepository;

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
		List<Bok01Book>list = bookRepository.selectList(queryAttr);
		String frHoldDate2 = LocalDate.parse(frHoldDate, dateFormatter).format(dateFormatter2);
		String toHoldDate2 = LocalDate.parse(toHoldDate, dateFormatter).format(dateFormatter2);
		
		model.addAttribute("list", list);
		model.addAttribute("frHoldDate", frHoldDate2);
		model.addAttribute("toHoldDate", toHoldDate2);
		return "/book/list";
	}
	
}

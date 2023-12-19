package kr.co.kfs.assetedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.kfs.assetedu.model.Student;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TestController {
	

	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public String displayJsp() {
		log.debug("-------------------in TestController --------------------");
		log.debug("-------------------in TestController --------------------");
		log.debug("-------------------in TestController --------------------");
		
		return "/test/test1";
	}
	//client로 데이터받는 방법1
	//@RequestMapping(value = "/test1", method = RequestMethod.POST)
	//public String receiveClientData1(String name, Integer age, Long asset, @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate birth) {
	//	log.debug("name : {}", name);
	//	log.debug("age : {}", age);
	//	log.debug("asset : {}", asset);
	//	log.debug("birth : {}", birth);
	//		return "/test/test2";
	//}
	@RequestMapping(value = "/test1", method=RequestMethod.POST)
	public String receiveClientData1(@ModelAttribute("student1")Student student, Model model) {
		log.debug("-------------------in TestController --------------------");
		log.debug("student : {}", student);
		log.debug("------------------------------------------------------------");
		model.addAttribute("result1", "ModelAttribute사용");
		return "/test/test2";
	}
	
}

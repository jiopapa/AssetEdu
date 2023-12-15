package kr.co.kfs.assetedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/user")
public class UserController {
	
	@GetMapping("list")
	public String list(String searchText, Model model) {
		log.debug("★★★★★★★★★★★★★★★★★★★★★★");
		log.debug("사용자리스트");
		log.debug("★★★★★★★★★★★★★★★★★★★★★★");
		
		return "/admin/user/list";
	}
	
}

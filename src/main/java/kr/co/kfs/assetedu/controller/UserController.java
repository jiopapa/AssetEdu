package kr.co.kfs.assetedu.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.kfs.assetedu.model.Sys01User;
import kr.co.kfs.assetedu.service.Sys01UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/user")
public class UserController {
	@Autowired
	private Sys01UserService sys01UserService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	
	@GetMapping("success")
	public String success(String msg, String mode, String userId, Model model) {
		model.addAttribute("pageTitle", "사용자 추가");
		model.addAttribute("msg", msg);
		model.addAttribute("mode", mode);
		model.addAttribute("userId", userId);
		log.debug("사용자 추가 화면");
		return"/admin/user/success";
	}
	@GetMapping("list")
	public String list(String searchText, Model model) {
		log.debug("★★★★★★★★★★★★★★★★★★★★★★");
		log.debug("사용자리스트");
		log.debug("★★★★★★★★★★★★★★★★★★★★★★");
		List<Sys01User> list =sys01UserService.selectList(null);
		model.addAttribute("list", list);
		return "/admin/user/list";
	}
	@GetMapping("insert")
	public String Insert(Model model) {
		log.debug("사용자등록");
		model.addAttribute("pageTitle", "사용자 추가");
		model.addAttribute("user", new Sys01User());
		return "/admin/user/insert_form";
	}
	@PostMapping("insert")
	public String Insert(@Valid @ModelAttribute Sys01User user, RedirectAttributes redirectAttr) {
		log.debug("사용자정보 저장하고 리스트로 이동");
		String pwd = user.getSys01Pwd();
		
		user.setSys01Pwd(passwordEncoder.encode(pwd));
		int affectedCount = sys01UserService.insert(user);
		log.debug("DB에 적용된 갯수 : {}", affectedCount);
		String msg = String.format("사용자 %s 님이 추가 되었습니다.", user.getSys01UserNm());
		redirectAttr.addAttribute("mode", "insert");
		redirectAttr.addAttribute("msg", msg);
		return "redirect:/admin/user/success";
	}
}

package kr.co.kfs.assetedu.controller;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.kfs.assetedu.model.PageAttr;
import kr.co.kfs.assetedu.model.QueryAttr;
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
	public String list(String searchText
						, @RequestParam(value="pageSize", defaultValue="10", required=false) Integer pageSize
						, @RequestParam(value="currentPageNo" ,defaultValue="1", required = false) Integer currentPageNo
						, Model model) {
		log.debug("사용자리스트");
		model.addAttribute("pageTitle", "사용자리스트");
		
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText", searchText);
		
		Long totalItemCount = sys01UserService.selectCount(queryAttr);
		PageAttr pageAttr = new PageAttr(totalItemCount, pageSize, currentPageNo);
		queryAttr.put("pageAttr", pageAttr);
		List<Sys01User> list =sys01UserService.selectList(queryAttr);
		model.addAttribute("user", list);
		model.addAttribute("pageAttr", pageAttr);
		model.addAttribute("searchText", searchText);
		
		log.debug("pageAttr:{}", pageAttr);
		return "/admin/user/list";
	}
	@GetMapping("insert")
	public String Insert(Model model) {
		log.debug("사용자등록");
		model.addAttribute("pageTitle", "사용자 추가");
		model.addAttribute("user", new Sys01User());
		return "/admin/user/insert_form";
	}
	@PostMapping("insert") //실제 저장할때 호출되는 url
	public String Insert(@Valid @ModelAttribute Sys01User user, RedirectAttributes redirectAttr) {
		log.debug("사용자정보 저장하고 리스트로 이동");
		String pwd = user.getSys01Pwd(); //비밀번호 암호화
		user.setSys01Pwd(passwordEncoder.encode(pwd));
		
		int affectedCount = sys01UserService.insert(user);
		log.debug("DB에 적용된 갯수 : {}", affectedCount);
		
		String msg = String.format("사용자 %s 님이 추가 되었습니다.", user.getSys01UserNm());
		redirectAttr.addAttribute("mode", "insert");
		redirectAttr.addAttribute("msg", msg);
		return "redirect:/admin/user/success";
	}
	
	@GetMapping("update")
	public String update(@ModelAttribute("user") Sys01User user, Model model) {
		log.debug("사용자정보 수정 폼 표시");
		user = sys01UserService.selectOne(user);
		model.addAttribute("user",user);
		return "admin/user/update_form";
	}
	@PostMapping("update")
	public String update_form(@ModelAttribute("user") Sys01User user, RedirectAttributes redirectAttr) throws UnsupportedEncodingException  {
		log.debug("사용자 정보 수정 ");
		String pwd = user.getSys01Pwd();
		user.setSys01Pwd(passwordEncoder.encode(pwd));
		sys01UserService.update(user);
		
		String msg = String.format("사용자 %s 님의 정보가 수정되었습니다.", user.getSys01UserNm());
		redirectAttr.addAttribute("mode", "update");
		redirectAttr.addAttribute("userId", user.getSys01UserId());
		redirectAttr.addAttribute("msg", msg);
		return "redirect:/admin/user/success";
	}
	@GetMapping("delete")
	public String delete(@ModelAttribute("user") Sys01User user) {
		log.debug("사용자 정보 삭제 id : " + user.getSys01UserId());
		
		int deletedCount = sys01UserService.delete(user);
		if(deletedCount >0 ) {
			log.warn("사용자 id : {}가 삭제되었습니다");
		}
		return "redirect:/admin/user/list";
		
		}
		
	}
	

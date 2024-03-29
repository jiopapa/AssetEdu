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
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com01CorpService;
import kr.co.kfs.assetedu.service.Com02CodeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/corp")
public class CorpController {
	@Autowired
	Com01CorpService com01CorpService;
	@Autowired
	Com02CodeService com02CodeService;

	@GetMapping("list")
	public String list(String searchText, Model model) {
		log.debug("기관정보리스트");

		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText", searchText);
		List<Com01Corp> CorpList = selectCorps(queryAttr);
		model.addAttribute("CorpList", CorpList);
		return "/corp/corpList";
	}

	private List<Com01Corp> selectCorps(QueryAttr queryAttr) {
		Com01Corp corp = new Com01Corp();
		queryAttr.putClass(corp);
		List<Com01Corp> list = com01CorpService.selectList(queryAttr);
		return list;
	}

	@GetMapping("insert")
	public String insert(Model model) {
		log.debug("기관정보입력페이지 , 기관구분리스트 포함");
		model.addAttribute("corp", new Com01Corp());
		model.addAttribute("corpTypeList", com02CodeService.codeList("CorpType"));
		return "/corp/insert_form";
	}

	@PostMapping("insert")
	public String insert(@Valid @ModelAttribute("corp") Com01Corp corp, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) throws UnsupportedEncodingException {
		log.debug("기관정보등록");

		if (bindingResult.hasErrors()) {
			model.addAttribute("corpTypeList", com02CodeService.codeList("CorpType"));
			return "/corp/insert_form";
		}
		String msg;

		// 유효성 검사//
		Com01Corp corpCheck = com01CorpService.selectOne(corp);
		if (corpCheck != null) {
			log.debug("기관코드 중복.");
			msg = String.format("\"%s\" 코드는 이미 \"%s\" 기관으로 등록되어 있습니다.", corpCheck.getCom01CorpCd(),
					corpCheck.getCom01CorpNm());
			bindingResult.addError(new FieldError("", "", msg));
			model.addAttribute("corpTypeList", com02CodeService.codeList("CorpType"));
			return "/corp/insert_form";

		} else {
			int affectedCount = com01CorpService.insert(corp);
			log.debug("등록된 기관정보 숫자 : {}", affectedCount);

			msg = String.format("\"%s\" 기관정보가 등록되었습니다", corp.getCom01CorpNm());
			redirectAttr.addAttribute("mode", "insert");
			redirectAttr.addAttribute("msg", msg);
			return "redirect:/corp/success";
		}

	}

	@GetMapping("success")
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
		model.addAttribute("corpTypeList", com02CodeService.codeList("CorpType"));
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
		}
	
	}

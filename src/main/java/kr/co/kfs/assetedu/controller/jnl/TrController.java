package kr.co.kfs.assetedu.controller.jnl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kfs.assetedu.model.ApiData;
import kr.co.kfs.assetedu.model.Jnl12Tr;
import kr.co.kfs.assetedu.model.Jnl13TrMap;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com02CodeService;
import kr.co.kfs.assetedu.service.Jnl12TrService;
import kr.co.kfs.assetedu.service.Jnl13TrMapService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/jnl/trma2p")
public class TrController {
	@Autowired
	Jnl12TrService jnl12TrService;
	
	@Autowired
	Jnl13TrMapService jnl13TrMapService;
	
	@Autowired
	Com02CodeService com02CodeService;
	
	@GetMapping("list")
	public String list (String searchText
						, Model model) {
		log.debug("분개매핑 리스트");
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("searchText", searchText);
		
		com02CodeService.codeList(searchText);
		List<Jnl12Tr> list = jnl12TrService.selectList(queryAttr);
		model.addAttribute("list12", list);
		return "jnl/tr/list";
		
	}
	@ResponseBody
	@GetMapping("find")
	public String selectList(@RequestParam(value="jnl12TrCd") String jnl12TrCd ){
		log.debug("{}",jnl12TrCd );
		
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.putClass(jnl12TrCd);
		
		List<Jnl12Tr> list = jnl12TrService.selectList(queryAttr);
		
		ApiData apiData = new ApiData();
		apiData.put("list",list);
		apiData.put("jnl12TrCd", jnl12TrCd);

		//공통코드의 이름을 찾는다
		queryAttr.clear();
		queryAttr.put("jnl12TrCd", jnl12TrCd);
		
		Jnl12Tr jnl12Tr = new Jnl12Tr();
		jnl12Tr.setJnl12TrCd(jnl12TrCd);

		Jnl12Tr trCodeNm = jnl12TrService.selectOne(jnl12Tr);
		log.debug("{}",jnl12Tr);
		log.debug("{}",trCodeNm);
		
		apiData.put("codeNm", trCodeNm.getJnl12TrNm());
		
		String json = apiData.toJson();
		return json;
	}
	@GetMapping("/jnl13/{jnl13TrCd}")
	public String listJnl13(@PathVariable("jnl13TrCd") String jnl13TrCd) {
		log.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		log.debug("jnl13 리스트  id : {}",jnl13TrCd);
		log.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		Jnl13TrMap jnl13TrMap = new Jnl13TrMap();
		jnl13TrMap.setJnl13TrCd(jnl13TrCd);
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.put("jnl13TrCd", jnl13TrCd);
		log.debug("queryAttr : {}", queryAttr);
		log.debug("jnl13TrCd : {}", jnl13TrCd);

		List<Jnl13TrMap> list = jnl13TrMapService.selectList(queryAttr);
		
		ApiData apiData = new ApiData();
		apiData.put("list", list);
		apiData.put("jnl13TrCd", jnl13TrCd);

		
		String json = apiData.toJson();
		return json;
	}
/*	
		
		
		List<Jnl11ReprAcnt> list = jnl11ReprAcntService.selectList(queryAttr);
		model.addAttribute("list", list);
		model.addAttribute("pageAttr", pageAttr);
		model.addAttribute("searchText", searchText);
		return "/jnl/repr-acnt/repr_acnt_list";
	}
	
	@GetMapping("insert")
	public String insert_form(Model model) {
		model.addAttribute("jnl11ReprAcnt", new Jnl11ReprAcnt());
		model.addAttribute("acntAttrCodeList", com02CodeService.codeList("AcntAttrCode"));
		return "/jnl/repr-acnt/insert_form";
	}
	
	@PostMapping("insert")
	public String insert(@Valid @ModelAttribute("jnl11ReprAcnt") Jnl11ReprAcnt jnl11ReprAcnt, BindingResult bindingResult
						, RedirectAttributes redirectAttributes, Model model){
		log.debug("대표계정코드 등록");
		String msg;
		Jnl11ReprAcnt reprAcntCheck = jnl11ReprAcntService.selectOne(jnl11ReprAcnt);
		
		if(reprAcntCheck != null) {
			log.debug("대표계정코드 중복");
			msg = String.format("\"%s\" 코드는 이미 \"%s\" 대표계정으로 등록되어 있습니다.", jnl11ReprAcnt.getJnl11ReprAcntCd()
								, reprAcntCheck.getJnl11ReprAcntNm());
			model.addAttribute("jnl11ReprAcnt", jnl11ReprAcnt);
			model.addAttribute("acntAttrCodeList", com02CodeService.codeList("AcntAttrCode"));
			bindingResult.addError(new FieldError("", "", msg));
			return "/jnl/repr-acnt/insert_form";
		}else {
			int insertCount = jnl11ReprAcntService.insert(jnl11ReprAcnt);
			log.debug("입력된 대표계정 {} ", insertCount);
			redirectAttributes.addFlashAttribute("mode", "insert");
			redirectAttributes.addFlashAttribute("jnl11ReprAcnt", jnl11ReprAcnt);
			log.debug("jnl11ReprAcnt : {} ", jnl11ReprAcnt);
			return "redirect:/jnl/repr-acnt/success";
		}
	}
	@GetMapping("success")
	public String success(Model model) {
		return "/jnl/repr-acnt/success";
	}
	@GetMapping("/update/{jnl11ReprAcntCd}")
	public String update_form(@PathVariable("jnl11ReprAcntCd") String jnl11ReprAcntCd, Model model) {
		Jnl11ReprAcnt reprAcnt = jnl11ReprAcntService.selectOne(jnl11ReprAcntCd);
		model.addAttribute("jnl11ReprAcnt", reprAcnt);
		model.addAttribute("acntAttrCodeList", com02CodeService.codeList("AcntAttrCode"));
		return "/jnl/repr-acnt/update_form";	
	}
	@PostMapping("update")
	public String update(String msg, @ModelAttribute("jnl11ReprAcnt") Jnl11ReprAcnt jnl11ReprAcnt, Model model
						, RedirectAttributes redirectAttributes) {
		int count = jnl11ReprAcntService.update(jnl11ReprAcnt);
		log.debug("수정 카운트  : {} ", count);
		msg = String.format("\"%s\"대표계정코드가 수정되었습니다.", jnl11ReprAcnt.getJnl11ReprAcntNm());
		redirectAttributes.addFlashAttribute("mode", "update");
		redirectAttributes.addFlashAttribute("jnl11ReprAcnt", jnl11ReprAcnt);
		return "redirect:/jnl/repr-acnt/success";
	}
	@GetMapping("/delete/{jnl11ReprAcntCd}")
	public String delete(@PathVariable("jnl11ReprAcntCd") String jnl11ReprAcntCd) {
		int count = jnl11ReprAcntService.delete(jnl11ReprAcntCd);
		log.debug("삭제 카운트 : {} ", count);
		return "redirect:/jnl/repr-acnt/list";
	}
	


	
	
*/

}

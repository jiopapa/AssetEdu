package kr.co.kfs.assetedu.controller.jnl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.kfs.assetedu.model.ApiData;
import kr.co.kfs.assetedu.model.Com02Code;
import kr.co.kfs.assetedu.model.Jnl12Tr;
import kr.co.kfs.assetedu.model.Jnl13TrMap;
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com02CodeService;
import kr.co.kfs.assetedu.service.Jnl12TrService;
import kr.co.kfs.assetedu.service.Jnl13TrMapService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/jnl/trmap")
public class TrMapController {
	@Autowired
	Com02CodeService com02CodeService;
	
	@Autowired
	Jnl12TrService jnl12TrService;
	
	@Autowired
	Jnl13TrMapService jnl13TrMapService;
	
	@GetMapping("list")
	public String list(String searchText, Model model) {
		log.debug("거래코드리스트");
		
		QueryAttr queryAttr = new QueryAttr();
	
		queryAttr.put("searchText", searchText);
		List<Jnl12Tr>jnl12list = jnl12TrService.selectList(queryAttr);
		model.addAttribute("jnl12List", jnl12list);
		return "/jnl/tr/list";
	}
	@ResponseBody
	@PostMapping("jnl12insert")
	public String insert(@Valid @RequestBody Jnl12Tr jnl12Tr) {
		ApiData apiData = new ApiData();
		int count = jnl12TrService.insert(jnl12Tr);
		apiData.put("count", count);
		apiData.put("result", "OK");
		apiData.put("jnl12Tr", jnl12Tr);
		apiData.put("msg", "추가되었습니다. 추가된 레코드갯수 : "+count );
		
		return apiData.toJson();
	}
	@ResponseBody
	@PostMapping("jnl12update")
	public String update(@Valid @RequestBody Jnl12Tr jnl12Tr) {
		ApiData apiData = new ApiData();
		int count = jnl12TrService.update(jnl12Tr);
		apiData.put("count", count);
		apiData.put("result", "OK");
		apiData.put("jnl12Tr", jnl12Tr);
		apiData.put("msg", "수정되었습니다. 수정된 레코드갯수 : "+count );
		return apiData.toJson();
	}
	
	
	//Jnl12Tr 거래코드 삭제
	@GetMapping("jnl12delete")
	public String delete( @RequestParam(value="jnl12TrCd", required=true) String jnl12TrCd
							,RedirectAttributes redirectAttr) {
		log.debug("거래코드삭제");
		Jnl12Tr jnl12Tr = new Jnl12Tr();
		jnl12Tr.setJnl12TrCd(jnl12TrCd);
		int count = jnl12TrService.delete(jnl12TrCd);
		log.debug("deleted count : {}", count);
		redirectAttr.addAttribute("lastComCd", jnl12TrCd); //마지막 선택된 공통코드
		return "redirect:/jnl/trmap/list";
	}
	
	
	@ResponseBody
	@GetMapping("find")
	public String selectList(@RequestParam(value="jnl12TrCd") String jnl12TrCd){
		Jnl13TrMap jnl13TrMap = new Jnl13TrMap();
		jnl13TrMap.setJnl13TrCd(jnl12TrCd);
		
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.putClass(jnl13TrMap);
		
		List<Jnl13TrMap> list = jnl13TrMapService.selectList(queryAttr);
		
		ApiData apiData = new ApiData();
		
		log.debug("list:{}", list );
		apiData.put("list",list);
		apiData.put("jnl12TrCd", jnl12TrCd);

		//공통코드의 이름을 찾는다
		queryAttr.clear();
		queryAttr.put("jnl12TrCd", jnl12TrCd);
		
		Jnl12Tr jnl12Tr = new Jnl12Tr();
		jnl12Tr.setJnl12TrCd(jnl12TrCd);

		
		Jnl12Tr jnl12Tr2 = jnl12TrService.selectOne(jnl12Tr);
		
		apiData.put("jnl12TrNm", jnl12Tr2.getJnl12TrNm());
		
		String json = apiData.toJson();
		return json;
	}
	@ResponseBody
	@PostMapping("jnl13insert")
	public String insert(@Valid @RequestBody Jnl13TrMap jnl13TrMap) {
		log.debug("jnl : {} ", jnl13TrMap);
		Jnl13TrMap checkJnl13 = jnl13TrMapService.selectOne(jnl13TrMap);
		
		ApiData apiData = new ApiData();
		if(checkJnl13 != null ) {
			apiData.put("result", "False");
			return apiData.toJson();
		}else{
			int count = jnl13TrMapService.insert(jnl13TrMap);
			apiData.put("count", count);
			apiData.put("result", "OK");
			apiData.put("jnl13TrMap", jnl13TrMap);
			apiData.put("msg", "추가되었습니다. 추가된 레코드갯수 : "+count );
			
			return apiData.toJson();
		}
		
	}
}

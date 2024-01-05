package kr.co.kfs.assetedu.controller;

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
import kr.co.kfs.assetedu.model.QueryAttr;
import kr.co.kfs.assetedu.service.Com02CodeService;
import kr.co.kfs.assetedu.service.Jnl12TrService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/code")
public class CodeController {
	@Autowired
	Com02CodeService com02CodeService;
	
	@GetMapping("list")
	public String list(String searchText, Model model) {
		log.debug("공통코드리스트");
		
		QueryAttr queryAttr = new QueryAttr();
	
		queryAttr.put("searchText", searchText);
		List<Com02Code>listCategory = codeList(queryAttr);
		model.addAttribute("CodeList", listCategory);
		return "/code/codeList";
	}
	private List<Com02Code> codeList(QueryAttr queryAttr){
		Com02Code code = new Com02Code();
		code.setCom02CodeType("C");
		queryAttr.putClass(code);
		List<Com02Code>list = com02CodeService.selectList(queryAttr);
		return list;
	}
	@ResponseBody
	@PostMapping("insert")
	public String insert(@Valid @RequestBody Com02Code com02Code) {
		ApiData apiData = new ApiData();
		int count = com02CodeService.insert(com02Code);
		apiData.put("count", count);
		apiData.put("result", "OK");
		apiData.put("com02Code", com02Code);
		apiData.put("msg", "추가되었습니다. 추가된 레코드갯수 : "+count );
		
		return apiData.toJson();
	}
	@ResponseBody
	@PostMapping("update")
	public String update(@Valid @RequestBody Com02Code com02Code) {
		ApiData apiData = new ApiData();
		int count = com02CodeService.update(com02Code);
		apiData.put("count", count);
		apiData.put("result", "OK");
		apiData.put("com02Code", com02Code);
		apiData.put("msg", "수정되었습니다. 수정된 레코드갯수 : "+count );
		return apiData.toJson();
	}

	@GetMapping("delete")
	public String delete( @RequestParam(value="comCd", required=true) String comCd,
							@RequestParam(value="dtlCd", required=false) String dtlCd
							,RedirectAttributes redirectAttr) {
							
		log.debug("코드삭제.... ");
		Com02Code code = new Com02Code();
		code.setCom02ComCd(comCd);
		code.setCom02DtlCd(dtlCd);
		int count = com02CodeService.delete(code);
		log.debug("deleted count : {}", count);
		redirectAttr.addAttribute("lastComCd", comCd); //마지막 선택된 공통코드
		return "redirect:/code/list";
	}
	
	
	@ResponseBody
	@GetMapping("find")
	public String selectList(@RequestParam(value="comCd") String comCd
									,@RequestParam(value="codeType") String codeType){
		Com02Code code = new Com02Code();
		code.setCom02ComCd(comCd);
		code.setCom02CodeType(codeType);
		
		QueryAttr queryAttr = new QueryAttr();
		queryAttr.putClass(code);
		
		List<Com02Code> list = com02CodeService.selectList(queryAttr);
		
		ApiData apiData = new ApiData();
		apiData.put("list",list);
		apiData.put("codeCd", comCd);

		//공통코드의 이름을 찾는다
		queryAttr.clear();
		queryAttr.put("com02ComCd", comCd);
		queryAttr.put("com02DtlCd", "NONE");
		
		Com02Code codeC = new Com02Code();
		codeC.setCom02ComCd(comCd);
		codeC.setCom02DtlCd("NONE");
		codeC.setCom02UseYn("true");
		
		Com02Code com02 = com02CodeService.selectOne(codeC);
		
		apiData.put("codeNm", com02.getCom02CodeNm());
		
		String json = apiData.toJson();
		return json;
	}

}

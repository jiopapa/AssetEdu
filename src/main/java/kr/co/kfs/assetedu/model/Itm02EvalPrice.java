package kr.co.kfs.assetedu.model;

import kr.co.kfs.assetedu.servlet.converter.YmdFormat;
import lombok.Data;

@Data
public class Itm02EvalPrice{	
	
	private String itm02ItemCd;
	private String itm02ItemNm;
	
	@YmdFormat
	private String itm02ApplyDate;
	
	private Long itm02ApplyPrice;
	
	private String searchText;
	
	@YmdFormat
	private String stdDate;
	


	
	
}

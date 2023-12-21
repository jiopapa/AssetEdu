package kr.co.kfs.assetedu.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Itm01Item{	
	@NotNull(message ="종목코드를 입력하세요.")
	private String itm01ItemCd;		//종목코드
	
	@NotNull(message ="종목이름을 입력하세요.")
	private String itm01ItemNm;
	
	private String itm01ItemEnm; //영문명
	private String itm01ShortCd; //단축코드
	private String itm01IssType; //발행구분
	private String itm01StkType; //증권종류
	private String itm01ListType; //상장구분 
	private String itm01MarketType; //시장구분
	private String itm01Par; //액면가
	private String itm01IssCoCd; //발행기관코드
}

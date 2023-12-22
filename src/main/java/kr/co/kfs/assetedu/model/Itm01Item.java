package kr.co.kfs.assetedu.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Itm01Item{	
	@NotNull(message ="종목코드를 입력하세요.")
	private String itm01ItemCd;		//종목코드
	
	@NotNull(message ="종목이름을 입력하세요.")
	private String itm01ItemNm;
	
	@NotNull(message ="상장구분을 선택하세요.")
	private String itm01ListType; //상장구분 
	
	@NotNull(message ="시장구분을 선택하세요.")
	private String itm01MarketType; //시장구분
	
	private String itm01ItemEnm; //영문명
	private String itm01ShortCd; //단축코드
	private String itm01IssType; //발행구분
	private String itm01StkType; //증권종류
	private Long itm01Par; //액면가
	private String itm01IssCoCd; //발행기관코드
	private String itm01StkTypeNm;//증권종류이름
	private String itm01ListTypeNm; //상장구분이름
	private String itm01MarketTypeNm; //시장구분이름
	
}

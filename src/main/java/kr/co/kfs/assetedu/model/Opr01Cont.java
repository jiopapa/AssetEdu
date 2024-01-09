package kr.co.kfs.assetedu.model;

import org.springframework.format.annotation.NumberFormat;

import kr.co.kfs.assetedu.servlet.converter.YmdFormat;
import lombok.Data;

@Data
public class Opr01Cont {
	private String opr01ContId;							
	private String opr01FundCd;
	private String opr01ItemCd;
	private String opr01TrCd;
	private String opr01TrCoCd;
	private String opr01BookId;
	private String opr01StatusCd;
	private String opr01FundNm;  
	private String opr01ItemNm;
	private String opr01TrCoNm; 
	private String opr01StatusNm;
	private String opr01TrNm;
	
	private String  opr01BizDate;
	
	@YmdFormat
	private String opr01ContDate;
	@YmdFormat
	private String opr01SettleDate;
	

	
	@NumberFormat(pattern = "###,###,###,###")
	private Long opr01Qty;
	@NumberFormat(pattern = "###,###,###,###")
	private Long opr01Price;
	@NumberFormat(pattern = "###,###,###,###")
	private Long opr01ContAmt;
	@NumberFormat(pattern = "###,###,###,###")
	private Long opr01TrPl;
	@NumberFormat(pattern = "###,###,###,###")
	private Long opr01Fee;
	@NumberFormat(pattern = "###,###,###,###")
	private Long opr01Tax;
	@NumberFormat(pattern = "###,###,###,###")
	private Long opr01SettleAmt;
	@NumberFormat(pattern = "###,###,###,###")
	private Long opr01BookAmt;
	
	
	String contId;
	
}

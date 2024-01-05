package kr.co.kfs.assetedu.model;

import java.util.Date;

import lombok.Data;

@Data
public class Opr01Cont {
	private String opr01ContId;							
	private String opr01FundCd;
	private String opr01ItemCd;
	private String opr01ContDate;
	private String opr01TrCd;
	private Long opr01Qty;
	private Long opr01Price;
	private Long opr01ContAmt;
	private Long opr01TrPl;
	private Long opr01Fee;
	private Long opr01Tax;
	private Long opr01SettleAmt;
	private String opr01SettleDate;
	private String opr01TrCoCd;
	private String opr01BookId;
	private Long opr01BookAmt;
	private String opr01StatusCd;
	
	private String opr01FundNm;  
	private String opr01ItemNm;
	private String opr01TrCoNm; 
	private String opr01StatusNm;
	private String opr01TrNm;
}

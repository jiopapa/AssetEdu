package kr.co.kfs.assetedu.model;

import org.springframework.format.annotation.NumberFormat;

import kr.co.kfs.assetedu.servlet.converter.YmdFormat;
import lombok.Data;

@Data
public class Bok01Book {
	private String bok01BookId;
	

	private String bok01HoldDate;
	
	private String bok01FundCd;
	private String bok01ItemCd;
	@NumberFormat
	private Long bok01HoldQty;
	@NumberFormat
	private Long bok01PurAmt;
	@NumberFormat
	private Long bok01BookAmt;
	@NumberFormat
	private Long bok01EvalAmt;
	@NumberFormat
	private Long bok01EvalPl;
	private String bok01EvalYn;
}

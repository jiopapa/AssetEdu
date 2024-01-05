package kr.co.kfs.assetedu.model;

import lombok.Data;

@Data
public class Bok01Book {
	private String bok01BookId;
	private String bok01HoldDate;
	private String bok01FundCd;
	private String bok01ItemCd;
	private Long bok01HoldQty;
	private Long bok01PurAmt;
	private Long bok01BookAmt;
	private Long bok01EvalAmt;
	private Long bok01EvalPl;
	private String bok01EvalYn;
}

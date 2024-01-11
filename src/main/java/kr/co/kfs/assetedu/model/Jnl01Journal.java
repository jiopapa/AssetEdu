package kr.co.kfs.assetedu.model;

import kr.co.kfs.assetedu.servlet.converter.YmdFormat;
import lombok.Data;

@Data
public class Jnl01Journal {
	private String jnl01ContId;
	private String jnl01DrAcntCd;
	private String jnl01CrAcntCd;
	private Long jnl01Seq;
	private Long jnl01CrAmt;
	private Long jnl01DrAmt;
	
	
	@YmdFormat
	private String opr01ContDate;
	
	private String opr01TrNm;
	private String fnd01FundNm;
	private String itm01ItemNm;
	private String drJnl10AcntCd;
	private String drJnl10AcntNm;
	private String crJnl10AcntCd;
	private String crJnl10AcntNm;

}

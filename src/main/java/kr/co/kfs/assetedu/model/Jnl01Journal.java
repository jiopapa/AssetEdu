package kr.co.kfs.assetedu.model;

import lombok.Data;

@Data
public class Jnl01Journal {
	private String jnl01ContId;
	private Long jnl01Seq;
	private String jnl01DrAcntCd;
	private Long jnl01DrAmt;
	private String jnl01CrAcntCd;
	private Long jnl01CrAmt;
}

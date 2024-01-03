package kr.co.kfs.assetedu.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Jnl11ReprAcnt {
	@NotNull(message = "대표계정 코드를 입력해 주세요.")
	private String jnl11ReprAcntCd;
	
	private String jnl11ReprAcntNm;
	
	private String jnl11AcntAttributeCd;
	private String jnl11AcntAttributeNm;
	private String jnl11TgtReprAcntCd;	
	private String jnl11TgtReprAcntNm;	

}

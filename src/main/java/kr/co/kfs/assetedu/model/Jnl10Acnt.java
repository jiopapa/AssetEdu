package kr.co.kfs.assetedu.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Jnl10Acnt {
	@NotNull(message = "계정코드를 입력해 주세요.")
	private String jnl10AcntCd;
	
	@NotNull(message = "계정이름을 입력해 주세요.")
	private String jnl10AcntNm;
	
	private String jnl10ParentCd;
	private String jnl10AcntAttrCd;
	private String jnl10AcntAttrNm;
	private String jnl10DrcrType;
	private String jnl10DrcrTypeNm;
	
	@NotNull(message = "필수 입력란입니다.")
	private String jnl10SlipYn;
	private String jnl10UseYn;
	private String jnl10ParentNm;
	
}

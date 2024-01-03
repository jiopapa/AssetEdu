package kr.co.kfs.assetedu.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Jnl12Tr {
	@NotNull(message = "거래 코드를 입력해 주세요.")
	private String jnl12TrCd;
	@NotNull(message = "거래명을 입력해 주세요.")
	private String jnl12TrNm;
	
	private String jnl12InOutType;
	private String jnl12UsePageType;
	private String jnl12UseYn;
	
	private String jnl12InOutTypeNm;

}

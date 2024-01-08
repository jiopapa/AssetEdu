package kr.co.kfs.assetedu.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Jnl13TrMap {
	@NotNull(message = "거래 코드를 입력해 주세요.")
	private String jnl13TrCd;
	@NotNull(message = "거래순번을 입력해 주세요.")
	private Long jnl13Seq;
	
	private String jnl13ReprAcntCd;
	private String jnl13ReprAcntNm;
	
	private String jnl13DrcrType;
	private String jnl13Formula;
	
}

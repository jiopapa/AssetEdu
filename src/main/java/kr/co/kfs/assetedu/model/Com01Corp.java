package kr.co.kfs.assetedu.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Com01Corp {
	
	@NotNull(message ="기관코드를 입력하세요.")
	private String com01CorpCd;
	
	@NotNull(message ="기관명을 입력하세요.")
	private String com01CorpNm;
	
	@NotNull(message ="기관구분을 입력하세요.")
	private String com01CorpType;
	
	private String com01CorpEnm;
	private String com01ExtnCorpCd;
	
	
}

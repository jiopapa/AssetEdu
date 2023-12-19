package kr.co.kfs.assetedu.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Com02Code {
	
	@NotNull(message = "공통코드를 입력해 주세요.")
	private String com02ComCd;

	@NotNull(message = "세부코드를 입력해 주세요.")
	private String com02DtlCd;
	
	@NotNull(message = "코드명을 입력해 주세요.")
	private String com02CodeNm;
	
	
	private String com02CodeType;
	private Integer com02Seq;
	private String com02UseYn;
	private String com02Note;
}

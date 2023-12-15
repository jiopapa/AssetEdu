package kr.co.kfs.assetedu.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Student {
	
	private String name;
	private Integer age;
	private Long asset;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;
}

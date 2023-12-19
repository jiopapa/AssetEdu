package kr.co.kfs.assetedu.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ApiDataTest {

	@Test
	void test() {
		List<String> list = new ArrayList<>();
		list.add("aaaaa");
		list.add("bbbbb");
		list.add("ccccc");
		
		//생성
		ApiData apiData = new ApiData();
		//데이터 담기
		apiData.put("name", "홍길동");
		apiData.put("age", 10);
		apiData.put("weight", 32.24);
		apiData.put("list", list);
		
		//json만들기
		String json = apiData.toJson();
		System.out.println(json);
		
	}

}

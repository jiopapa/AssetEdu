package kr.co.kfs.assetedu.model;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
 class ConditionTest {

	@Test
	@Disabled
	void test() {
//		Condition condition = new Condition();
//	assertNotNull( condition);
		PageAttr pageAttr = new PageAttr(130L,10);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("age", 11 );
		map.put("id", "abc");
		map.put("name", "홍길동");
		map.put("pageAttr", pageAttr);
		
		String name = (String)map.get("name");
		PageAttr pageAttr1 = (PageAttr)map.get("pageAttr");
		Long totalCount = pageAttr1.getTotalItemCount();
		int pageSize = pageAttr1.getPageSize();
		System.out.println("totalCount : "+totalCount);
		System.out.println("pageSize : "+pageSize);
		System.out.println(name);
	}
	@Test
	void test2() {
		Condition condition = new Condition();
		PageAttr pageAttr = new PageAttr(130L,20);
		condition.put("name", "홍길동");
		condition.put("pageAttr", pageAttr);
		
		PageAttr pageAttr1 = (PageAttr) condition.get("pageAttr");
		Long totalCount = pageAttr1.getTotalItemCount();
	}
	
//	@Test
//	void test1() {
		
//		Com02Code code = new Com02Code();
//		code.setCom02ComCd("1");
//		code.setCom02DtlCd("2");
//		Condition condition = new Condition();
//		condition.putClass(code);
//		String s = (String) condition.get("com02ComCd");
//		log.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
//		log.debug("s: {}", s);
//		log.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

	}



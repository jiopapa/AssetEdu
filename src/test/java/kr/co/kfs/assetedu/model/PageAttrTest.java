package kr.co.kfs.assetedu.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class PageAttrTest {

	@Test
	void test() {
		//총갯수 10, 1페이지당 3개, 현재 페이지 번호 1일때 총 페이지 갯수는 4개여야한다
		PageAttr pageAttr = new PageAttr(10L,3,1);
		log.debug("pageAttr: {}", pageAttr);
		assertEquals(pageAttr.getTotalPageCount(), 4);
		
		
		//총갯수 10, 1페이지당 5개, 현재 페이지 번호 1일때 총 페이지 갯수는 2개여야한다
		pageAttr = new PageAttr(10L,5,2);
		log.debug("pageAttr: {}", pageAttr);
		assertEquals(pageAttr.getTotalPageCount(), 2);
		assertEquals(pageAttr.getOffset(), 5);
	}

}

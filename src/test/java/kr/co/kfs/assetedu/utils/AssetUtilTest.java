package kr.co.kfs.assetedu.utils;

import org.junit.jupiter.api.Test;

class AssetUtilTest {

	@Test
	void test() {
		// junit에서 확인

		System.out.println("--------------------------------");
		System.out.println("AssetUtil 사용법");
		System.out.println("--------------------------------");

		System.out.println(AssetUtil.today());
		System.out.println(AssetUtil.removeDash("2023-12-17"));
		System.out.println(AssetUtil.removeComma("1,234"));

		System.out.println(AssetUtil.today());
		System.out.println(AssetUtil.displayYmd("20231216"));

		System.out.println(AssetUtil.ymd());

	}

}

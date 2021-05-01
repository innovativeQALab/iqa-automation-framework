package org.iqa.suite.commons;

import org.testng.asserts.SoftAssert;

public class AssertionFactory {

	private static ThreadLocal <SoftAssert> softAssertHolder = new ThreadLocal<SoftAssert>();

	public static SoftAssert getSoftAssert() {
		return softAssertHolder.get();
	}

	public static void setSoftAssert(SoftAssert softAssert) {
		softAssertHolder.set(softAssert);
	}
	
	
	
}

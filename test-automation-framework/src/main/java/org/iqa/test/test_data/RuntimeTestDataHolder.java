package org.iqa.test.test_data;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuntimeTestDataHolder {
	
    private static final Logger logger = LoggerFactory.getLogger(RuntimeTestDataHolder.class);
	private static ThreadLocal<HashMap<String, String>> testDataHolder = new ThreadLocal<HashMap<String,String>>();
	
	public static HashMap<String,String> getRunTimeTestData() {
		return testDataHolder.get();
	}

	public static void setRunTimeTestData(HashMap<String,String> dataMap) {
		testDataHolder.set(dataMap);
	}

}

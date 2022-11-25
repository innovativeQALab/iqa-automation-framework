package org.iqa.test.runner;

import org.iqa.test.base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

public class ParallelTestRunner extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(ParallelTestRunner.class);

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
	
		//Call to scenarios method of AbstractTestNGCucumberTests
		return super.scenarios();
	}

}

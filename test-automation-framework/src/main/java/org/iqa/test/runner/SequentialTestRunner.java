package org.iqa.test.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import io.cucumber.testng.AbstractTestNGCucumberTests;

public class SequentialTestRunner extends AbstractTestNGCucumberTests {
	private static final Logger logger = LoggerFactory.getLogger(SequentialTestRunner.class);

	@Override
	@DataProvider(parallel = false)
	public Object[][] scenarios() {
		Object[][] cucumberScenarios = null;
		try {
			cucumberScenarios = super.scenarios();
		} catch (Exception e) {
			logger.error("!!!!!!!!!!!!ERROR Please check feature file if there are any lexical errors!!!");
		}
		return cucumberScenarios;
	}

}

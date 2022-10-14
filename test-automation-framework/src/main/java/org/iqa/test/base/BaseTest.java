package org.iqa.test.base;

import org.iqa.suite.commons.PropertyHolder;
import org.iqa.suite.commons.applitool.ApplitoolsEyesUtils;
import org.iqa.suite.commons.listeners.TestNGMethodInvocationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@Listeners(TestNGMethodInvocationListener.class)
public class BaseTest extends AbstractTestNGCucumberTests {

	private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	private static String AUT = null;

	@BeforeSuite
	public void beforeSuite() {

		logger.info("********* Loading Test suite config properties...");
		PropertyHolder.loadFrameworkConfig();
		PropertyHolder.loadApplitoolConfig();
		PropertyHolder.loadGeneralConfig();
		logger.info("********* Test suite config properties loaded successfully.");
		AUT = PropertyHolder.testSuiteConfigurationProperties.getProperty("AUT").toString();
		logger.info("********* Application under test is : " + AUT);
		if (null == AUT) {
			logger.error(
					"!!!!!!!!!! AUT Property must be set in src/test/resources/properties/framework/FrameworkConfig.properties"
							+ "\n Property name should be AUT and values should be one of {WEB, MOBILE, API_ONLY.}"
							+ "\n AUT should be set as WEB and MOBILE if you want to execute Web/Mobile automation tests and it also allows execution of API tests."
							+ "\n AUT should be set as API_ONLY if you want to execute only API tests}. Exiting...");
			System.exit(-1);
		}

		if (!AUT.equalsIgnoreCase("API_ONLY")) {
			logger.info("********* Setting applitool eyes configs...");
			ApplitoolsEyesUtils.setApplitoolEyeConfig();
			
		}
	}

	@AfterSuite
	public void afterSuite() {
		if (null != AUT && !AUT.equalsIgnoreCase("API_ONLY")) {
			if (null != PropertyHolder.testSuiteConfigurationProperties.getProperty("EYE_ENABLE") && new Boolean(
					PropertyHolder.testSuiteConfigurationProperties.getProperty("EYE_ENABLE").toString()) == true) {
				if (PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
						.equalsIgnoreCase("LINUX")) {
					ApplitoolsEyesUtils.applicationToolEyeWebGetAllTestResults(); // Only for Web
				}
			}
		}

	}

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

package org.iqa.test.base;

import org.iqa.suite.commons.PropertyHolder;
import org.iqa.suite.commons.TestMetaData;
import org.iqa.suite.commons.applitool.ApplitoolsEyesUtils;
import org.iqa.suite.commons.listeners.TestNGMethodInvocationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;

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
							+ "\n Property name should be AUT and values should be one of {WEB, ANDROID,IOS, API_ONLY.}"
							+ "\n AUT should be set as WEB/ANDROID/IOS if you want to execute Web/Mobile automation tests and it also allows execution of API tests."
							+ "\n AUT should be set as API_ONLY if you want to execute only API tests}. Exiting...");
			System.exit(-1);
		}

		if (!AUT.equalsIgnoreCase("API_ONLY")) {
			logger.info("********* Setting applitool eyes configs...");
			ApplitoolsEyesUtils.setApplitoolEyeConfig();
			logger.info("********* Applitools eyes configs set up done.");

		}
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
		TestMetaData.setFeatureWrapper(featureWrapper);
		TestMetaData.setPickleWrapper(pickleWrapper);
		ApplitoolsEyesUtils.openApplitoolEye();
		super.runScenario(pickleWrapper, featureWrapper);
	}

	@AfterSuite
	public void afterSuite() {
//    ***** Skipping applicationToolEyeWebGetAllTestResults as it is taking longer time for processing and causing execution pipeline to wait
//
//		if (null != AUT && !AUT.equalsIgnoreCase("API_ONLY")) {
//			if (null != PropertyHolder.testSuiteConfigurationProperties.getProperty("EYE_ENABLE") && new Boolean(
//					PropertyHolder.testSuiteConfigurationProperties.getProperty("EYE_ENABLE").toString()) == true) {
//				String platform = SeleniumUtils.getOsFamilyName(PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString());
//				if (platform.equalsIgnoreCase("LINUX")) {
//					ApplitoolsEyesUtils.applicationToolEyeWebGetAllTestResults(); // Only for Web
//				}
//			}
//		}

	}
}

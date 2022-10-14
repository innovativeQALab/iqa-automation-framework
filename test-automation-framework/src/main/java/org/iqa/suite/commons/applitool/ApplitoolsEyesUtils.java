package org.iqa.suite.commons.applitool;
import org.iqa.suite.commons.TestMetaData;
import org.iqa.suite.commons.PropertyHolder;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.applitools.eyes.TestResultsSummary;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;

public class ApplitoolsEyesUtils {

	private static final Logger logger = LoggerFactory.getLogger(ApplitoolsEyesUtils.class);
	private static String platform = null;
	private static String EYE_ENABLE = null;
	private static String APPLITOOLS_API_KEY = null;

	static {

		logger.debug("********* In ApplitoolsEyesUtils static block...");
		platform = PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString();
		EYE_ENABLE = PropertyHolder.testSuiteConfigurationProperties.getProperty("EYE_ENABLE").toString();
		APPLITOOLS_API_KEY = PropertyHolder.testSuiteConfigurationProperties.getProperty("APPLITOOLS_API_KEY")
				.toString();
	}

	public static void setApplitoolEyeConfig() {

		if (null != EYE_ENABLE && new Boolean(EYE_ENABLE) == true) {
			if (platform.equalsIgnoreCase("LINUX") || platform.equalsIgnoreCase("WINDOWS")) {
				logger.info("********* Opening Applitool Eyes for Web");
				ApplitoolEyesWeb.enabled = true;
				ApplitoolEyesWeb.setApplitoolCongfig(APPLITOOLS_API_KEY);
				logger.info("Applitools eyes configuration setup done.");
			} else if (platform.equalsIgnoreCase("ANDROID") || platform.equalsIgnoreCase("IOS")) {
				logger.info("********* Opening Applitool Eyes for Mobile");
				ApplitoolEyesMobile.enabled = true;
				ApplitoolEyesMobile.setApplitoolCongfig(APPLITOOLS_API_KEY);
				logger.info("********* Applitool configuration setup done.");
			} else {
				logger.info("!!!!!!!!!!!!! Platform not found, it must be within {Android,IOS,LINUX,WINDOWS}");
				System.exit(1);
			}

		}
	}

	public static void openApplitoolEye() {
		if ((ApplitoolEyesWeb.enabled == true || ApplitoolEyesMobile.enabled) == true) {
			if (platform.equalsIgnoreCase("LINUX") || platform.equalsIgnoreCase("WINDOWS")) {
				ApplitoolEyesWeb.createEyes().open(WebDriverFactory.getDriver(), TestMetaData.getFeatureWrapper().toString(),
						TestMetaData.getPickleWrapper.toString() + ":" + PropertyHolder.testSuiteConfigurationProperties
								.getProperty("platform").toLowerCase());// ,new RectangleSize(1024, 751));

			} else if (platform.equalsIgnoreCase("ANDROID") || platform.equalsIgnoreCase("IOS")) {
				ApplitoolEyesMobile.createEyes().open(WebDriverFactory.getDriver(), TestMetaData.getFeatureWrapper().toString(),
						TestMetaData.getPickleWrapper.toString() + ":" + PropertyHolder.testSuiteConfigurationProperties
								.getProperty("platform").toLowerCase());// ,new RectangleSize(1024, 751));
			}
		}

	}

	public static void closeApplitoolEye() {

		logger.info("****** Closing Applitools Eyes...");
		if (ApplitoolEyesWeb.enabled == true && null != ApplitoolEyesWeb.getEyes()
				&& ApplitoolEyesWeb.getEyes().getIsOpen()) {
			ApplitoolEyesWeb.getEyes().closeAsync();

		} else if (ApplitoolEyesMobile.enabled == true && null != ApplitoolEyesMobile.getEyes()
				&& ApplitoolEyesMobile.getEyes().getIsOpen()) {
			ApplitoolEyesMobile.getEyes().closeAsync();
		}
		logger.info("****** Applitools Eyes closed.");
	}

	public static void applicationToolEyeWebGetAllTestResults() {
		TestResultsSummary testResultSummary = ApplitoolEyesWeb.getApplitoolEyeRunner().getAllTestResults(true);
	}

}

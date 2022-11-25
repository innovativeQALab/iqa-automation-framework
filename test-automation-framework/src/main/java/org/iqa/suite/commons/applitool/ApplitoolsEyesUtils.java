package org.iqa.suite.commons.applitool;

import org.iqa.suite.commons.PropertyHolder;
import org.iqa.suite.commons.SeleniumUtils;
import org.iqa.suite.commons.TestMetaData;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.applitools.eyes.TestResultsSummary;

public class ApplitoolsEyesUtils {

	private static final Logger logger = LoggerFactory.getLogger(ApplitoolsEyesUtils.class);
	private static String AUT = null;
	private static String EYE_ENABLE = null;
	private static String APPLITOOLS_API_KEY = null;

	static {
		logger.debug("********* In ApplitoolsEyesUtils static block...");
		AUT = PropertyHolder.testSuiteConfigurationProperties.getProperty("AUT").toString();
		EYE_ENABLE = PropertyHolder.testSuiteConfigurationProperties.getProperty("EYE_ENABLE").toString();
		APPLITOOLS_API_KEY = PropertyHolder.testSuiteConfigurationProperties.getProperty("APPLITOOLS_API_KEY")
				.toString();
	}

	public static void setApplitoolEyeConfig() {
		if (null != EYE_ENABLE && new Boolean(EYE_ENABLE) == true) {
			if (AUT.equalsIgnoreCase("WEB")) {
				logger.info("********* Opening Applitool Eyes for Web");
				ApplitoolEyesWeb.enabled = true;
				ApplitoolEyesWeb.setApplitoolCongfig(APPLITOOLS_API_KEY);
				logger.info("Applitools eyes configuration setup done.");
			} else if (AUT.equalsIgnoreCase("ANDROID") || AUT.equalsIgnoreCase("IOS")) {
				logger.info("********* Opening Applitool Eyes for Mobile");
				ApplitoolEyesMobile.enabled = true;
				ApplitoolEyesMobile.setApplitoolCongfig(APPLITOOLS_API_KEY);
				logger.info("********* Applitool configuration setup done.");
			} else {
				logger.info("!!!!!!!!!!!!! Invalid AUT property value. Please provide correct value from {WEB,ANDROID,IOS}");
				System.exit(1);
			}
		}
	}

	public static void openApplitoolEye() {
		if ((ApplitoolEyesWeb.enabled == true || ApplitoolEyesMobile.enabled) == true) {
			String platform = SeleniumUtils.getOsFamilyName(PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString());
			if (platform.equalsIgnoreCase("LINUX") || platform.equalsIgnoreCase("WINDOWS")) {
				ApplitoolEyesWeb.createEyes().open(WebDriverFactory.getDriver(),
						TestMetaData.getFeatureWrapper().toString(),
						TestMetaData.getPickleWrapper().toString() + ":"
								+ platform.toLowerCase());// ,new RectangleSize(1024, 751));

			} else if (platform.equalsIgnoreCase("ANDROID") || platform.equalsIgnoreCase("IOS")) {
				ApplitoolEyesMobile.createEyes().open(WebDriverFactory.getDriver(),
						TestMetaData.getFeatureWrapper().toString(),
						TestMetaData.getPickleWrapper().toString() + ":"
								+ platform.toLowerCase());// ,new RectangleSize(1024, 751));
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

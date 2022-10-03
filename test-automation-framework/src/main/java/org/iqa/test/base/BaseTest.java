package org.iqa.test.base;

import org.iqa.suite.commons.PropertyHolder;
import org.iqa.suite.commons.TestMetaData;
import org.iqa.suite.commons.applitool.ApplitoolEyesMobile;
import org.iqa.suite.commons.applitool.ApplitoolEyesWeb;
import org.iqa.suite.commons.listeners.TestNGMethodInvocationListener;
import org.iqa.suite.commons.reporting.ExtentReportTestFactory;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

@Listeners({ TestNGMethodInvocationListener.class })
//ExtentITestListenerClassAdapter.class

public class BaseTest extends AbstractTestNGCucumberTests {

	private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

	@BeforeSuite
	public void beforeSuite() {

		logger.info("TestNGCucumberRunner object initialization completed.");

		PropertyHolder.loadGeneralConfig();
		if (null != PropertyHolder.testSuiteConfigurationProperties.get("AUT")
				&& !PropertyHolder.testSuiteConfigurationProperties.get("AUT").toString().equalsIgnoreCase("API")) {
			PropertyHolder.loadWebDriverConfig();
			PropertyHolder.loadApplitoolConfig();
			this.setApplitoolEyeConfig();
		}
	}

	@SuppressWarnings("deprecation")
	@AfterSuite
	public void afterSuite() {
		ExtentReportTestFactory.flushReport();
		if (null != PropertyHolder.testSuiteConfigurationProperties.get("AUT")
				&& !PropertyHolder.testSuiteConfigurationProperties.get("AUT").toString().equalsIgnoreCase("API")) {
			if (null != PropertyHolder.testSuiteConfigurationProperties.get("EYE_ENABLE") && new Boolean(
					PropertyHolder.testSuiteConfigurationProperties.get("EYE_ENABLE").toString()) == true) {
				if (PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
						.equalsIgnoreCase("LINUX")) {
					applicationToolEyeWebGetAllTestResults(); // Only for Web
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

	@SuppressWarnings("deprecation")
	private void setApplitoolEyeConfig() {
		if (null != PropertyHolder.testSuiteConfigurationProperties.get("EYE_ENABLE")
				&& new Boolean(PropertyHolder.testSuiteConfigurationProperties.get("EYE_ENABLE").toString()) == true) {
			if (PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
					.equalsIgnoreCase("LINUX")
					|| PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
							.equalsIgnoreCase("WINDOWS")) {
				System.out.println("********** Opening Applitool Eyes for Web");
				ApplitoolEyesWeb.enabled = true;
				ApplitoolEyesWeb.setApplitoolCongfig(System.getenv("APPLITOOLS_API_KEY"));
				logger.info("Applitool configuration setup done...");
				System.out.println("Applitool configuration setup done...");
			} else if (PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
					.equalsIgnoreCase("ANDROID")
					|| PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
							.equalsIgnoreCase("IOS")) {
				System.out.println("********** Opening Applitool Eyes for Mobile");
				ApplitoolEyesMobile.enabled = true;
				ApplitoolEyesMobile.setApplitoolCongfig(System.getenv("APPLITOOLS_API_KEY"));
				logger.info("Applitool configuration setup done...");
				System.out.println("Applitool configuration setup done...");
			} else {
				logger.info("!!!!!!!!!!!!! Platform not found, it must be within {Android,IOS,LINUX,WINDOWS}");
				System.out.println("!!!!!!!!!!!!! Platform not found, it must be within {Android,IOS,LINUX,WINDOWS}");
				System.exit(1);
			}

		}
	}

	protected void openApplitoolEye(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
		if ((ApplitoolEyesWeb.enabled == true || ApplitoolEyesMobile.enabled) == true
				&& TestMetaData.getTestTags().contains("@ScreenValidation")) {
			if (PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
					.equalsIgnoreCase("LINUX")
					|| PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
							.equalsIgnoreCase("WINDOWS")) {
				ApplitoolEyesWeb.createEyes().open(WebDriverFactory.getDriver(), featureWrapper.toString(),
						pickleWrapper.toString() + ":" + PropertyHolder.testSuiteConfigurationProperties
								.getProperty("platform").toLowerCase());// ,new RectangleSize(1024, 751));
			} else if (PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
					.equalsIgnoreCase("ANDROID")
					|| PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
							.equalsIgnoreCase("IOS")) {
				ApplitoolEyesMobile.createEyes().open(WebDriverFactory.getDriver(), featureWrapper.toString(),
						pickleWrapper.toString() + ":" + PropertyHolder.testSuiteConfigurationProperties
								.getProperty("platform").toLowerCase());
			}
		}

	}

	protected void applicationToolEyeWebGetAllTestResults() {
		TestResultsSummary testResultSummary = ApplitoolEyesWeb.getApplitoolEyeRunner().getAllTestResults(true);
	}
}

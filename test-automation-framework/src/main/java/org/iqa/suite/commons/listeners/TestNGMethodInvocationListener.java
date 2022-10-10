package org.iqa.suite.commons.listeners;

import java.net.MalformedURLException;
import java.util.HashMap;
import org.iqa.suite.commons.AssertionFactory;
import org.iqa.suite.commons.PropertyHolder;
import org.iqa.suite.commons.SeleniumUtils;
import org.iqa.suite.commons.TestMetaData;
import org.iqa.suite.commons.applitool.ApplitoolEyesMobile;
import org.iqa.suite.commons.applitool.ApplitoolEyesWeb;
import org.iqa.test.test_data.RuntimeTestDataHolder;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.iqa.test.webdriver_factory.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class TestNGMethodInvocationListener implements IInvokedMethodListener {
	private static final Logger logger = LoggerFactory.getLogger(TestNGMethodInvocationListener.class);
	private SoftAssert softAssert;

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

		if (method.isTestMethod()) {
			logger.info("******** In before invocation");
			if (null != PropertyHolder.testSuiteConfigurationProperties.get("AUT")
					&& !PropertyHolder.testSuiteConfigurationProperties.get("AUT").toString().equalsIgnoreCase("API")) {
				String AUT = PropertyHolder.testSuiteConfigurationProperties.get("AUT").toString().toUpperCase();
				if (AUT == null) {
					logger.error(
							"!!!!!!!! AUT property is null. Please add AUT property in Environment.properties file with one of the values from [WEB/MOBILE/API_ONLY]. Exiting...");
					System.exit(-1);
				}
				logger.info("********* AUT[Application under Test] property value is : " + AUT);
				// Added switch case for AUT[Application under Test]
				switch (AUT) {
				case "WEB":
				case "UI+API":
					try {
						WebDriverFactory.setDriver(WebDriverManager.CreateInstance());
						WebDriverFactory.getDriver().manage().window().maximize();

						logger.info("******** Driver object and test report instance created successfully");
					} catch (MalformedURLException e) {
						logger.error("!!!!!!!! Exception while creating Driver object and test report instance ");
						logger.error(
								"!!!!!!!! Exception occured while creating Driver object and test report instance ");
						e.printStackTrace();
					}
					break;
				case "MOBILE":
					try {
						WebDriverFactory.setDriver(WebDriverManager.CreateInstance());
					} catch (MalformedURLException e) {
						logger.error(
								"!!!!!!!! Exception occured while creating AppiumDriver object and test report instance");
						e.printStackTrace();
					}
					break;
				case "API_ONLY":
					break;

				default:
					logger.error("!!!!!!!! AUT property should have values from [WEB/MOBILE/API_ONLY]. Exiting...");
					break;
				}
				softAssert = new SoftAssert();
				AssertionFactory.setSoftAssert(softAssert);
				RuntimeTestDataHolder.setRunTimeTestData(new HashMap<String, String>());
				logger.info("********RuntimeTestDataHolder initialized.");
				TestMetaData.initialize();
				logger.info("********TestMetaData initialized.");
			}
		}

	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			logger.info("******** In after invocation");
			logger.info("******** Test Case Status " + testResult.isSuccess());
			try {
				if (null != PropertyHolder.testSuiteConfigurationProperties.get("AUT")
						&& !PropertyHolder.testSuiteConfigurationProperties.get("AUT").toString()
								.equalsIgnoreCase("API")) {
					if (!testResult.isSuccess()) {
						ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, MediaEntityBuilder
								.createScreenCaptureFromBase64String(SeleniumUtils.getScreenshotAsBase64()).build());
						logger.debug("******** Screenshot attached to extent report");
					}
				}
			} catch (Exception e) {
			}
			WebDriverFactory.getDriver().quit();
			closeApplitoolEye();
		}
	}

	protected void closeApplitoolEye() {
		if (ApplitoolEyesWeb.enabled == true && null != ApplitoolEyesWeb.getEyes()
				&& ApplitoolEyesWeb.getEyes().getIsOpen()) {
			ApplitoolEyesWeb.getEyes().closeAsync();

		} else if (ApplitoolEyesMobile.enabled == true && null != ApplitoolEyesMobile.getEyes()
				&& ApplitoolEyesMobile.getEyes().getIsOpen()) {
			ApplitoolEyesMobile.getEyes().closeAsync();
		}
	}
}

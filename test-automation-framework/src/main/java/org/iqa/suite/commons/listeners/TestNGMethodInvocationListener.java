package org.iqa.suite.commons.listeners;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.iqa.suite.commons.AssertionFactory;
import org.iqa.suite.commons.PropertyHolder;
import org.iqa.suite.commons.TestMetaData;
import org.iqa.suite.commons.applitool.ApplitoolEyesMobile;
import org.iqa.suite.commons.applitool.ApplitoolEyesWeb;
import org.iqa.suite.commons.reporting.ExtentReportTestFactory;
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


public class TestNGMethodInvocationListener implements IInvokedMethodListener {
	private static final Logger logger = LoggerFactory.getLogger(TestNGMethodInvocationListener.class);
	private SoftAssert softAssert;

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		logger.info("******** In before invocation");
		if (method.isTestMethod()) {
			logger.info("******** In before invocation");
			if (null != PropertyHolder.testSuiteConfigurationProperties.get("AUT")
					&& !PropertyHolder.testSuiteConfigurationProperties.get("AUT").toString().equalsIgnoreCase("API")) {
				try {
					WebDriverFactory.setDriver(WebDriverManager.CreateInstance());
					WebDriverFactory.getDriver().manage().window().maximize();

					logger.info("******** Driver object and test report instance created successfully");
				} catch (MalformedURLException e) {
					logger.error("!!!!!!!! Exception while creating Driver object and test report instance ");
					e.printStackTrace();
				}
			}
			softAssert = new SoftAssert();
			AssertionFactory.setSoftAssert(softAssert);
			RuntimeTestDataHolder.setRunTimeTestData(new HashMap<String, String>());
			logger.info("********RuntimeTestDataHolder initialized.");
			TestMetaData.initialize();
			logger.info("********TestMetaData initialized.");
		}

	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			logger.info("******** In after invocation");
			logger.info("******** In after invocation - Test Case Status " + testResult.isSuccess());
			try {
				if (null != PropertyHolder.testSuiteConfigurationProperties.get("AUT")
						&& !PropertyHolder.testSuiteConfigurationProperties.get("AUT").toString()
								.equalsIgnoreCase("API")) {
					if (!testResult.isSuccess()) {
						// ExtentReportTestFactory.getTest().fail(testResult.getThrowable());
						//ExtentReportTestFactory.getTest()
						//		.addScreenCaptureFromBase64String(SeleniumUtils.getScreenshotAsBase64());
		//				ExtentCucumberAdapter.addTestStepLog("FAILED");
					//	ExtentTestManager.getTest(result).fail("ITestResult.FAILURE, event afterMethod",
		          //              MediaEntityBuilder.createScreenCaptureFromPath("/Users/vzodge/Downloads/BKP/ShivajiMaharaj.png").build());
	//					ExtentReportTestFactory.getTest().addScreenCaptureFromPath("/Users/prashantbhange/Documents/iqa_git_repo/iqa-automation-framework/orange-hrm/src/test/resources/img_03102022_142133.png");
		//				ExtentReportTestFactory.getTest().info(MediaEntityBuilder.createScreenCaptureFromPath("/Users/prashantbhange/Documents/iqa_git_repo/iqa-automation-framework/orange-hrm/src/test/resources/img_03102022_142133.png").build());
//						ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(SeleniumUtils.getBase64Screenshot());
//						ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(SeleniumUtils.getScreenshotAsBase64());
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

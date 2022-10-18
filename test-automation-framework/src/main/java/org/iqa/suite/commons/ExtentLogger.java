package org.iqa.suite.commons;

import org.iqa.suite.commons.reporting.ExtentReportTestFactory;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class ExtentLogger {

	public ExtentLogger info(String message) {
		ExtentReportTestFactory.getTest().log(Status.INFO, message);
		return this;
	}

	@Deprecated
	public ExtentLogger error(String message) {
		ExtentReportTestFactory.getTest().fail(message);
		return this;
	}

	@Deprecated
	public ExtentLogger error(Throwable thorowable) {
		ExtentReportTestFactory.getTest().fail(thorowable);
		return this;
	}

	public ExtentLogger fail(String message) {
		ExtentReportTestFactory.getTest().fail(message);
		return this;
	}

	public ExtentLogger fail(Throwable thorowable) {
		ExtentReportTestFactory.getTest().fail(thorowable);
		return this;
	}

	public ExtentLogger attachScreenshot() {
		ExtentReportTestFactory.getTest().log(Status.INFO,
				MediaEntityBuilder.createScreenCaptureFromBase64String(SeleniumUtils.getScreenshotAsBase64()).build());
		return this;
	}

}

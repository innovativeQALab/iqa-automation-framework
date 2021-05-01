package org.iqa.suite.commons;

import org.iqa.suite.commons.reporting.ExtentReportTestFactory;

import com.aventstack.extentreports.Status;

public class ExtentLogger {

	public void info(String message) {
		ExtentReportTestFactory.getTest().log(Status.INFO, message);
	}
	
	public void error(String message) {
		ExtentReportTestFactory.getTest().error(message);
	}
	
	public void error(Throwable thorowable) {
		ExtentReportTestFactory.getTest().error(thorowable);
	}
	
	public void fail(String message) {
		ExtentReportTestFactory.getTest().fail(message);
	}
	
	public void fail(Throwable thorowable) {
		ExtentReportTestFactory.getTest().fail(thorowable);
	}

}

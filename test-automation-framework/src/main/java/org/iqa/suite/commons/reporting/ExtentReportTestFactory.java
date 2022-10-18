package org.iqa.suite.commons.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class ExtentReportTestFactory {
	public static ExtentTest getTest() {
		return ExtentCucumberAdapter.getCurrentStep();
	}
}

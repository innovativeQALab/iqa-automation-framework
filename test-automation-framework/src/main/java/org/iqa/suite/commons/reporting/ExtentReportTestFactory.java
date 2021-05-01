package org.iqa.suite.commons.reporting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReportTestFactory {
	private static ExtentReports extentReport;
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	private static Map<String, ExtentTest> moduleMap = new HashMap<String, ExtentTest>();
    private static final Logger logger = LoggerFactory.getLogger(ExtentReportTestFactory.class);

	static {
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extent.html");
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("Test Result");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("Test Results");
		extentReport = new ExtentReports();
		extentReport.attachReporter(htmlReporter);
				
	}
	
	synchronized public static ExtentTest getModule(String className)
	{
		if(!moduleMap.isEmpty() && moduleMap.containsKey(className))
		{
			return moduleMap.get(className);
		}else {
			moduleMap.put(className, extentReport.createTest(className));
			return moduleMap.get(className);
		}
	}
	
	
	synchronized public static void createNewTest(String moduleName,String testName)
	{
		ExtentReportTestFactory.extentTest.set(
				getModule(moduleName)
				.createNode(testName)
				);
	}

	synchronized public static void flushReport()
	{
		extentReport.flush();
	}

	synchronized public static ExtentTest getTest() {
		return extentTest.get();
	}


	synchronized public static void assignTestCategories(List<String> tags) {
		for(String category:tags)
		{
			getTest().assignCategory(category);
		}
	}
}

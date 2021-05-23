package org.iqa.test.runner;

import org.iqa.suite.commons.AssertionFactory;
import org.iqa.suite.commons.applitool.ApplitoolEyes;
import org.iqa.suite.commons.reporting.ExtentReportTestFactory;
import org.iqa.test.base.BaseTest;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.testng.annotations.Test;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;

public class SequentialTestRunner extends BaseTest{
	
	 @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "cucumber-examples-sequential")
	 public void runScenarios(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) throws Throwable
	 {
		 ExtentReportTestFactory.createNewTest(featureWrapper.toString(),pickleWrapper.toString());
		 ExtentReportTestFactory.assignTestCategories(pickleWrapper.getPickle().getTags());
		 openApplitoolEye( pickleWrapper,  featureWrapper);
		 cucumberRunner.runScenario(pickleWrapper.getPickle());
		 AssertionFactory.getSoftAssert().assertAll();
		 closeApplitoolEye();
	 }
	 
	 private void openApplitoolEye(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper)
	 {
		 if(ApplitoolEyes.enabled==true)
			 ApplitoolEyes.getEyes().open(WebDriverFactory.getDriver(),featureWrapper.toString(), pickleWrapper.toString());
	 }
	 
	 private void closeApplitoolEye()
	 {
		 if(ApplitoolEyes.enabled==true)
			 ApplitoolEyes.getEyes().close();
	 }
	
}

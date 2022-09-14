package org.iqa.test.runner;

import org.iqa.suite.commons.AssertionFactory;
import org.iqa.suite.commons.TestMetaData;
import org.iqa.suite.commons.reporting.ExtentReportTestFactory;
import org.iqa.test.base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;

public class ParallelTestRunner extends BaseTest{
	
	private static final Logger logger = LoggerFactory.getLogger(ParallelTestRunner.class);

	
	 @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "cucumber-examples-parallel")
	 public void runScenarios(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) throws Throwable
	 {
		 logger.info("********** Scenario execution begins : "+pickleWrapper.toString());
		 TestMetaData.setFeatureWrapper(featureWrapper);
		 TestMetaData.setPickleWrapper(pickleWrapper);
		 
		 ExtentReportTestFactory.createNewTest(featureWrapper.toString(),pickleWrapper.toString());
		 ExtentReportTestFactory.assignTestCategories(pickleWrapper.getPickle().getTags());
		 TestMetaData.setTestTags(pickleWrapper.getPickle().getTags());
		 openApplitoolEye( pickleWrapper,  featureWrapper);
		 cucumberRunner.runScenario(pickleWrapper.getPickle());
		 AssertionFactory.getSoftAssert().assertAll();
		 logger.info("********** Scenario execution commpleted: "+pickleWrapper.toString());
	 }
	
}

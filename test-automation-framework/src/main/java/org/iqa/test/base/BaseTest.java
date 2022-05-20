package org.iqa.test.base;

import org.iqa.suite.commons.PropertyHolder;
import org.iqa.suite.commons.TestMetaData;
import org.iqa.suite.commons.applitool.ApplitoolEyes;
import org.iqa.suite.commons.listeners.SeleniumMethodInvocationListener;
import org.iqa.suite.commons.reporting.ExtentReportTestFactory;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.applitools.eyes.selenium.Eyes;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

@Listeners(SeleniumMethodInvocationListener.class)
public class BaseTest {

	public TestNGCucumberRunner cucumberRunner;

	private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	
	@AfterClass(alwaysRun=true)
	public void afterClass()
	{
		cucumberRunner.finish(); // Close used instances and print cucumber summary of execution. Ex. missing step definitions 
	}
	
	 @Parameters({"featureFilePath","glueCodePackageName","tagsToExecute"})
	 @BeforeSuite
	 public void beforeSuite(String featureFilePath, String glueCodePackageName, String tagsToExecute)
	 {
		 String tagToIncludeCommandOption = tagsToExecute.trim()!="" &&tagsToExecute!=null?" --tags \""+tagsToExecute+"\"":"";
		 System.out.println("********* tags to execute command" +tagToIncludeCommandOption);
		 System.setProperty("cucumber.options",featureFilePath+" --glue "+glueCodePackageName+" "+tagToIncludeCommandOption);
		 
		 cucumberRunner = new TestNGCucumberRunner(this.getClass());
		 
		 this.loadAllconfiguration();
		 this.openApplitoolEye();
	 }
	 
	 
	 @AfterSuite
	 public void afterSuite()
	 {
			ExtentReportTestFactory.flushReport();
	 }
	 
	 @DataProvider(name="cucumber-examples-parallel",parallel=true)
	 public Object[][] dataProviderParallel()
	 {
		 return cucumberRunner.provideScenarios();
	 }
	 
	 @DataProvider(name="cucumber-examples-sequential",parallel=false)
	 public Object[][] dataProviderSequencrial()
	 {
		 return cucumberRunner.provideScenarios();
	 } 
	 

	 private void loadAllconfiguration()
	 {
		 PropertyHolder.loadWebDriverConfig();
		 PropertyHolder.loadGeneralConfig();
		 PropertyHolder.loadApplitoolConfig();
		 
	 }
	 
	 private void openApplitoolEye()
	 {
		 if(null!=PropertyHolder.testSuiteConfigurationProperties.get("EYE_ENABLE") && new Boolean(PropertyHolder.testSuiteConfigurationProperties.get("EYE_ENABLE").toString())==true)
		 {
			 ApplitoolEyes.enabled=true;
			 ApplitoolEyes.setApplitoolCongfig(System.getenv("APPLITOOLS_API_KEY"),PropertyHolder.testSuiteConfigurationProperties.get("BATCH_NAME").toString());
			 logger.info("Applitool configuration setup done...");
			 System.out.println("Applitool configuration setup done...");
		 }
	 }
	 
	 protected void openApplitoolEye(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper)
	 {
		 if(ApplitoolEyes.enabled==true && TestMetaData.getTestTags().contains("@ScreenValidation"))
			 ApplitoolEyes.createEyes().open(WebDriverFactory.getDriver(),featureWrapper.toString(), pickleWrapper.toString());
	 }
}

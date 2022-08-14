package com.orangehrm.pages.web;

import org.iqa.suite.commons.PropertyHolder;
import org.iqa.suite.commons.TestMetaData;
import org.iqa.suite.commons.applitool.ApplitoolEyesWeb;
import org.iqa.test.base.BasePageBrowser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BasePageBrowser{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

	
	@FindBy(xpath="//input[@id='txtUsername']")	
	private WebElement userName;	
	
	@FindBy(xpath="//input[@id='txtPassword']")
	private WebElement password;
	
	@FindBy(xpath="//*[@id='btnLogin']")
	private WebElement loginButton;
	
	
	
	public LoginPage navigateToOrangeHrmApplication()
	{
		String ENVIRONMENT = PropertyHolder.testSuiteConfigurationProperties.getProperty("ENVIRONMENT");
		driver.get(PropertyHolder.testSuiteConfigurationProperties.getProperty(ENVIRONMENT+"_URL"));
		
		
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(driver.getPageSource());
		//setRunTimeTestData("BrowserTitle", driver.getTitle());
		return this;
	}
	
	public void loginForSuccess(String userName, String password)
	{
		logger.info("********** In verify welcome message method");
		this.userName.sendKeys(userName);
		this.password.sendKeys(password);
		this.loginButton.click();
	}

	public LoginPage isPageLoaded() {
		
		if (TestMetaData.getTestTags().contains("@ScreenValidation") && ApplitoolEyesWeb.enabled) {
            ApplitoolEyesWeb.getEyes().checkWindow("Login Page");
        }
		
		return this;
		
	}

}

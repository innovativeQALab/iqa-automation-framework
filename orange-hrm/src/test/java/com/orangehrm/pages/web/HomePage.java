package com.orangehrm.pages.web;

import org.iqa.suite.commons.TestMetaData;
import org.iqa.suite.commons.applitool.ApplitoolEyesWeb;
import org.iqa.test.base.BasePageBrowser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends BasePageBrowser {

	//BasePage provides driver object, logger object
	
	@FindBy(id = "welcome")
	private WebElement welComeText;

	public HomePage verifyWelcomeText(String strWelComeText) {
		logger.info("********** In verify welcome message method");
		logger.info("********** Run time data stored check - Title "+getRunTimeTestData("BrowserTitle"));
		Assert.assertEquals(welComeText.getText(), strWelComeText,"Test case failed because expected data not found");
		return this;
	}

	public HomePage isPageLoaded() {
		if (TestMetaData.getTestTags().contains("@ScreenValidation") && ApplitoolEyesWeb.enabled) {
            ApplitoolEyesWeb.getEyes().checkWindow("Home Page");
        }
		return this;
	}
}

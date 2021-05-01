package com.orangehrm.pages.web;

import org.iqa.test.base.BasePageBrowser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import io.cucumber.java.en.Then;

public class HomePage extends BasePageBrowser {

	//BasePage provides driver object, logger object
	
	@FindBy(id = "welcome")
	private WebElement welComeText;

	@Then("user should be able to see {string} message")
	public HomePage verifyWelcomeText(String strWelComeText) {
		logger.info("********** In verify welcome message method");
		logger.info("********** Run time data stored check - Title "+getRunTimeTestData("BrowserTitle"));
		Assert.assertEquals(welComeText.getText(), strWelComeText,"Test case failed because expected data not found");
		return this;
	}
}

package com.orangehrm.pages.mobile;

import org.iqa.test.base.BasePageMobile;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.java.en.Then;

public class HomePage extends BasePageMobile {

	//BasePage provides driver object, logger object
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Welcome Admin']")
	private WebElement welComeText;

	@Then("user should be able to see {string} message")
	public HomePage verifyWelcomeText(String strWelComeText) {
		System.out.println(driver.getPageSource());
		logger.info("********** In verify welcome message method");
		//logger.info("********** Run time data stored check - Title "+getRunTimeTestData("BrowserTitle"));
		Assert.assertEquals(welComeText.getText(), strWelComeText,"Test case failed because expected data not found");
		return this;
	}
}

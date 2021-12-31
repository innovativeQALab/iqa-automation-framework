package com.orangehrm.step_def.web;

import com.orangehrm.pages.web.HomePage;
import com.orangehrm.pages.web.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

	
	@Given("user navigate to orange hrm URL")
	public void navigateToOrangeHrmSite()
	{
		new LoginPage().navigateToOrangeHrmApplication().isPageLoaded();
	}
	
	@When("uses enters user name as {string} and password as {string} and click on login button")
	public void login(String userName, String password)
	{
		new LoginPage().loginForSuccess(userName, password);
	}
	
	@Then("user should be able to see {string} message")
	public void verifyWelcomeText(String strWelComeText) {
		new HomePage().isPageLoaded().verifyWelcomeText(strWelComeText);
	}
}

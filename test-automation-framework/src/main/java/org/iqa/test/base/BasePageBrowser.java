package org.iqa.test.base;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.iqa.suite.commons.AssertionFactory;
import org.iqa.suite.commons.ExtentLogger;
import org.iqa.suite.commons.applitool.ApplitoolEyesWeb;
import org.iqa.test.test_data.RuntimeTestDataHolder;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.asserts.SoftAssert;
import com.applitools.eyes.selenium.Eyes;

public class BasePageBrowser {

	protected SoftAssert softAssert;
	protected Wait<WebDriver> fluentwait;
	protected WebDriver driver;
	public ExtentLogger logger = new ExtentLogger();
	public Eyes eye = ApplitoolEyesWeb.getEyesForPageObject();
	private Map<String, String> runTimeTestData = new HashMap<String, String>();

	public BasePageBrowser() {
		driver = WebDriverFactory.getDriver();
		fluentwait = getFluentWaitTimeout();
		softAssert = new SoftAssert();
		AssertionFactory.setSoftAssert(softAssert);

		PageFactory.initElements(driver, this);
		runTimeTestData = RuntimeTestDataHolder.getRunTimeTestData();
	}

	public Wait<WebDriver> getFluentWaitTimeout() {
		return new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(15)).pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
	}

	public String getRunTimeTestData(String key) {
		return runTimeTestData.get(key);
	}

	public void setRunTimeTestData(String key, String value) {

		if (runTimeTestData.containsKey(key)) {
			logger.info("!!!!!!!!!! Duplicate Runtime Data Key inserstion found. Key " + key
					+ " was already present in Map with value " + runTimeTestData.get(key)
					+ ". Same key is being asked to insert again, so, overwriting it with new value " + value
					+ ". Confirm if it is intended.");
		}
		runTimeTestData.put(key, value);
	}

}

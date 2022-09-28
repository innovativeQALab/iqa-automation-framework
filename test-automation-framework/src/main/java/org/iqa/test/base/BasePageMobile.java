package org.iqa.test.base;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.iqa.suite.commons.ExtentLogger;
import org.iqa.suite.commons.applitool.ApplitoolEyesWeb;
import org.iqa.test.test_data.RuntimeTestDataHolder;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestException;
import org.testng.asserts.SoftAssert;
import com.applitools.eyes.selenium.Eyes;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BasePageMobile {

	protected SoftAssert softAssert;
	@SuppressWarnings("rawtypes")
	protected Wait<AppiumDriver> fluentwait;
	@SuppressWarnings("rawtypes")
	protected AppiumDriver driver;
	protected ExtentLogger extentLogger = new ExtentLogger();
	protected static final Logger logger = LoggerFactory.getLogger(BasePageBrowser.class);
	private static final int DEFAULT_WAIT_TIMEOUT = 30;
	protected Eyes eye = ApplitoolEyesWeb.getEyesForPageObject();
	private Map<String, String> runTimeTestData = new HashMap<String, String>();

	@SuppressWarnings("rawtypes")
	public BasePageMobile() {
		try {
			driver = (AppiumDriver) WebDriverFactory.getDriver();
			fluentwait = getFluentWaitTimeout();
			PageFactory.initElements(new AppiumFieldDecorator(driver), this);
			runTimeTestData = RuntimeTestDataHolder.getRunTimeTestData();
		} catch (Exception e) {
			logger.error(
					"!!!!!!!!!ERROR AppiumDriver driver found as NULL. Please check if AUT property is correctly set as AUT=MOBILE in Environment.properties file. Exiting...");
			System.exit(-1);
		}

	}

	/**
	 * Get FluentWait with DEFAULT_WAIT_TIMEOUT
	 * 
	 * @return Wait<WebDriver>
	 */

	@SuppressWarnings("rawtypes")
	protected Wait<AppiumDriver> getFluentWaitTimeout() {
		return new FluentWait<AppiumDriver>((AppiumDriver) driver) .withTimeout(Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT))
				.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
	}

	/**
	 * Get FluentWait with given TIMEOUT
	 * 
	 * @param int timeOutInSeconds
	 * @return Wait<WebDriver>
	 */

	protected Wait<WebDriver> getFluentWaitTimeout(int timeOutInSeconds) {
		return new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOutInSeconds))
				.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
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

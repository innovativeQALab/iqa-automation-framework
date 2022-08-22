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
import com.applitools.eyes.selenium.Eyes;

public class BasePageBrowser {

	protected Wait<WebDriver> fluentwait;
	protected WebDriver driver;
	protected ExtentLogger extentLogger = new ExtentLogger();
	protected static final Logger logger = LoggerFactory.getLogger(BasePageBrowser.class);
	public Eyes eye = ApplitoolEyesWeb.getEyesForPageObject();
	private Map<String, String> runTimeTestData = new HashMap<String, String>();
	private static final int DEFAULT_WAIT_TIMEOUT = 30;

	public BasePageBrowser() {
		try {
			driver = WebDriverFactory.getDriver();
			fluentwait = getFluentWaitTimeout();
			PageFactory.initElements(driver, this);
			runTimeTestData = RuntimeTestDataHolder.getRunTimeTestData();
		} catch (Exception e) {
			logger.error(
					"!!!!!!!!!ERROR WebDriver driver found as NULL. Please check if AUT property is correctly set as UI for Web Automation in properties. Exiting...");
			System.exit(-1);
		}

	}

	/**
	 * Get FluentWait with DEFAULT_WAIT_TIMEOUT
	 * 
	 * @return Wait<WebDriver>
	 */

	protected Wait<WebDriver> getFluentWaitTimeout() {
		return new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT))
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

	/**
	 * Click on WebElement using JavaScriptExecutor
	 * 
	 * @param WebElement element
	 */
	protected void clickOnElementUsingJS(WebElement element) {

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		try {
			jsExecutor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			logger.error("!!!!!!!ERROR Element is not clickable using JavascriptExecutor\n" + e.getMessage());
			throw new TestException(
					String.format("The element is not clickable using JavascriptExecutor : [%s]", element));
		}
	}

}

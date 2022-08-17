package org.iqa.test.base;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.iqa.suite.commons.AssertionFactory;
import org.iqa.suite.commons.ExtentLogger;
import org.iqa.suite.commons.applitool.ApplitoolEyesWeb;
import org.iqa.test.test_data.RuntimeTestDataHolder;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestException;
import org.testng.asserts.SoftAssert;
import com.applitools.eyes.selenium.Eyes;

public class BasePageBrowser {

	protected SoftAssert softAssert;
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
			softAssert = new SoftAssert();
			AssertionFactory.setSoftAssert(softAssert);
			PageFactory.initElements(driver, this);
			runTimeTestData = RuntimeTestDataHolder.getRunTimeTestData();
		} catch (Exception e) {
			logger.error(
					"!!!!!!!!!ERROR WebDriver driver found as NULL. Please check if AUT property is correctly set as UI for Web Automation in properties. Exiting...");
			System.exit(-1);
		}

	}

	public Wait<WebDriver> getFluentWaitTimeout() {
		return new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT))
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
	 * Navigate to given URL
	 *
	 * @param String url
	 */
	protected void navigateToUrl(String url) {
		driver.get(url);
		driver.manage().window().maximize();
	}

	/**
	 * Wait for an element to be visible on the screen, given a maximum time to wait
	 * 
	 * @param WebElement element
	 * @param int        timeOutInSeconds
	 * @return WebElement
	 */
	protected WebElement waitForElementToBeVisible(WebElement element, int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);

		try {
			return wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			logger.error("!!!!!!!ERROR Element is not visible\n" + e.getMessage());
			throw new TestException(String.format("The element is not visible on screen : [%s]", element));
		}
	}

	/**
	 * Wait for an element to be visible on the screen, using the
	 * DEFAULT_WAIT_TIMEOUT
	 * 
	 * @param WebElement element
	 * @return WebElement
	 */
	protected WebElement waitForElementToBeVisible(WebElement element) {
		return waitForElementToBeVisible(element, DEFAULT_WAIT_TIMEOUT);
	}

	/**
	 * Wait for an element to be clickable, given a maximum time to wait
	 * 
	 * @param WebElement element
	 * @param int        timeOutInSeconds
	 * @return WebElement
	 */
	protected WebElement waitForElementToBeClickable(WebElement element, int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);

		try {
			return wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			logger.error("!!!!!!!ERROR Element is not clickable\n" + e.getMessage());
			throw new TestException(String.format("The element is not clickable : [%s]", element));
		}
	}

	/**
	 * Wait for an element to be visible on the screen, using the
	 * DEFAULT_WAIT_TIMEOUT
	 * 
	 * @param WebElement element
	 * @return WebElement
	 */
	protected WebElement waitForElementToBeClickable(WebElement element) {
		return waitForElementToBeClickable(element, DEFAULT_WAIT_TIMEOUT);
	}

	/**
	 * Click on WebElement
	 * 
	 * @param WebElement element
	 */
	protected void clickOnElement(WebElement element) {
		waitForElementToBeClickable(element, DEFAULT_WAIT_TIMEOUT);
		try {
			element.click();
		} catch (Exception e) {
			logger.error("!!!!!!!ERROR Element is not clickable\n" + e.getMessage());
			throw new TestException(String.format("The element is not clickable : [%s]", element));
		}
	}

	/**
	 * Clear Text field
	 * 
	 * @param WebElement element
	 */
	protected void clearText(WebElement element) {
		waitForElementToBeVisible(element, DEFAULT_WAIT_TIMEOUT);
		try {
			element.clear();
		} catch (Exception e) {
			logger.error("!!!!!!!ERROR in clearing the element\n" + e.getMessage());
			throw new TestException(String.format("The text was not cleared for element : [%s]", element));
		}
	}

	/**
	 * Enter text in field
	 * 
	 * @param WebElement element
	 * @Param String textToBeEntered
	 */
	protected void enterText(WebElement element, String textToBeEntered) {
		clearText(element);
		try {
			element.sendKeys(textToBeEntered);
		} catch (Exception e) {
			logger.error("!!!!!!!ERROR in sending text to the element\n" + e.getMessage());
			throw new TestException(String.format("The text was not sent to element : [%s]", element));
		}
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

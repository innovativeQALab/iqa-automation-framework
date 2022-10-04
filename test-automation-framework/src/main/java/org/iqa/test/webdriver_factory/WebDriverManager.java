package org.iqa.test.webdriver_factory;

import java.net.MalformedURLException;
import java.net.URL;
import org.iqa.suite.commons.PropertyHolder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class WebDriverManager {
	private static final Logger logger = LoggerFactory.getLogger(WebDriverManager.class);

	synchronized public static WebDriver CreateInstance() throws MalformedURLException {
		WebDriver dr = null;
		try {
			if (PropertyHolder.testSuiteConfigurationProperties.getProperty("DRIVER").toString().contains("BROWSER")) {
				System.setProperty(PropertyHolder.testSuiteConfigurationProperties.getProperty("DRIVER_PROPERTY_NAME"),
						PropertyHolder.testSuiteConfigurationProperties.getProperty("DRIVER_EXECUTABLE_PATH"));
				if (PropertyHolder.testSuiteConfigurationProperties.getProperty("browser").toString()
						.contains("chrome"))
					dr = new ChromeDriver();
				else if (PropertyHolder.testSuiteConfigurationProperties.getProperty("browser").toString()
						.contains("firefox"))
					dr = new FirefoxDriver();
				else if (PropertyHolder.testSuiteConfigurationProperties.getProperty("browser").toString()
						.contains("ie"))
					dr = new InternetExplorerDriver();
				else {
					logger.error("!!!!!!!!!!Error - Browser Name is not found. Exiting...");
					System.exit(-1);
				}

			} else if (PropertyHolder.testSuiteConfigurationProperties.getProperty("DRIVER").toString()
					.contains("REMOTE")) {

				logger.info("****** Before Webdriver object creation");

				if (PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
						.equalsIgnoreCase("Android"))
					dr = new AndroidDriver<WebElement>(
							new URL(PropertyHolder.testSuiteConfigurationProperties.getProperty("hubUrl").toString()),
							CapabilityFactory.getDesiredCapabilities());
				else if (PropertyHolder.testSuiteConfigurationProperties.getProperty("platform").toString()
						.equalsIgnoreCase("iOS"))
					dr = new IOSDriver<WebElement>(
							new URL(PropertyHolder.testSuiteConfigurationProperties.getProperty("hubUrl").toString()),
							CapabilityFactory.getDesiredCapabilities());
				else
					dr = new RemoteWebDriver(
							new URL(PropertyHolder.testSuiteConfigurationProperties.getProperty("hubUrl").toString()),
							CapabilityFactory.getDesiredCapabilities());

				logger.info("****** After Webdriver object creation");

			} else {
				logger.error("!!!!!!!!ERROR - Unable to create driver object");
				System.exit(-1);
			}
		} catch (Exception e) {
			logger.error("!!!!!!!!!!ERROR - Unable to create browser object. ");
			e.printStackTrace();
		}
		logger.info("******** In after object creation");
		return dr;
	}
}

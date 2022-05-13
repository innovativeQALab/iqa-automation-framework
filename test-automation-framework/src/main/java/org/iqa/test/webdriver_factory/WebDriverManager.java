package org.iqa.test.webdriver_factory;

import java.net.MalformedURLException;
import java.net.URL;

import org.iqa.suite.commons.PropertyHolder;
import org.iqa.suite.commons.listeners.SeleniumMethodInvocationListener;
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
    private static final Logger logger = LoggerFactory.getLogger(SeleniumMethodInvocationListener.class);

	private static String driverMode;
	private static String browser;
	private static String browserVersion;
	private static String platform;
	private static String hubUrl;
	private static String driverPropertyName;
	private static String driverExecutablePath;

	static {
		loadDesiredData();
	}

	synchronized public static WebDriver CreateInstance() throws MalformedURLException {
		WebDriver dr = null;
		try {	
			if (driverMode.contains("BROWSER")) {
			System.setProperty(driverPropertyName, driverExecutablePath);
				if (browser.contains("chrome"))
					dr = new ChromeDriver();
				else if (browser.contains("firefox"))
					dr = new FirefoxDriver();
				else if (browser.contains("ie"))
					dr = new InternetExplorerDriver();
				else {
					System.out.println("!!!!!!!!!!  Browser Name is not found. Exiting");
					System.exit(-1);
				}

		} else if (driverMode.contains("REMOTE")) {
			
			System.out.println("****** Before Webdriver object creation");


			if (platform.equalsIgnoreCase("Android"))
				dr = new AndroidDriver<WebElement>(new URL(hubUrl), CapabilityFactory.getDesiredCapabilities());
			else if(platform.equalsIgnoreCase("iOS"))
				dr = new IOSDriver<WebElement>(new URL(hubUrl), CapabilityFactory.getDesiredCapabilities());
			else
				dr = new RemoteWebDriver(new URL(hubUrl), CapabilityFactory.getDesiredCapabilities());
			
			
			System.out.println("****** After Webdriver object creation");

		} else {
			System.out.println("!!!!!!!! Unable to create driver object");
			System.exit(-1);
		}
		} catch (Exception e) {
			logger.error("!!!!!!!!!! Unable to create browser object. ");
			e.printStackTrace();
		}
		System.out.println("******** In after object creation");
		return dr;
	}

	private static void loadDesiredData() {

		if (null == System.getenv("browser")) {
			loadConfigFromPropertyFile();
		} else {
			loadConfigFromSystemProperty();
		}

	}

	private static void loadConfigFromSystemProperty() {
		platform = System.getenv("platform");
		hubUrl = System.getenv("hubUrl");
		driverMode = "REMOTE";
	}

	private static void loadConfigFromPropertyFile() {
		if (null == PropertyHolder.testSuiteConfigurationProperties) {
			System.out.println(
					"!!!!!! Webdriver Object Creation failed. webdriverProperties.properties does not load properly");
			System.exit(-1);
		}else {
			System.out.println(
					"*********** Webdriver property file loaded");
		}

		if (PropertyHolder.testSuiteConfigurationProperties.get("DRIVER").toString().contentEquals("BROWSER")) {
			
			browser = PropertyHolder.testSuiteConfigurationProperties.getProperty("browser");
			browserVersion = PropertyHolder.testSuiteConfigurationProperties.getProperty("browserVersion");
			platform = PropertyHolder.testSuiteConfigurationProperties.getProperty("platform");
			driverMode = "BROWSER";
			driverPropertyName = PropertyHolder.testSuiteConfigurationProperties.getProperty("DRIVER_PROPERTY_NAME");
			driverExecutablePath = PropertyHolder.testSuiteConfigurationProperties.getProperty("DRIVER_EXECUTABLE_PATH");
			
		} else if (PropertyHolder.testSuiteConfigurationProperties.get("DRIVER").toString().contentEquals("REMOTE")) {
			platform = PropertyHolder.testSuiteConfigurationProperties.getProperty("platform");
			hubUrl = PropertyHolder.testSuiteConfigurationProperties.getProperty("hubUrl");
			driverMode = "REMOTE";	
			System.out.println(
					"************** Driver mode found as REMOTE.");
		} else {
			System.out.println(
					"!!!!!! DRIVER property from webdriverProperties.properties should be either REMOTE or BROWSER.");
			System.exit(-1);
		}

	}

}

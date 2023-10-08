package org.iqa.test.webdriver_factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;
import org.iqa.suite.commons.PropertyHolder;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;

public class CapabilityFactory {
	private static final Logger logger = LoggerFactory.getLogger(CapabilityFactory.class);
	private static ChromeOptions chromeOptions = new ChromeOptions(); 
	private static FirefoxOptions firefoxOptions = new FirefoxOptions(); 
	private static UiAutomator2Options androidOptions = new UiAutomator2Options();
	private static XCUITestOptions iOSOptions = new XCUITestOptions();

	static {
		String AUT = PropertyHolder.testSuiteConfigurationProperties.getProperty("AUT").toString();
		logger.debug("*********** Static block start. AUT - " + AUT);
		switch (AUT) {
		case "ANDROID":
			logger.debug("*********** ANDROID IN");
			setAndroidOptions(getProperties("AndroidCapabilities.properties"));
			logger.debug("*********** ANDROIF OUT");
			logger.info("******************* AndroidOptions set as: ********************");
			printCapabilities(getAndroidOptions());		
			break;
		case "IOS":
			logger.debug("*********** IOS IN");
			setIOSOptions(getProperties("iOSCapabilities.properties"));
			logger.debug("*********** IOS OUT");
			logger.info("******************* IOSOptions set as: ********************");
			printCapabilities(getIOSOptions());		
			break;
		default:
			logger.debug("*********** BROWSER IN");
			setBrowserOptions(getProperties("BrowserCapabilities.properties"));
			logger.debug("*********** BROWSER OUT");
			logger.info("******************* BrowserOptions set as: ********************");
			printCapabilities(getIOSOptions());	
			break;
		}
		logger.debug("*********** Static block end");
		logger.info("********************************************************************");
	}
	
	public static void printCapabilities(Object options) {
        Class<?> optionsClass = options.getClass();
        System.out.println("Capabilities for " + optionsClass.getSimpleName() + ":");

        Field[] fields = optionsClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(options);
                System.out.println(field.getName() + ": " + value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
	
	public static UiAutomator2Options getAndroidOptions() {
		if (null == androidOptions) {
			logger.error("!!!!!!!!!!!  Desired capability found null");
		}
		return androidOptions;
	}

	public static XCUITestOptions getIOSOptions() {
		if (null == iOSOptions) {
			logger.error("!!!!!!!!!!!  XCUITestOptions found null");
		}
		return iOSOptions;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBrowserOptions() {
		String browserName = PropertyHolder.testSuiteConfigurationProperties.getProperty("browser").toString();
		if (browserName.equalsIgnoreCase("chrome")) {
			if (null == chromeOptions) {
				logger.error("!!!!!!!!!!!  ChromeOptions found null");
			}
			return (T) chromeOptions;
		} else if (browserName.equalsIgnoreCase("firefox")) {
			if (null == firefoxOptions) {
				logger.error("!!!!!!!!!!!  FirefoxOptions found null");
			}
			return (T) firefoxOptions;
		} else {
			return null;
		}
	}
	
	private static void setBrowserOptions(Properties properties) {
		 String browserName = PropertyHolder.testSuiteConfigurationProperties.getProperty("browser").toString();
	        if (browserName.equalsIgnoreCase("chrome")) {
	        	chromeOptions.setCapability("platformName", "Windows 10");
	        	chromeOptions.setCapability("browserVersion", "latest");
	        } else if (browserName.equalsIgnoreCase("firefox")) {
	        	firefoxOptions.setCapability("platformName", "Windows 10");
	        	firefoxOptions.setCapability("browserVersion", "latest");
	        } else {
	            throw new IllegalArgumentException("Unsupported browser: " + browserName);
	        }
		
	}

	private static void setIOSOptions(Properties properties) {

		iOSOptions.setDeviceName(properties.getProperty("deviceName"))
				.setBundleId(properties.getProperty("appActivity"))
				.setPlatformName(properties.getProperty("platformName", "iOS"))
				.setPlatformVersion(properties.getProperty("platformVersion")).setApp(properties.getProperty("app"))
				.setUdid(properties.getProperty("udid"))
				.setAutomationName(properties.getProperty("automationName", "XCUITest"));

	}

	private static void setAndroidOptions(Properties properties) {
		androidOptions.setDeviceName(properties.getProperty("deviceName"))
				.setAppActivity(properties.getProperty("appActivity"))
				.setAppPackage(properties.getProperty("appPackage")).setApp(properties.getProperty("app"))
				.setNoReset(Boolean.parseBoolean(properties.getProperty("noReset")))
				.setOrientation(screenOrientation(properties))
				.setPlatformName(properties.getProperty("platformName", "Android"))
				.setPlatformVersion(properties.getProperty("platformVersion"))
				.setAutomationName(properties.getProperty("automationName", "UIAutomator2"));
	}
	
	private static ScreenOrientation screenOrientation(Properties properties) {
		String deviceOrientation = null;
		deviceOrientation = properties.getProperty("deviceOrientation");
		if (deviceOrientation.equalsIgnoreCase("POTRAIT"))
			return ScreenOrientation.PORTRAIT;
		else if (deviceOrientation.equalsIgnoreCase("LANDSCAPE"))
			return ScreenOrientation.LANDSCAPE;
		else
			return null;

	}

	private static Properties getProperties(String propertyFileName) {
		logger.debug("getProperties IN - " + propertyFileName);
		InputStream input;
		Properties properties = new Properties();
		try {
			input = new FileInputStream("src/test/resources/properties/capabilities/" + propertyFileName);
			properties = new Properties();
			properties.load(input);
		} catch (FileNotFoundException e) {
			logger.error("!!!!! Unable to laoad property file at location src/test/resources/properties/capabilities/"
					+ propertyFileName);
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			logger.error("!!!!! Unable to laoad property file at location src/test/resources/properties/capabilities/"
					+ propertyFileName + " \n Please check content of property file and try again.");
			e.printStackTrace();
			System.exit(-1);
		}
		logger.debug("getProperties OUT - " + propertyFileName);
		// Loading Capabilities properties into testSuiteConfigurationProperties for
		// further usage
		loadPropertiesIntoTestConfig(properties);
		return properties;
	}

	private static void loadPropertiesIntoTestConfig(Properties properties) {

		properties.forEach((k, v) -> PropertyHolder.testSuiteConfigurationProperties.put(k.toString(),
				null != PropertyHolder.testSuiteConfigurationProperties.getProperty(k.toString())
						? PropertyHolder.testSuiteConfigurationProperties.getProperty(k.toString())
						: v.toString()));
	}

}

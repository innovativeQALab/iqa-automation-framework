package org.iqa.test.webdriver_factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.iqa.suite.commons.PropertyHolder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CapabilityFactory {
	private static final Logger logger = LoggerFactory.getLogger(CapabilityFactory.class);
	private static DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

	static {
		String AUT = PropertyHolder.testSuiteConfigurationProperties.getProperty("AUT").toString();
		logger.debug("*********** Static block start. AUT - " + AUT);
		switch (AUT) {
		case "ANDROID":
			logger.debug("*********** ANDROID IN");
			fillCapabilities(getProperties("AndroidCapabilities.properties"));
			logger.debug("*********** ANDROIF OUT");
			break;
		case "IOS":
			logger.debug("*********** IOS IN");
			fillCapabilities(getProperties("iOSCapabilities.properties"));
			logger.debug("*********** IOS OUT");
			break;
		default:
			logger.debug("*********** BROWSER IN");
			fillCapabilities(getProperties("BrowserCapabilities.properties"));
			logger.debug("*********** BROWSER OUT");
			break;
		}
		logger.debug("*********** Static block end");
		logger.info("********************************************************************");
		logger.info("******************* DesiredCapabilities set as: ********************");
		desiredCapabilities.asMap().forEach((key, value) -> logger.info(key + " - " + value));
		logger.info("********************************************************************");
	}

	public static DesiredCapabilities getDesiredCapabilities() {

		if (null == desiredCapabilities) {
			logger.error("!!!!!!!!!!!  Desired capability found null");
		}
		return desiredCapabilities;
	}

	private static void fillCapabilities(Properties properties) {
		logger.debug("fillCapabilities IN - " + properties.toString());
		// Consider capablities present in testSuiteConfiguration else use from property
		// file. This is useful to pass browserName or version or package from
		// Environment or as Parameter
		properties.forEach((k, v) -> desiredCapabilities.setCapability(k.toString(),
				null != PropertyHolder.testSuiteConfigurationProperties.getProperty(k.toString())
						? PropertyHolder.testSuiteConfigurationProperties.getProperty(k.toString())
						: v.toString()));

		logger.debug("fillCapabilities OUT - " + properties.toString());
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

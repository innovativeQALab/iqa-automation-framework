package org.iqa.suite.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.iqa.suite.commons.applitool.ApplitoolEyesWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyHolder {

	public static CustomProperties testSuiteConfigurationProperties = null;

	private static final Logger logger = LoggerFactory.getLogger(PropertyHolder.class);

	public static void loadPropertyFile(String propFilePath) throws IOException {
		if (null == testSuiteConfigurationProperties)
			testSuiteConfigurationProperties = new CustomProperties();
		InputStream input = new FileInputStream(propFilePath);
		testSuiteConfigurationProperties.load(input);
	}

	public static void loadWebDriverConfig() {
		try {
			loadPropertyFile("src/test/resources/properties/framework/WebDriverConfig.properties");
			logger.info("WebDriverConfig.properties loaded.");

		} catch (IOException e) {
			logger.error(
					"Error while loading property WebDriverConfig.properties file. \n Please check content of property file and try again.");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void loadApplitoolConfig() {

		try {
			loadPropertyFile("src/test/resources/properties/framework/ApplitoolEyeConfig.properties");
			logger.info("ApplitoolEyeConfig.properties");
		} catch (IOException e) {

			logger.error(
					"Applitool Config file not found. Please create config file if you want to use Applitool here.. src/test/resources/properties/framework/ApplitoolEyeConfig.properties");
			ApplitoolEyesWeb.enabled = false;

		}
	}

	public static void loadGeneralConfig() {
		File[] files = new File("src/test/resources/properties/userDefined").listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				logger.error(
						"!!!!! Unexpected structure found : src/test/resources/properties/userDefined folder contain sub folders. It should contain only properties files. Load properties form sub folder is not yet implemented.");
			} else {
				try {
//					input = new FileInputStream(file);
//					Properties tempGeneralConfig = new Properties();
//					tempGeneralConfig.load(input);
//					generalProperties.putAll(tempGeneralConfig);
					loadPropertyFile(file.getAbsolutePath());
				} catch (FileNotFoundException e) {
					logger.error("!!!!! " + file.getAbsolutePath()
							+ " file Not found. \n Please create WebDriverConfig.properties as src/test/resources/properties/framework location and provide configuration properly.");
					logger.error("!!!!!  " + file.getAbsolutePath()
							+ " file Not found. \n Please create WebDriverConfig.properties as src/test/resources/properties/framework location and provide configuration properly.");
					e.printStackTrace();
					// System.exit(-1);
				} catch (IOException e) {
					logger.error("Error while loading property " + file.getAbsolutePath()
							+ " file. \n Please check content of property file and try again.");
					logger.error("Error while loading property " + file.getAbsolutePath()
							+ " file. \n Please check content of property file and try again.");
					e.printStackTrace();
					// System.exit(-1);
				}
			}
		}
	}
}

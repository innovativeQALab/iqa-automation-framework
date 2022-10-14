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

	public static void loadFrameworkConfig() {
		try {
			loadPropertyFile("src/test/resources/properties/framework/FrameworkConfig.properties");
			logger.debug("FrameworkConfig.properties loaded.");

		} catch (FileNotFoundException e) {
			logger.error("!!!!! FrameworkConfig.properties"
					+ " file Not found. \n Please create FrameworkConfig.properties to start with execution at location  => src/test/resources/properties/framework/FrameworkConfig.properties.");
			e.printStackTrace();
			logger.error("Exiting ...");
			System.exit(-1);
		} catch (IOException e) {
			logger.error(
					"!!!!!!Error while loading property FrameworkConfig.properties file. \n Please check content of property file and try again.");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void loadApplitoolConfig() {

		try {
			loadPropertyFile("src/test/resources/properties/framework/ApplitoolEyeConfig.properties");
			logger.debug("ApplitoolEyeConfig.properties");
		}catch (FileNotFoundException e) {
			logger.error("!!!!! ApplitoolEyeConfig.properties"
					+ " file Not found. \n Please create ApplitoolEyeConfig.properties if you want to use Applitool at location  => src/test/resources/properties/framework/ApplitoolEyeConfig.properties.");
			e.printStackTrace();
			ApplitoolEyesWeb.enabled = false;
		} 
		catch (IOException e) {
			logger.error(
					"!!!!!!Error while loading property ApplitoolEyeConfig.properties file. \n Please check content of property file and try again.");
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
					loadPropertyFile(file.getAbsolutePath());
				} catch (FileNotFoundException e) {
					logger.error("!!!!! " + file.getAbsolutePath()
							+ " file Not found. \n Please create Environment.properties file if you want to configure exeuction environment at location => src/test/resources/properties/framework/userDefined.");
					e.printStackTrace();
					// System.exit(-1);
				} catch (IOException e) {
					logger.error("Error while loading property " + file.getAbsolutePath()
							+ " file. \n Please check content of property file and try again.");
					e.printStackTrace();
					// System.exit(-1);
				}
			}
		}
	}
}

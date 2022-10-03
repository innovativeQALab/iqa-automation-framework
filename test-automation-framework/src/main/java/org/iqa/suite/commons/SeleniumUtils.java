package org.iqa.suite.commons;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumUtils {
    private static final Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

	public static void takeScreenshot(String fileNameWithPath) {
		if (null == WebDriverFactory.getDriver()) {
			logger.info("!!!!!! Webdriver is null hence returning from SeleniumUtil > takeScreenShot()");
			return;
		}
		logger.info("********** Taking screenshot at - "+fileNameWithPath);
		File scrFile = ((TakesScreenshot) WebDriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(fileNameWithPath));
		} catch (IOException e) {
			logger.info("!!!!!!!!!! exception while taking screenshot");
			e.printStackTrace();
		}
	}
	
	public static String getScreenshotAsBase64() {
		if (null == WebDriverFactory.getDriver()) {
			logger.info("!!!!!! Webdriver is null hence returning from SeleniumUtil > takeScreenShot()");
			return null;
		}
		logger.info("********** Taking screenshot as Base64 ");
		return  ((TakesScreenshot) WebDriverFactory.getDriver()).getScreenshotAs(OutputType.BASE64);	
	}

}

package org.iqa.suite.commons;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

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
		logger.info("********** Taking screenshot at - " + fileNameWithPath);
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
		return ((TakesScreenshot) WebDriverFactory.getDriver()).getScreenshotAs(OutputType.BASE64);
	}

	public static String getBase64Screenshot() {
		String base64StringOfScreenshot = "";
		if (null == WebDriverFactory.getDriver()) {
			logger.info("!!!!!! Webdriver is null hence returning from SeleniumUtil > takeScreenShot()");
			return null;
		}
		logger.info("********** Taking screenshot as Base64 ");
		File src = ((TakesScreenshot) WebDriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY_HHmmss");
		String sDate = sdf.format(date);
		String imagePath = System.getProperty("user.dir") + "/test-output/Extent-report/screenshots/" + "img_" + sDate
				+ ".png";
		byte[] fileContent = null;
		try {
			FileUtils.copyFile(src, new File(imagePath));
			fileContent = FileUtils.readFileToByteArray(src);
		} catch (IOException e) {
			e.printStackTrace();
		}
		base64StringOfScreenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
		return base64StringOfScreenshot;
	}

}

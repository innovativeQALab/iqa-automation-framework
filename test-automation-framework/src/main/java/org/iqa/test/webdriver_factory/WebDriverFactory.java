package org.iqa.test.webdriver_factory;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {
	
	private static ThreadLocal<WebDriver> webDriver= new ThreadLocal<WebDriver>();
	
	synchronized public static WebDriver getDriver(){
		return webDriver.get();		
	}
	synchronized public static void setDriver(WebDriver dr){
		webDriver.set(dr);		
	}
}

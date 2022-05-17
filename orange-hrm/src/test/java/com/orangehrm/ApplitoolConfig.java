package com.orangehrm;

import org.iqa.suite.commons.applitool.IConfigListner;

import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;

public class ApplitoolConfig implements IConfigListner{

	@Override
	public Configuration getApplitoolConfig(Configuration config) {

		config.addBrowser(800, 600, BrowserType.CHROME);
		config.addBrowser(700, 500, BrowserType.FIREFOX);
		config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
		
		return config;
	}

}

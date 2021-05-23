package org.iqa.suite.commons.applitool;

import org.iqa.suite.commons.listeners.SeleniumMethodInvocationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

public class ApplitoolEyes {
	
	private static EyesRunner runner;
	private static Eyes eyes;
	private static Configuration config ;
	public static boolean enabled=false;
	
    private static final Logger logger = LoggerFactory.getLogger(ApplitoolEyes.class);

	
	private static void setApplitoolEye() {
		runner = new ClassicRunner();
		eyes = new Eyes(runner);
		config = new Configuration();
	}
	
	public static void setApplitoolCongfig(String applitoolApiKey, String batchName)
	{
		setApplitoolEye();
		
		config.setApiKey(applitoolApiKey);

		config.setBatch(new BatchInfo(batchName));

		eyes.setConfiguration(config);
	}

	public static Eyes getEyes() {
		
		if(!ApplitoolEyes.enabled || null==eyes)
		{
			System.out.println("!!!!!!!!!! Applitool eye either disabled or not configured corrctly. Plase check Applitool configuration in src/test/resources/properties/framework/ApplitoolEyeConfig.proprtis");
			logger.error("!!!!!!!!!! Applitool eye either disabled or not configured corrctly. Plase check Applitool configuration in src/test/resources/properties/framework/ApplitoolEyeConfig.proprtis");
		}
		return eyes;
	}

	public static Eyes getEyesForPageObject() {
		return eyes;
	}
			
			

}

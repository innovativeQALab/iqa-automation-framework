package org.iqa.suite.commons.applitool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

public class ApplitoolEyes {
	
	private static EyesRunner runner;
	private static ThreadLocal<Eyes> eyes = new ThreadLocal<>();
	private static Configuration config ;
	public static boolean enabled=false;
	
    private static final Logger logger = LoggerFactory.getLogger(ApplitoolEyes.class);

	
	private static void createApplitoolEyeConfig() {
		runner = new ClassicRunner();
		config = new Configuration();
	}
	
	public static void setApplitoolCongfig(String applitoolApiKey, String batchName)
	{
		
		createApplitoolEyeConfig();
		config.setApiKey(applitoolApiKey);
		config.setBatch(new BatchInfo(batchName));

	}

	public static Eyes getEyes() {
		
		if(!ApplitoolEyes.enabled )
		{
			System.out.println("!!!!!!!!!! Applitool eye either disabled or not configured corrctly. Plase check Applitool configuration in src/test/resources/properties/framework/ApplitoolEyeConfig.proprtis");
			logger.error("!!!!!!!!!! Applitool eye either disabled or not configured corrctly. Plase check Applitool configuration in src/test/resources/properties/framework/ApplitoolEyeConfig.proprtis");
		}else {
			
		}
		return eyes.get();
	}

	public static Eyes getEyesForPageObject() {
		return eyes.get();
	}

	public static Eyes createEyes() {
		if(ApplitoolEyes.enabled )
		{
		eyes.set(new Eyes(runner));
		eyes.get().setConfiguration(config);
		}
		return eyes.get();
	}
			
			

}

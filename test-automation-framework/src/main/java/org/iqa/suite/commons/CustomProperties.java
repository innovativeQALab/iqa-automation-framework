package org.iqa.suite.commons;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomProperties extends Properties {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CustomProperties.class);

	@Override
	public String getProperty(String key) {
		if (null != System.getenv(key)) {
			logger.info("Property '" + key + "' obtained from System environment variable");
			System.out.println("Property '" + key + "' obtained from System environment variable as "+System.getenv(key));
			return System.getenv(key);
		}else if (null != System.getProperty(key)) {
			System.out.println("Property '" + key + "' obtained from System Propertiesas "+System.getProperty(key));
			return System.getProperty(key);
		} else if (null != super.getProperty(key)) {
			System.out.println("Property '" + key + "' obtained from Property files as"+super.getProperty(key));
			return super.getProperty(key);
		} else {
			System.out.println("Failed to fetch Property '"+ key+"' Please verify property is defined");
			return null;
		}
	}

}

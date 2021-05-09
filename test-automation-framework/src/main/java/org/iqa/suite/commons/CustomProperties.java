package org.iqa.suite.commons;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomProperties extends Properties {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CustomProperties.class);

	@Override
	public String getProperty(String key) {
		if (null != System.getProperty(key)) {
			logger.info("Property '" + key + "' obtained from System Properties");
			return System.getProperty(key);
		} 
		if (null != super.getProperty(key)) {
			logger.debug("Property '" + key + "' obtained from user defined Properties");
			return super.getProperty(key);
		} else {
			logger.error("Failed to fetch Property '"+ key+"' Please verify property is defined");
			return null;
		}
	}

}

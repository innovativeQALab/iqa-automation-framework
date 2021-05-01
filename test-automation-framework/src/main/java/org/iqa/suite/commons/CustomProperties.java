package org.iqa.suite.commons;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomProperties extends Properties {

	private static final long serialVersionUID = 1L;

	@Override
	public String getProperty(String key) {
		if (null != System.getProperty(key)) {
			log.info("Property '" + key + "' obtained from System Properties");
			return System.getProperty(key);
		} 
		if (null != super.getProperty(key)) {
			log.debug("Property '" + key + "' obtained from user defined Properties");
			return super.getProperty(key);
		} else {
			log.error("Failed to fetch Property '"+ key+"' Please verify property is defined");
			return null;
		}
	}

}

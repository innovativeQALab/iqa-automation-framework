package org.iqa.suite.commons.applitool;

import java.util.Set;

import org.iqa.suite.commons.PropertyHolder;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;

public class ApplitoolEyesWeb {

	private static final VisualGridRunner runner = new VisualGridRunner(new RunnerOptions().testConcurrency(20));
	private static ThreadLocal<Eyes> eyes = new ThreadLocal<>();
	private static Configuration config;
	public static boolean enabled = false;
	private static final Logger logger = LoggerFactory.getLogger(ApplitoolEyesWeb.class);

	private static void createApplitoolEyeConfig() {
		config = new Configuration();
	}

	@SuppressWarnings("unchecked")
	public static void setApplitoolCongfig(String applitoolApiKey) {
		createApplitoolEyeConfig();
		Reflections reflections;
		reflections = new Reflections(
				PropertyHolder.testSuiteConfigurationProperties.getProperty("APPLITOOL_CONFIG_PACKAGE").toString());
		Set<Class<? extends IConfigListner>> classes = reflections.getSubTypesOf(IConfigListner.class);
		Object[] arr = classes.toArray();
		try {
			((Class<? extends IConfigListner>) arr[0]).newInstance().getApplitoolConfig(config);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		config.setApiKey(applitoolApiKey);
		config.setBatch(
				new BatchInfo(PropertyHolder.testSuiteConfigurationProperties.getProperty("BATCH_NAME").toString()));
		config.setHostOS(PropertyHolder.testSuiteConfigurationProperties.getProperty("platformOS").toString());
	}

	public static EyesRunner getApplitoolEyeRunner() {
		return runner;
	}

	public static Eyes getEyes() {

		if (!ApplitoolEyesWeb.enabled) {
			logger.info("!!!!!!!!!! Applitool eye either disabled or not configured corrctly."
					+ "\n Please check Applitool configuration in src/test/resources/properties/framework/ApplitoolEyeConfig.proprtis");
			logger.error("!!!!!!!!!! Applitool eye either disabled or not configured corrctly."
					+ "\n Please check Applitool configuration in src/test/resources/properties/framework/ApplitoolEyeConfig.proprtis");
		} else {
		}
		return eyes.get();
	}

	public static Eyes getEyesForPageObject() {
		return eyes.get();
	}

	public static Eyes createEyes() {
		if (ApplitoolEyesWeb.enabled) {
			eyes.set(new Eyes(runner));
			eyes.get().setConfiguration(config);
		}
		return eyes.get();
	}

}

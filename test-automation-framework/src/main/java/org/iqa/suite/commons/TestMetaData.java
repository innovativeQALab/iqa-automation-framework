package org.iqa.suite.commons;

import java.util.ArrayList;
import java.util.List;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;

public class TestMetaData {
	
	private static ThreadLocal<PickleWrapper> pickleWrapper = new ThreadLocal<>();
	private static ThreadLocal<FeatureWrapper> featureWrapper = new ThreadLocal<>();

	public static PickleWrapper getPickleWrapper() {
		return pickleWrapper.get();
	}

	public static void setPickleWrapper(PickleWrapper pickleWrapper) {
		TestMetaData.pickleWrapper.set(pickleWrapper);
	}

	public static FeatureWrapper getFeatureWrapper() {
		return featureWrapper.get();
	}

	public static void setFeatureWrapper(FeatureWrapper featureWrapper) {
		TestMetaData.featureWrapper.set(featureWrapper);
	}

	public static void setTestTags(ThreadLocal<List<String>> testTags) {
		TestMetaData.testTags = testTags;
	}

	private static ThreadLocal<List<String>> testTags = new ThreadLocal<>();

	public static void initialize() {
		testTags.set(new ArrayList<String>());
	}

	public static void setTestTags(List<String> tags) {
		testTags.get().addAll(tags);
	}
	
	public static List<String> getTestTags() {
		return new ArrayList<>(testTags.get()); //Immutable object return
	}
	
}

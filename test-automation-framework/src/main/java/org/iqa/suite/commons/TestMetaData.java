package org.iqa.suite.commons;

import java.util.ArrayList;
import java.util.List;

public class TestMetaData {

	private static ThreadLocal<List<String>> testTags = new ThreadLocal<>();
	
	static
	{
		testTags.set(new ArrayList<String>());
	}

	public static void setTestTags(List<String> tags) {
		testTags.get().addAll(tags);
	}
	
	public static List<String> getTestTags() {
		return new ArrayList<>(testTags.get()); //Immutable object return
	}
	
	
}

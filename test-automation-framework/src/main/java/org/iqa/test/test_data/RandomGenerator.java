package org.iqa.test.test_data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomGenerator {

	private static final Logger logger = LoggerFactory.getLogger(RandomGenerator.class);

	/**
	 * Generate random string of given length
	 *
	 * @param int numberOfChars
	 * @return String randomValue
	 */
	public String generateRandomString(int numberOfChars) {
		String randomValue = RandomStringUtils.randomAlphabetic(numberOfChars);
		logger.info("*****RandomGenerator random string -" + randomValue);
		return randomValue;
	}

	/**
	 * Generate random string of given length with user provided prefix
	 *
	 * @param int numberOfChars
	 * @return String randomValue
	 */
	public String generateRandomStringWithPrefix(String prefix, int numberOfChars) {
		String randomValue = prefix + RandomStringUtils.randomAlphabetic(numberOfChars);
		logger.info("*****RandomGenerator random string with prefix -" + randomValue);
		return randomValue;
	}

	/**
	 * Generate random number of given length
	 *
	 * @param int numberOfDigit
	 * @return String randomValue
	 */
	public String generateRandomNumber(int numberOfDigit) {
		String randomValue = RandomStringUtils.randomNumeric(numberOfDigit);
		logger.info("*****RandomGenerator random number -" + randomValue);
		return randomValue;
	}

	/**
	 * Generate random string with user provided prefix
	 *
	 * @param int numberOfDigit
	 * @return String randomValue
	 */
	public String generateRandomNumberWithPrefix(String prefix, int numberOfDigit) {
		String randomValue = RandomStringUtils.randomNumeric(numberOfDigit);
		logger.info("*****RandomGenerator random number with prefix -" + randomValue);
		return randomValue;
	}

}

package org.iqa.test.test_data;

import java.util.Random;

import org.testng.Assert;

public class RandonNumber {

	/**
	 * Get the random number
	 * 
	 * @param numberOfDigit
	 *            is total length of return <b>String<b>
	 * @return random number in String format <br>
	 * 		<br>
	 *         <b>Example :</b> getRandomNumberOfLength(6) -> 123456
	 */
	public static String getRandomNumberOfLength(int numberOfDigit) {
		String str = "";
		Random r = new Random();
		for (int i = 1; i <= numberOfDigit; i++)
			str = str + new Integer(r.nextInt((9 - 1) + 1) + 1).toString();

		return str;
	}

	/**
	 * Get the random number and append string as prefix
	 * 
	 * @param numberOfDigit
	 *            is total length of return string including prefix
	 * @param prefix
	 *            to add before random number <br>
	 * 			<br>
	 *            <b>Example :</b> getRandomNumberOfLengthWithPrefix("USER",6) ->
	 *            USER123456
	 * 
	 */
	public static String getRandomNumberOfLengthWithPrefix(String prefix, int numberOfDigit) {
		Random r = new Random();
		for (int i = 1; i <= numberOfDigit; i++)
			prefix = prefix + new Integer(r.nextInt((9 - 1) + 1) + 1).toString();  //  VZ123456
		return prefix;
	}

	
}

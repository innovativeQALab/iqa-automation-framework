package org.iqa.test.base;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.iqa.suite.commons.AssertionFactory;
import org.iqa.suite.commons.DBUtils;
import org.iqa.suite.commons.ExtentLogger;
import org.iqa.suite.commons.PropertyHolder;
import org.iqa.suite.commons.TestMetaData;
import org.iqa.suite.commons.applitool.ApplitoolEyesWeb;
import org.iqa.suite.commons.database.DBConnection;
import org.iqa.test.test_data.RuntimeTestDataHolder;
import org.iqa.test.webdriver_factory.WebDriverFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.asserts.SoftAssert;

import com.applitools.eyes.selenium.Eyes;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
/**
 * {@inheritDoc}
 * @param otherAnimal Tasty treat.
 */
public class BasePageBrowser {

	protected SoftAssert softAssert;
	protected Wait<WebDriver> fluentwait;
	protected WebDriver driver;
	public ExtentLogger logger = new ExtentLogger();
	public Eyes eye = ApplitoolEyesWeb.getEyesForPageObject();
	private Map<String,String> runTimeTestData = new HashMap<String,String>();

	public BasePageBrowser()
	{
		driver = WebDriverFactory.getDriver();
		fluentwait = getFluentWaitTimeout();
		softAssert = new SoftAssert();
		AssertionFactory.setSoftAssert(softAssert);

		PageFactory.initElements(driver, this);
		runTimeTestData = RuntimeTestDataHolder.getRunTimeTestData();

	}

	public Wait<WebDriver> getFluentWaitTimeout() {
		return new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(15))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
		
	}

	public String getRunTimeTestData(String key) {
		return runTimeTestData.get(key);
	}
	
	

	public void setRunTimeTestData(String key, String value) {

		if(runTimeTestData.containsKey(key))
		{
			logger.info("!!!!!!!!!! Duplicate Runtime Data Key inserstion found. Key "+key+" was already present in Map with value "+runTimeTestData.get(key)+". Same key is being asked to insert again, so, overwriting it with new value "+value+". Confirm if it is intended.");
		}
		runTimeTestData.put(key, value);
	}

	/**
	 * Method executeSelectQuery is used to executing select queries
	 * @param String query to be provided with the desired select query
	 * 
	 * */    
	public ResultSet executeSelectQuery(String query){

		ResultSet result= null;

		try {
			DBConnection dbconn = DBUtils.getDBConnection();
			Connection conn = dbconn.getConn();
			result = conn.createStatement().executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}
	
	/**
	 * Method executeUpdateOrDeleteQuery will be used to perform update or delete DB opertion
	 * 
	 * 
	 * **/
	public int executeUpdateOrDeleteQuery(String query){
		int resultCount=0;
		try {
			DBConnection dbconn = DBUtils.getDBConnection();
			Connection conn = dbconn.getConn();
			resultCount = conn.createStatement().executeUpdate(query);
			logger.info("****Result count "+resultCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultCount;
	}

}

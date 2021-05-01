package org.iqa.suite.commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.iqa.suite.commons.database.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtils {

	private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);


	private static String dbUrl = "jdbc:mysql://localhost:3306/advancedjava";

	private static String userName = "root";
	private static String password = "root";

	private static List<DBConnection> dbPool = new ArrayList<DBConnection>();

	private static Connection conn;

	public static void createConnection() {
		if (null == conn) {
			try {
				String driverClassName = PropertyHolder.testSuiteConfigurationProperties.getProperty("driver.class.name");
				userName = PropertyHolder.testSuiteConfigurationProperties.getProperty("dbusername");
				password = PropertyHolder.testSuiteConfigurationProperties.getProperty("dbpasssword");
				String connectionPoolSize = PropertyHolder.testSuiteConfigurationProperties.getProperty("connection.active");
				dbUrl = PropertyHolder.testSuiteConfigurationProperties.getProperty("dbUrl");
				logger.info("********** in getConnection : connection is not yet created, creating first time.");
				Class.forName(driverClassName);
				logger.info("********** in getConnection: Connection driver ...");

				int intConnectionPoolSize = Integer.parseInt(connectionPoolSize);
				if(intConnectionPoolSize>20){
					logger.warn("connnection Size is greated then 20 which is MAX hence setting the value to 20");
					intConnectionPoolSize=20;
				}
				if(connectionPoolSize!=null){
					for (int i = 0; i < intConnectionPoolSize; i++) {
						dbPool.add(new DBConnection(DriverManager.getConnection(dbUrl, userName, password),false));

					}
				}else{
					dbPool.add(new DBConnection(DriverManager.getConnection(dbUrl, userName, password),false));
					dbPool.add(new DBConnection(DriverManager.getConnection(dbUrl, userName, password),false));
				}

			} catch (Exception e) {
				logger.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Unable to create connection \n\n\n");
				e.printStackTrace();
			}
		}	
		logger.info("********** in getConnection : connection is already exists, rending it back.");
	}

	public static DBConnection getDBConnection(){

		if(dbPool.size()<1)
			DBUtils.createConnection();
		
		for (DBConnection dbConnection : dbPool) {
			if(!dbConnection.isActive()){
				dbConnection.setActive(true);
				return dbConnection;
			}

		}
		return null;
	}





	/**
	 * Method 
	 * **/

}

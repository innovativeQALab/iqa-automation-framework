package org.iqa.suite.commons;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class DBUtils {

	private final static Logger logger = LoggerFactory.getLogger(DBUtils.class);
	private final static Map<String, DataSource> DB_POOL_MAP = new ConcurrentHashMap<>();

	/**
	 * Method getConnection is used to fetch connection from DB_POOLMAP
	 * 
	 * @param Connection conn
	 * 
	 */
	public static synchronized Connection getConnection(String dbName) {
		Connection conn = null;
		if (!DB_POOL_MAP.containsKey(dbName)) {
			createDataSource(dbName);
		}
		DataSource dataSource = DB_POOL_MAP.get(dbName);
		try {
			conn = dataSource.getConnection();
			logger.info("Fetched connection from database pool");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("!!!!!!!!!! Exception occured while fetching connection from database pool");
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Method createDataSource is used for creating the data source
	 * 
	 * @param String dbName - database name for which data source is to be created
	 * 
	 */
	private static void createDataSource(String dbName) {
		logger.info("Creating the DataSource for " + dbName);
		HikariConfig hikariConfig = getHikariConfig(dbName);
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		DB_POOL_MAP.put(dbName, hikariDataSource);
		logger.info("DataSource added to Database Pool Map");
	}

	/**
	 * Method getHikariConfig is used to set Hikari configurations
	 * 
	 * @param String dbName - database name for which Hikari configs are to be set
	 * 
	 */
	private static HikariConfig getHikariConfig(String dbName) {

		String environment = PropertyHolder.testSuiteConfigurationProperties.getProperty("ENVIRONMENT");

		if (null == PropertyHolder.testSuiteConfigurationProperties.getProperty(environment + "_" + dbName + ".url")) {
			logger.error("!!!!!!!!!!!!!ERROR - Database not definied in user defined properties...");
		}
		HikariConfig hikaConfig = new HikariConfig();
		Function<String, String> getValue = (key) -> PropertyHolder.testSuiteConfigurationProperties
				.getProperty(environment + "_" + dbName + "." + key).toString();

		hikaConfig.setJdbcUrl(getValue.apply("url"));
		hikaConfig.setUsername(getValue.apply("user"));
		hikaConfig.setPassword(getValue.apply("password"));
		hikaConfig.setDriverClassName(getValue.apply("driver"));
		hikaConfig.setPoolName(dbName);
		hikaConfig.setMaximumPoolSize(Integer.parseInt(getValue.apply("poolsize")));
		hikaConfig.setConnectionTimeout(Duration.ofSeconds(30).toMillis());
		hikaConfig.setIdleTimeout(Duration.ofMinutes(2).toMillis());
		return hikaConfig;
	}

	/**
	 * Method executeSelectQuery is used to executing select queries
	 * 
	 * @param String query - select query
	 * @param String dbName - database name for which select query is to be executed
	 * 
	 * @return ResultSet result
	 */
	public static ResultSet executeSelectQuery(String query, String dbName) {

		ResultSet result = null;
		try {
			Connection conn = DBUtils.getConnection(dbName);
			result = conn.createStatement().executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Method executeSelectQuery is used to executing select queries
	 * 
	 * @param String query - select query
	 * @param String dbName - database name for which Update or Delete queries are
	 *               to be executed
	 * 
	 * @return int resultCount
	 */
	public static int executeUpdateOrDeleteQuery(String query, String dbName) {
		int resultCount = 0;
		try {
			Connection conn = DBUtils.getConnection(dbName);
			resultCount = conn.createStatement().executeUpdate(query);
			logger.info("****Result count " + resultCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultCount;
	}
}
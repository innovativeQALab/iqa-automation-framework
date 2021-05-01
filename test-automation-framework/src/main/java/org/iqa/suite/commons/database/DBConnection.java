package org.iqa.suite.commons.database;

import java.sql.Connection;

public class DBConnection {
	private boolean isActive;
	private Connection conn;

	public DBConnection(Connection connection, boolean isActive) {
		this.conn = connection;
		this.isActive = isActive;

	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}

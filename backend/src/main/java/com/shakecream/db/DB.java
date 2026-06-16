package com.shakecream.db;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try {
			Properties props = new Properties();

			InputStream is = DB.class
					.getClassLoader()
					.getResourceAsStream("db.properties");

			if (is == null) {
				throw new DbException("db.properties not found");
			}

			props.load(is);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
}
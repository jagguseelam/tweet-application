package com.tweet_app;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Seelam Jagadeeswara Reddy
 *
 */
public class TweetConfig {

	public static Connection getDbConnection() {
		Connection connection = null;

		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream("src/resources/db.properties"));
			String driver = properties.getProperty("driver");
			String url = properties.getProperty("url");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException classNotFoundException) {
			throw new RuntimeException("Class Not Found Exception");
		} catch (IOException ioException) {
			throw new RuntimeException("IO Exception");
		} catch (SQLException sqlException) {
			throw new RuntimeException("SQL Exception");
		}

		return connection;
	}
}

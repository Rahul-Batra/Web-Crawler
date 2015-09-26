package com.rahul.investopedia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBconnection {
	private static final String SERVER ="localhost";
	private static final String PORT ="3306";
	private static final String DATABASE = "investopedia";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "admin";
	
	public static Connection getConnection() {
		String dbURL = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + DATABASE;
		Connection conn = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = (Connection) DriverManager.getConnection(dbURL, USERNAME, PASSWORD);
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
		return conn;
	}

	public static void release(Connection con, PreparedStatement statement, ResultSet resultSet) {
		try {
			if(resultSet != null) resultSet.close();
			if(statement != null) statement.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			System.out.println("[ERROR] : DBConnection : " + e.getMessage());
		}
	}
	
	public static void release(Connection con, Statement statement, ResultSet resultSet) {
		try {
			if(resultSet != null) resultSet.close();
			if(statement != null) statement.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			System.out.println("[ERROR] : DBConnection : " + e.getMessage());
		}
	}

}

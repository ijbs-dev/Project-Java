package org.exemplo.persistencia.database.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoMySQL {

	private final static String DB_ADDRESS = "localhost";
	private final static String DB_PORT = "3307";
	private final static String DB_SCHEMA = "prontuario";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "root";
	
	private static Connection connection;
	
	private ConexaoBancoMySQL() {
		
	}
	
	
	public static Connection getConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+DB_ADDRESS+":"+DB_PORT+"/"+DB_SCHEMA, DB_USER, DB_PASSWORD);
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

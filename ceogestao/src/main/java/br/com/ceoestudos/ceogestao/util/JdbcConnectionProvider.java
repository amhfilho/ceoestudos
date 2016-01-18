package br.com.ceoestudos.ceogestao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionProvider {
	
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/odontosys";
	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static Connection conn;
	
	private JdbcConnectionProvider(){
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		if(conn==null){
			Class.forName(COM_MYSQL_JDBC_DRIVER);
			conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PASSWORD);
		}
		return conn;
	}

}

package eebv.org.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.*;

public class DataBaseConnection {
	public static DataBaseConnection instance ;
	private Connection  connection;
	
	
	public static DataBaseConnection getInstance() {
		if(instance == null) {
			instance = new DataBaseConnection();
		}
		
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	
	private DataBaseConnection() {
		try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Properties p = new Properties();
                    String url = "jdbc:mysql://localhost:3306/trello?useSSL=true";
                    this.connection = DriverManager.getConnection(url, "root", "");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}

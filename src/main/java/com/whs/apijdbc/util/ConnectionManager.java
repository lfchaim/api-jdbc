package com.whs.apijdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionManager {

    private Connection connection=null;
	
	public Connection openConnection( String driver, String url, String usr, String pwd ) {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection=DriverManager.getConnection(url, usr, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
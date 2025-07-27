package com.litmus7.employeemanager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.litmus7.employeemanager.property.DatabaseProperties;

public class DBConfig {
	
	public static Connection getDBConnection() {
        String URL = DatabaseProperties.getDbUrl();
        String USERNAME = DatabaseProperties.getDbUser();
//        String USERNAME = "root";
        String PASSWORD = DatabaseProperties.getDbPassword();
        Connection conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }
        catch (SQLException e) {
            System.err.println("Connect SQL Error: " + e.getMessage());
        }

        return conn;
    }

}

package com.litmus7.employeemanager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
	
	public static Connection getDBConnection() {
        String URL = "jdbc:mysql://localhost:3306/employee_management";
        String USERNAME = "root";
        String PASSWORD = "#12345678#";
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

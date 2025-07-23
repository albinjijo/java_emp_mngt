package com.litmus;

public class EmployeeManagerApp {
    public static void main(String[] args) {
    	
    	String filename = "C:\\Users\\ALBIN JIJO\\eclipse-workspace\\EmployeeManager\\src\\Employee.csv";
        
        try {
            Response status = EmployeeManagerController.writeToDB(filename);
            System.out.println(status.getStatuscode());
            System.out.println(status.getErrormsg());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
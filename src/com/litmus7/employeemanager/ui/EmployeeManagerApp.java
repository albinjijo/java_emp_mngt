package com.litmus7.employeemanager.ui;

import java.util.List;

import com.litmus7.employeemanager.controller.EmployeeManagerController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;

public class EmployeeManagerApp {
    public static void main(String[] args) {
    	
    	String filename = "C:\\Users\\ALBIN JIJO\\eclipse-workspace\\EmployeeManager\\src\\com\\litmus7\\employeemanager\\resources\\Employee.csv";
        EmployeeManagerController employeecontroller = new EmployeeManagerController();
        
        Response status = employeecontroller.writeToDB(filename);
        System.out.println(status.getStatuscode());
        System.out.println(status.getErrormsg());
    
        
        Response<List<Employee>> employeeListResponse=employeecontroller.getAllEmployees();
        for (Employee emp: employeeListResponse.getData()) {
        	System.out.println(emp.getFirstName());
        }
    }
}
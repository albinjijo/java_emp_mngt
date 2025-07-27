package com.litmus7.employeemanager.ui;

import java.util.List;

import com.litmus7.employeemanager.controller.EmployeeManagerController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;

public class EmployeeManagerApp {
    public static void main(String[] args) {
    	
    	String filename = "C:\\Users\\ALBIN JIJO\\eclipse-workspace\\EmployeeManager\\src\\com\\litmus7\\employeemanager\\resources\\Employee.csv";
        EmployeeManagerController employeecontroller = new EmployeeManagerController();
        
        Response response = employeecontroller.writeToDB(filename);
        if (response.getStatuscode() == 200) {
            System.out.println("All records were inserted.");
        }  else {
            System.out.println("Message: " + response.getErrormsg());
        }
    
        
        Response<List<Employee>> employeeListResponse=employeecontroller.getAllEmployees();
        if(employeeListResponse.getStatuscode()!=200) {
        	System.out.println("Message: " + employeeListResponse.getErrormsg());
        }else {
        List<Employee> employees = employeeListResponse.getData();
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    } 
}
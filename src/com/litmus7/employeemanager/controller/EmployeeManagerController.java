
package com.litmus7.employeemanager.controller;


import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.constants.ApplicationStatusCodes;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.utils.CSVOperations;
import com.litmus7.employeemanager.utils.Validator;



public class EmployeeManagerController {
	static EmployeeService service= new EmployeeService();

	public static Response writeToDB(String filename){
		if (!Validator.isValidCSVFileName(filename)) {
			return new Response(400, "invalid filename",null);
		}

        
        int statuscode=ApplicationStatusCodes.SUCCESS;
        String errormsg = null;
        
        

        List<String[]> data = CSVOperations.readFromCSV(filename);

        boolean isWritten=service.writeDataToDB(data);

        if(!isWritten) {
        	statuscode=400;
        	errormsg="Error Occured";
        }
        
        return new Response(statuscode, errormsg,null); 
        
    }

	public static Response<List<Employee>> getAllEmployees() {
		// TODO Auto-generated method stub
		List<Employee> employeeList=new ArrayList<>();
		employeeList=EmployeeService.readAllFromDb();
		if (employeeList == null) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "Failed to fetch data", null);
	    }
		Response<List<Employee>> obj = new Response<>(ApplicationStatusCodes.SUCCESS,"data Fetched Succesfully",employeeList);
		return obj;
	}

}

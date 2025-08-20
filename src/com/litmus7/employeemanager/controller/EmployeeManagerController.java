
package com.litmus7.employeemanager.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.litmus7.employeemanager.constants.ApplicationStatusCodes;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.exception.EmployeeServiceException;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.utils.CSVOperations;
import com.litmus7.employeemanager.utils.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeManagerController {
	static EmployeeService service= new EmployeeService();
	private static final Logger logger = LogManager.getLogger(EmployeeManagerController.class);


	public static Response writeToDB(String filename) {
		logger.trace("Entering writeToDB");
		if (!Validator.isValidCSVFileName(filename)) {
			return new Response(400, "invalid filename",100,null);
		}

        
        int statuscode=ApplicationStatusCodes.SUCCESS;
        String errormsg = null;
        
        boolean isWritten =true;

        List<String[]> data = CSVOperations.readFromCSV(filename);
        try {

        isWritten=service.writeDataToDB(data);
        }catch(EmployeeServiceException e) {
        	return new Response(400, e.getMessage(),e.getErrorCode(),null);
        	
        }

        if(!isWritten) {
        	statuscode=400;
        	errormsg="Error Occured";
        }
        
        return new Response(statuscode, errormsg,100,null); 
        
    }

	public static Response<List<Employee>> getAllEmployees() {
		logger.trace("Entering getAllEmployees");
		// TODO Auto-generated method stub
		List<Employee> employeeList=new ArrayList<>();
		try {
			employeeList=EmployeeService.readAllFromDb();
		} catch (EmployeeServiceException e) {
			// TODO Auto-generated catch block
			return new Response(400, e.getMessage(),e.getErrorCode(),null);
		}
		if (employeeList == null) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "Failed to fetch data",100, null);
	    }
		Response<List<Employee>> obj = new Response<>(ApplicationStatusCodes.SUCCESS,"data Fetched Succesfully",100,employeeList);
		return obj;
	}
	
	public Response<Employee> getEmployeeById(int employeeId) {
		logger.trace("Entering getEmployeeById");
	    if (employeeId <= 0) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "invalid employee data",100,null);
	    }

	    try {
	        Employee employee = service.getEmployeeById(employeeId);
	        if (employee == null) {
	            return new Response<>(ApplicationStatusCodes.FAILURE, "employee not found",100,null);
	        }
	        return new Response<>(ApplicationStatusCodes.SUCCESS, "data fetch succesful",100, employee);
	    } catch (EmployeeServiceException e) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "service failure",100,null);
	    }
	}
	
	public Response<Boolean> updateEmployee(Employee employee) {
		logger.trace("Entering updateEmployee");
	    try {
	        boolean updated = service.updateEmployee(employee);
	        if (updated) {
	            return new Response<>(ApplicationStatusCodes.SUCCESS, "employee update sucess",100, true);
	        } else {
	            return new Response<>(ApplicationStatusCodes.FAILURE, "employee not found",100, null);
	        }
	    } catch (EmployeeServiceException e) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "service error",100,null);
	    }
	}

	public Response<Boolean> deleteEmployeeById(int employeeId) {
		logger.trace("Entering deleteEmployeeById");
	    if (employeeId <= 0) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "invalid employee id",100,null);
	    }

	    Response<Employee> employeeResponse = getEmployeeById(employeeId);
	    if (employeeResponse.getStatuscode() != ApplicationStatusCodes.SUCCESS) {
	        return new Response<>(ApplicationStatusCodes.FAILURE,"employee not found",100,null);
	    }

	    try {
	        boolean deleted = service.deleteEmployeeById(employeeId);
	        if (deleted) {
	            return new Response<>(ApplicationStatusCodes.SUCCESS, "employee delete sucess",100, true);
	        } else {
	            return new Response<>(ApplicationStatusCodes.FAILURE, "employee delete failed",100,null);
	        }
	    } catch (EmployeeServiceException e) {
	        return new Response<>(ApplicationStatusCodes.FAILURE,"service error",100,null);
	    }
	}

	public Response<Boolean> addEmployee(Employee employee) {
		logger.trace("Entering addEmployee");
	    if (employee == null || employee.getEmployeeId() <= 0) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "invalid employee",100, null);
	    }

	    try {
	        boolean added = service.addEmployee(employee);
	        if (added) {
	            return new Response<>(ApplicationStatusCodes.SUCCESS, "employee add sucess",100, true);
	        } else {
	            return new Response<>(ApplicationStatusCodes.FAILURE, "employee add failed",100, false);
	        }
	    } catch (EmployeeServiceException e) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "service error",100, false);
	    }
	}
	
	public Response<Integer> addEmployeesInBatch(List<Employee> employeeList) {
        logger.trace("Entering addEmployeesInBatch");

        if (employeeList == null || employeeList.isEmpty()) {
            logger.warn("Empty or null employee list provided for batch addition");
            return new Response<>(ApplicationStatusCodes.FAILURE, "empty employee list",100,null);
        }

        try {
            int[] result = service.addEmployeesInBatch(employeeList);
            int addedCount = Arrays.stream(result).sum();
            

            if (addedCount == employeeList.size()) {
                return new Response<>(ApplicationStatusCodes.SUCCESS,"batch insertion successful",100, addedCount);
                }
             else {
                return new Response<>(ApplicationStatusCodes.FAILURE, "batch insertion failed",100, null);
            }
        } catch (EmployeeServiceException e) {
            logger.error("Error during batch employee addition", e);
            return new Response<>(ApplicationStatusCodes.FAILURE, "batch insertion failed: " + e.getMessage(),e.getErrorCode(),null);
        } 
    }

    public Response<Boolean> transferEmployeesToDepartment(List<Integer> employeeIds, String newDepartment) {
        logger.trace("Entering transferEmployeesToDepartment");

        if (employeeIds == null || employeeIds.isEmpty() || newDepartment == null || newDepartment.isBlank()) {
            logger.warn("Invalid input for employee transfer: ids={}, newDept={}", employeeIds, newDepartment);
            return new Response<>(ApplicationStatusCodes.FAILURE, "invalid transfer input",100, false);
        }

        try {
            int updatedCount = service.transferEmployeesToDepartment(employeeIds, newDepartment);
            return new Response<>(ApplicationStatusCodes.SUCCESS,
                    updatedCount + " employees transferred to " + newDepartment + " department.",100, true);
        } catch (EmployeeServiceException e) {
            logger.error("Error transferring employees to department {}", newDepartment, e);
            return new Response<>(ApplicationStatusCodes.FAILURE, "Transfer failed: " + e.getMessage(),100, false);
        }
    }

}

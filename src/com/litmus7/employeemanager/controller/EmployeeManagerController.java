
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
		if (!Validator.isValidCSVFileName(filename)) {
			return new Response(400, "invalid filename",null);
		}

        
        int statuscode=ApplicationStatusCodes.SUCCESS;
        String errormsg = null;
        
        boolean isWritten =true;

        List<String[]> data = CSVOperations.readFromCSV(filename);
        try {

        isWritten=service.writeDataToDB(data);
        }catch(EmployeeServiceException e) {
        	return new Response(400, e.getMessage(),null);
        	
        }

        if(!isWritten) {
        	statuscode=400;
        	errormsg="Error Occured";
        }
        
        return new Response(statuscode, errormsg,null); 
        
    }

	public static Response<List<Employee>> getAllEmployees() {
		// TODO Auto-generated method stub
		List<Employee> employeeList=new ArrayList<>();
		try {
			employeeList=EmployeeService.readAllFromDb();
		} catch (EmployeeServiceException e) {
			// TODO Auto-generated catch block
			return new Response(400, e.getMessage(),null);
		}
		if (employeeList == null) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "Failed to fetch data", null);
	    }
		Response<List<Employee>> obj = new Response<>(ApplicationStatusCodes.SUCCESS,"data Fetched Succesfully",employeeList);
		return obj;
	}
	
	public Response<Employee> getEmployeeById(int employeeId) {
	    if (employeeId <= 0) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "invalid employee data",null);
	    }

	    try {
	        Employee employee = service.getEmployeeById(employeeId);
	        if (employee == null) {
	            return new Response<>(ApplicationStatusCodes.FAILURE, "employee not found",null);
	        }
	        return new Response<>(ApplicationStatusCodes.SUCCESS, "data fetch succesful", employee);
	    } catch (EmployeeServiceException e) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "service failure",null);
	    }
	}
	
	public Response<Boolean> updateEmployee(Employee employee) {
	    try {
	        boolean updated = service.updateEmployee(employee);
	        if (updated) {
	            return new Response<>(ApplicationStatusCodes.SUCCESS, "employee update sucess", true);
	        } else {
	            return new Response<>(ApplicationStatusCodes.FAILURE, "employee not found", null);
	        }
	    } catch (EmployeeServiceException e) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "service error",null);
	    }
	}

	public Response<Boolean> deleteEmployeeById(int employeeId) {
	    if (employeeId <= 0) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "invalid employee id",null);
	    }

	    Response<Employee> employeeResponse = getEmployeeById(employeeId);
	    if (employeeResponse.getStatuscode() != ApplicationStatusCodes.SUCCESS) {
	        return new Response<>(ApplicationStatusCodes.FAILURE,"employee not found",null);
	    }

	    try {
	        boolean deleted = service.deleteEmployeeById(employeeId);
	        if (deleted) {
	            return new Response<>(ApplicationStatusCodes.SUCCESS, "employee delete sucess", true);
	        } else {
	            return new Response<>(ApplicationStatusCodes.FAILURE, "employee delete failed",null);
	        }
	    } catch (EmployeeServiceException e) {
	        return new Response<>(ApplicationStatusCodes.FAILURE,"service error",null);
	    }
	}

	public Response<Boolean> addEmployee(Employee employee) {
	    if (employee == null || employee.getEmployeeId() <= 0) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "invalid employee", null);
	    }

	    try {
	        boolean added = service.addEmployee(employee);
	        if (added) {
	            return new Response<>(ApplicationStatusCodes.SUCCESS, "employee add sucess", true);
	        } else {
	            return new Response<>(ApplicationStatusCodes.FAILURE, "employee add failed", false);
	        }
	    } catch (EmployeeServiceException e) {
	        return new Response<>(ApplicationStatusCodes.FAILURE, "service error", false);
	    }
	}
	
	public Response<Integer> addEmployeesInBatch(List<Employee> employeeList) {
        logger.trace("Entering addEmployeesInBatch(batchSize={})", 
                     employeeList != null ? employeeList.size() : "null");

        if (employeeList == null || employeeList.isEmpty()) {
            logger.warn("Empty or null employee list provided for batch addition");
            return new Response<>(ApplicationStatusCodes.FAILURE, "empty employee list",null);
        }

        try {
            int[] result = service.addEmployeesInBatch(employeeList);
            int addedCount = Arrays.stream(result).sum();
            logger.info("Batch addition result: {}/{} employees added", addedCount, employeeList.size());

            if (addedCount == employeeList.size()) {
                return new Response<>(ApplicationStatusCodes.SUCCESS,"batch insertion successful", addedCount);
                }
             else {
                return new Response<>(ApplicationStatusCodes.FAILURE, "batch insertion failed", null);
            }
        } catch (EmployeeServiceException e) {
            logger.error("Error during batch employee addition", e);
            return new Response<>(ApplicationStatusCodes.FAILURE, "batch insertion failed: " + e.getMessage(),null);
        } finally {
            logger.trace("Exiting addEmployeesInBatch");
        }
    }

    public Response<Boolean> transferEmployeesToDepartment(List<Integer> employeeIds, String newDepartment) {
        logger.trace("Entering transferEmployeesToDepartment(ids={}, newDept={})", employeeIds, newDepartment);

        if (employeeIds == null || employeeIds.isEmpty() || newDepartment == null || newDepartment.isBlank()) {
            logger.warn("Invalid input for employee transfer: ids={}, newDept={}", employeeIds, newDepartment);
            return new Response<>(ApplicationStatusCodes.FAILURE, "invalid transfer input", false);
        }

        try {
            int updatedCount = service.transferEmployeesToDepartment(employeeIds, newDepartment);
            logger.info("{} employees transferred to {} department", updatedCount, newDepartment);
            return new Response<>(ApplicationStatusCodes.SUCCESS,
                    updatedCount + " employees transferred to " + newDepartment + " department.", true);
        } catch (EmployeeServiceException e) {
            logger.error("Error transferring employees to department {}", newDepartment, e);
            return new Response<>(ApplicationStatusCodes.FAILURE, "Transfer failed: " + e.getMessage(), false);
        } finally {
            logger.trace("Exiting transferEmployeesToDepartment");
        }
    }

}

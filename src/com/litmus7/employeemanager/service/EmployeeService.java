package com.litmus7.employeemanager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.dao.EmployeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exception.EmployeeDaoException;
import com.litmus7.employeemanager.exception.EmployeeServiceException;
import com.litmus7.employeemanager.utils.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeService {
	static EmployeeDAO dao = new EmployeeDAO();
	
	private static final Logger logger = LogManager.getLogger(EmployeeService.class);


	public boolean writeDataToDB(List<String[]> data) throws EmployeeServiceException {
		for (String[] row : data) {

			Employee employee = new Employee();
			try {
				employee.setEmployeeId(Integer.parseInt(row[0]));
				employee.setFirstName(row[1]);
				employee.setLastName(row[2]);
				employee.setEmail(row[3]);
				employee.setPhone(row[4]);
				employee.setDepartment(row[5]);
				employee.setSalary(Double.parseDouble(row[6]));

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				employee.setJoinDate(sdf.parse(row[7]));
				if (!Validator.validateEmployee(employee)) return false;
				dao.storeInDB(employee);
			} catch (EmployeeDaoException e) {
				throw new EmployeeServiceException("Service failed while saving employee", e);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return true;
	}

	public static List<Employee> readAllFromDb() throws EmployeeServiceException{
		List<Employee> employeeList = new ArrayList<>();
		try {
		employeeList = EmployeeDAO.selectAllEmployees();
		}catch(EmployeeDaoException e) {
			throw new EmployeeServiceException("Failed to fetch employee details",e);
		}
		return employeeList;
	}
	
	 public Employee getEmployeeById(int employeeId) throws EmployeeServiceException {
	        try {
	            return dao.getEmployeeById(employeeId);
	        } catch (EmployeeDaoException e) {
	            throw new EmployeeServiceException("Failed to fetch employee by ID: " + employeeId, e);
	        }
	    }
	    
	    public boolean updateEmployee(Employee employee) throws EmployeeServiceException {
	        try {
	            if (!Validator.validateEmployee(employee)) {
	                return false;
	            }
	            return dao.updateEmployee(employee);
	        } catch (EmployeeDaoException e) {
	            throw new EmployeeServiceException("Failed to update employee", e);
	        }
	    }
	    
	    public boolean deleteEmployeeById(int employeeId) throws EmployeeServiceException {
	        try {
	            return dao.deleteEmployeeById(employeeId);
	        } catch (EmployeeDaoException e) {
	            throw new EmployeeServiceException("Failed to delete employee", e);
	        }
	    }

	    public boolean addEmployee(Employee employee) throws EmployeeServiceException {
	        try {
	        	if (!Validator.validateEmployee(employee)) {
	        	    return false;
	        	}

	            return dao.addEmployee(employee);
	        } catch (EmployeeDaoException e) {
	            throw new EmployeeServiceException("Service error: Failed to add employee", e);
	        }
	    }
	    
	    public int[] addEmployeesInBatch(List<Employee> employeeList) throws EmployeeServiceException {
	        logger.trace("Entering addEmployeesInBatch()");
	        List<Employee> validEmployees = new ArrayList<>();

	        for (int i = 0; i < employeeList.size(); i++) {
	            Employee employee = employeeList.get(i);

	            if (!Validator.validateEmployee(employee)) {
	                logger.warn("Batch Row {}: Validation failed for employee ID {}", (i + 1), employee.getEmployeeId());
	                continue;
	            }

	            try {
	                if (dao.getEmployeeById(employee.getEmployeeId()) != null || validEmployees.contains(employee)) {
	                    logger.warn("Batch Row {}: Employee with ID {} already exists", (i + 1), employee.getEmployeeId());
	                    continue;
	                }
	            } catch (EmployeeDaoException e) {
	                logger.error("Batch Row {}: Failed to check existing employee in DB", (i + 1), e);
	                throw new EmployeeServiceException("Error checking for existing employee ID: " + employee.getEmployeeId(), e);
	            }

	            validEmployees.add(employee);
	        }

	        try {
	            int[] result = dao.addEmployeesInBatch(validEmployees);
	            logger.trace("Exiting addEmployeesInBatch()");
	            return result;
	        } catch (EmployeeDaoException e) {
	            logger.trace("Exiting addEmployeesInBatch() with exception");
	            throw new EmployeeServiceException("Failed to add employees in batch" + e.getMessage(), e);
	        }
	    }

	    public int transferEmployeesToDepartment(List<Integer> employeeIds, String newDepartment) throws EmployeeServiceException {
	        logger.trace("Entering transferEmployeesToDepartment()");
	        if (employeeIds == null || employeeIds.isEmpty()) {
	            throw new EmployeeServiceException("Employee ID list is empty.");
	        }
	        if (newDepartment == null || newDepartment.isBlank()) {
	            throw new EmployeeServiceException("New department name is invalid.");
	        }
	        try {
	            int transferred = dao.transferEmployeesToDepartment(employeeIds, newDepartment);
	            logger.trace("Exiting transferEmployeesToDepartment()");
	            return transferred;
	        } catch (EmployeeDaoException e) {
	            logger.trace("Exiting transferEmployeesToDepartment() with exception");
	            throw new EmployeeServiceException("Error During Department Transfers" + e.getMessage(), e);
	        }
	    }

}

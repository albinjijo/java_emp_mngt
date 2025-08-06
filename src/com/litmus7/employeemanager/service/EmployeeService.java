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

public class EmployeeService {
	static EmployeeDAO dao = new EmployeeDAO();

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

}

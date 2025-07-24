package com.litmus7.employeemanager.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.dao.EmployeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.utils.Validator;

public class EmployeeService {
	static EmployeeDAO dao = new EmployeeDAO();

	public boolean writeDataToDB(List<String[]> data) {
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
			} catch (Exception e) {
				return false;

			}

		}
		return true;
	}

	public static List<Employee> readAllFromDb() {
		List<Employee> employeeList = new ArrayList<>();
		employeeList = EmployeeDAO.selectAllEmployees();
		return employeeList;
	}

}

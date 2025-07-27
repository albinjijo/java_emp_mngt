package com.litmus7.employeemanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.controller.EmployeeManagerController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.utils.DBConfig;
import com.litmus7.employeemanager.constants.SQLConstants;


public class EmployeeDAO {
	public boolean storeInDB(Employee employee) {
		try (Connection conn = DBConfig.getDBConnection();) {
			PreparedStatement stmt = conn.prepareStatement(SQLConstants.INSERT_EMPLOYEE);
			stmt.setInt(1, employee.getEmployeeId());
			stmt.setString(2, employee.getFirstName());
			stmt.setString(3, employee.getLastName());
			stmt.setString(4, employee.getEmail());
			stmt.setString(5, employee.getPhone());
			stmt.setString(6, employee.getDepartment());
			stmt.setDouble(7, employee.getSalary());

			// Convert java.util.Date to java.sql.Date
			java.sql.Date sqlDate = new java.sql.Date(employee.getJoinDate().getTime());
			stmt.setDate(8, sqlDate);
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	public static List<Employee> selectAllEmployees() {
		// TODO Auto-generated method stub
		List<Employee> employeeList = new ArrayList<>();

		try (Connection conn = DBConfig.getDBConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLConstants.SELECT_ALL_EMPLOYEES);
				ResultSet result = stmt.executeQuery();) {
			while (result.next()) {
				Employee emp = new Employee();
				emp.setEmployeeId(result.getInt("emp_id"));
				emp.setFirstName(result.getString("first_name"));
				emp.setLastName(result.getString("last_name"));
				emp.setEmail(result.getString("email"));
				emp.setPhone(result.getString("phone"));
				emp.setDepartment(result.getString("department"));
				emp.setSalary(result.getDouble("salary"));
				emp.setJoinDate(result.getDate("join_date"));
				employeeList.add(emp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeeList;

	}

}

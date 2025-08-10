package com.litmus7.employeemanager.constants;

public class SQLConstants {
	public static final String INSERT_EMPLOYEE =
	"INSERT INTO employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
	"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
//	public static final String CHECK_EMPLOYEE_EXISTS = "SELECT emp_id FROM employee WHERE emp_id = ?";

	
	public static final String SELECT_ALL_EMPLOYEES="SELECT * FROM employee";
	

	public static final String SELECT_EMPLOYEE_BY_ID ="SELECT * FROM employee WHERE emp_id = ?";
	
	public static final String UPDATE_EMPLOYEE =
    	    "UPDATE employee SET first_name = ?, last_name = ?, email = ?, phone = ?, department = ?, salary = ?, join_date = ? WHERE emp_id = ?";
    
    public static final String DELETE_EMPLOYEE_BY_ID =
            "DELETE FROM employee WHERE emp_id = ?";
    
    public static final String UPDATE_DEPARTMENT =
    	    "UPDATE employee SET department = ? WHERE emp_id = ?";

}
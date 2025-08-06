package com.litmus7.employeemanager.dto;

import java.util.Date;

public class Employee {
	private int employeeId ;
	private String firstName ;
	private String lastName ;
	private String email ;
	private String phone ;
	private String department ;
	private Double salary ;
	private Date joinDate ;
	
	public Employee() {}
	
	public Employee(int employeeId, String firstName, String lastName, String email, String phone, String department, double salary, Date joinDate) {
	    this.employeeId = employeeId;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.email = email;
	    this.phone = phone;
	    this.department = department;
	    this.salary = salary;
	    this.joinDate = joinDate;
	}
	
	
	
	
	
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	

}

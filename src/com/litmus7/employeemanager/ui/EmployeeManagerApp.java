package com.litmus7.employeemanager.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.litmus7.employeemanager.constants.ApplicationStatusCodes;
import com.litmus7.employeemanager.controller.EmployeeManagerController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.property.ErrorCodeProperties;


public class EmployeeManagerApp {
    public static void main(String[] args) throws ParseException {
    	
    	Scanner scanner = new Scanner(System.in);
    	String filename = "C:\\Users\\ALBIN JIJO\\eclipse-workspace\\EmployeeManager\\src\\com\\litmus7\\employeemanager\\resources\\Employee.csv";
        EmployeeManagerController employeecontroller = new EmployeeManagerController();
        
        Response response = employeecontroller.writeToDB(filename);
        if (response.getStatuscode() == 200) {
            System.out.println("All records were inserted.");
        }  else {
            System.out.println("Message: " + response.getErrormsg()+ ErrorCodeProperties.getErrorCodeMeaning(Integer.toString(response.getErrorCode())));
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
        
      //get employee by id
        System.out.print("Enter Employee ID to fetch: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); 
        Response<Employee> employeeResponse = employeecontroller.getEmployeeById(employeeId);

        if (employeeResponse.getStatuscode() == 200) {
            System.out.println("Employee details: " + employeeResponse.getData());
        } else {
            System.out.println("Error: " + employeeResponse.getErrormsg());
        }

        // Update employee
        System.out.print("Do you want to update any employee details? (yes/no): ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter Employee ID to update: ");
            int updateId = scanner.nextInt();
            scanner.nextLine(); // consume newline

            Response<Employee> existingEmpResponse = employeecontroller.getEmployeeById(updateId);

            if (existingEmpResponse.getStatuscode() == 200) {
                Employee empToUpdate = existingEmpResponse.getData();

                System.out.print("Enter new First Name (" + empToUpdate.getFirstName() + "): ");
                String firstName = scanner.nextLine();
                if (!firstName.isBlank()) empToUpdate.setFirstName(firstName);

                System.out.print("Enter new Last Name (" + empToUpdate.getLastName() + "): ");
                String lastName = scanner.nextLine();
                if (!lastName.isBlank()) empToUpdate.setLastName(lastName);

                System.out.print("Enter new Email (" + empToUpdate.getEmail() + "): ");
                String email = scanner.nextLine();
                if (!email.isBlank()) empToUpdate.setEmail(email);

                System.out.print("Enter new Phone (" + empToUpdate.getPhone() + "): ");
                String phone = scanner.nextLine();
                if (!phone.isBlank()) empToUpdate.setPhone(phone);

                System.out.print("Enter new Department (" + empToUpdate.getDepartment() + "): ");
                String dept = scanner.nextLine();
                if (!dept.isBlank()) empToUpdate.setDepartment(dept);

                System.out.print("Enter new Salary (" + empToUpdate.getSalary() + "): ");
                String salaryStr = scanner.nextLine();
                if (!salaryStr.isBlank()) empToUpdate.setSalary(Double.parseDouble(salaryStr));

                System.out.print("Enter new Join Date (" + empToUpdate.getJoinDate() + ") [yyyy-mm-dd]: ");
                String joinDate = scanner.nextLine();
                if (!joinDate.isBlank()) {
                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    				try {
						empToUpdate.setJoinDate(sdf.parse(joinDate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	
                }
                

                Response<Boolean> updateResponse = employeecontroller.updateEmployee(empToUpdate);
                if (updateResponse.getStatuscode() == 200 && updateResponse.getData()) {
                    System.out.println("Employee updated successfully!");
                } else {
                    System.out.println("Update failed: " + updateResponse.getErrormsg());
                }

            } else {
                System.out.println("Employee not found: " + existingEmpResponse.getErrormsg());
            }
        }
        
        //delete employee by id
        System.out.print("Enter Employee ID to delete: ");
        int employeeId1 = scanner.nextInt();
        scanner.nextLine(); 

        Response<Boolean> employeeDeleteResponse = employeecontroller.deleteEmployeeById(employeeId1);

        if (employeeDeleteResponse.getStatuscode() == 200) {
            System.out.println("Success: " + employeeDeleteResponse.getErrormsg());
        } else {
            System.out.println("Error: " + employeeDeleteResponse.getErrormsg());
        }
        
        // Add new employee
        System.out.print("Do you want to add a new employee? (yes/no): ");
        String addChoice = scanner.nextLine();
        if (addChoice.equalsIgnoreCase("yes")) {
            try {
                System.out.print("Enter Employee ID: ");
                int id = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter First Name: ");
                String firstName = scanner.nextLine();

                System.out.print("Enter Last Name: ");
                String lastName = scanner.nextLine();

                System.out.print("Enter Email: ");
                String email = scanner.nextLine();

                System.out.print("Enter Phone: ");
                String phone = scanner.nextLine();

                System.out.print("Enter Department: ");
                String department = scanner.nextLine();

                System.out.print("Enter Salary: ");
                double salary = Double.parseDouble(scanner.nextLine());

                System.out.print("Enter Join Date (yyyy-mm-dd): ");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
                String date = scanner.nextLine();
                
                java.util.Date joinDate = sdf.parse(date);
                
                

                Employee newEmp = new Employee(id, firstName, lastName, email, phone, department, salary, joinDate);
                Response<Boolean> addResponse = employeecontroller.addEmployee(newEmp);

                if (addResponse.getStatuscode() == 200 && addResponse.getData()) {
                    System.out.println("Employee added successfully.");
                } else {
                    System.out.println("Failed to add employee: " + addResponse.getErrormsg());
                }

            } catch (Exception e) {
                System.out.println("Error while adding employee: " + e.getMessage());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            
            List<Employee> employeeList = Arrays.asList(
            	    new Employee(111, "virat", "kohli", "virat@example.com", "111111111", "IT", 30000.0, sdf.parse("2025-11-23")),
            	    new Employee(112, "rohit", "sharma", "rohit@example.com", "222222222", "Finance", 71000.0, sdf.parse("2021-02-06"))
            	);

            	Response<Integer> batchResponse = employeecontroller.addEmployeesInBatch(employeeList);

            	
            	if (batchResponse.getStatuscode() == ApplicationStatusCodes.SUCCESS) {
            	    System.out.println("All employees added successfully.");
            	}  else {
            	    System.out.println(batchResponse.getErrormsg());
            	}
            	List<Integer> empIdsToTransfer = Arrays.asList(102, 105); 
            	String newDept = "HR";

            	Response<Boolean> transferResponse = employeecontroller.transferEmployeesToDepartment(empIdsToTransfer, newDept);

            	if (transferResponse.getStatuscode() == ApplicationStatusCodes.SUCCESS) {
            	    System.out.println("Tranferring employees to new department is Successfully completed");
            	} else {
            	    System.out.println("Failed Tranferring employees to new department");
            	}

            	System.out.println("Message: " + transferResponse.getErrormsg());

    } 
}}
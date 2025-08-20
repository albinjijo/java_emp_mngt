package com.litmus7.employeemanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.controller.EmployeeManagerController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exception.EmployeeDaoException;
import com.litmus7.employeemanager.utils.DBConfig;
import com.litmus7.employeemanager.constants.SQLConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeDAO {
	
	private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

	
	public boolean storeInDB(Employee employee) throws EmployeeDaoException{
		logger.trace("Entering storeInDB.");
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
			throw new EmployeeDaoException("Error inserting employee to DB", e,100);
			

		}
	}

	public static List<Employee> selectAllEmployees() throws EmployeeDaoException {
		logger.trace("Entering selectAllEmployees.");
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
			throw new EmployeeDaoException("Error in retrieving employee details from DB", e,100);
		}
		return employeeList;

	}
	
	public Employee getEmployeeById(int employeeId) throws EmployeeDaoException {
		logger.trace("Entering getEmployeeById.");
        try (Connection conn = DBConfig.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(SQLConstants.SELECT_EMPLOYEE_BY_ID)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                 
                 Employee emp = new Employee();
 				emp.setEmployeeId(rs.getInt("emp_id"));
 				emp.setFirstName(rs.getString("first_name"));
 				emp.setLastName(rs.getString("last_name"));
 				emp.setEmail(rs.getString("email"));
 				emp.setPhone(rs.getString("phone"));
 				emp.setDepartment(rs.getString("department"));
 				emp.setSalary(rs.getDouble("salary"));
 				emp.setJoinDate(rs.getDate("join_date"));
                return emp;
            }
        } catch (SQLException e) {
        	throw new EmployeeDaoException("Error in retrieving employee details from DB", e,100);
        }
        return null;
    }

    public boolean updateEmployee(Employee employee) throws EmployeeDaoException {
    	logger.trace("Entering updateEmployee.");
        try (Connection conn = DBConfig.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(SQLConstants.UPDATE_EMPLOYEE)) {

            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPhone());
            stmt.setString(5, employee.getDepartment());
            stmt.setDouble(6, employee.getSalary());
            java.sql.Date sqlDate = new java.sql.Date(employee.getJoinDate().getTime());
			stmt.setDate(7, sqlDate);
            stmt.setInt(8, employee.getEmployeeId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new EmployeeDaoException("Error updating employee", e,100);
        }
    }

    public boolean deleteEmployeeById(int employeeId) throws EmployeeDaoException {
    	logger.trace("Entering deleteEmployeeById.");
        try (Connection conn = DBConfig.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(SQLConstants.DELETE_EMPLOYEE_BY_ID)) {

            stmt.setInt(1, employeeId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
  
        } catch (SQLException e) {
            throw new EmployeeDaoException("Error deleting employee with ID " + employeeId + ": " + e.getMessage(), e,100);
        }

     }
    

    public boolean addEmployee(Employee employee) throws EmployeeDaoException {
    	logger.trace("Entering addEmployee.");
        try (Connection connection = DBConfig.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.INSERT_EMPLOYEE)) {

            preparedStatement.setInt(1, employee.getEmployeeId());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setString(5, employee.getPhone());
            preparedStatement.setString(6, employee.getDepartment());
            preparedStatement.setDouble(7, employee.getSalary());
            java.sql.Date sqlDate = new java.sql.Date(employee.getJoinDate().getTime());
			preparedStatement.setDate(8, sqlDate);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            throw new EmployeeDaoException("Error inserting employee with ID " + employee.getEmployeeId() + ": " + e.getMessage(), e,100);
        }
    }
    
    public int[] addEmployeesInBatch(List<Employee> employeeList) throws EmployeeDaoException {
        logger.trace("Entering addEmployeesInBatch.");
        try (Connection conn = DBConfig.getDBConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQLConstants.INSERT_EMPLOYEE)) {

            for (Employee employee : employeeList) {
                preparedStatement.setInt(1, employee.getEmployeeId());
                preparedStatement.setString(2, employee.getFirstName());
                preparedStatement.setString(3, employee.getLastName());
                preparedStatement.setString(4, employee.getEmail());
                preparedStatement.setString(5, employee.getPhone());
                preparedStatement.setString(6, employee.getDepartment());
                preparedStatement.setDouble(7, employee.getSalary());
                java.sql.Date sqlDate = new java.sql.Date(employee.getJoinDate().getTime());
    			preparedStatement.setDate(8, sqlDate);
                preparedStatement.addBatch();
            }

            int[] result = preparedStatement.executeBatch();
            logger.debug("addEmployeesInBatch executed");

            return result;
        } catch (SQLException e) {
            logger.error("Error inserting employees in batch", e);
            throw new EmployeeDaoException("Error inserting employees :" + e.getMessage(), e,100);
        } finally {
            logger.trace("Exiting addEmployeesInBatch");
        }
    }

    public int transferEmployeesToDepartment(List<Integer> employeeIds, String newDepartment) throws EmployeeDaoException {
        logger.trace("Entering transferEmployeesToDepartment.");
        int updatedCount = 0;
        try (Connection conn = DBConfig.getDBConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(SQLConstants.UPDATE_DEPARTMENT)) {
                for (Integer employeeId : employeeIds) {
                    pstmt.setString(1, newDepartment);
                    pstmt.setInt(2, employeeId);
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected == 0) {
                        logger.warn("No employee found for ID {}", employeeId);
                        throw new SQLException("Employee ID " + employeeId + " not found");
                    }
                    updatedCount++;
                }
                conn.commit();
                logger.debug("transferEmployeesToDepartment committed");
            } catch (SQLException e) {
                conn.rollback();
                logger.error("Transaction failed for transferEmployeesToDepartment", e);
                throw new EmployeeDaoException("Transaction failed. Rolled back. " + e.getMessage(),100);
            } finally {
                conn.setAutoCommit(true);
            }
            return updatedCount;
        } catch (SQLException e) {
            logger.error("Database error in transferEmployeesToDepartment", e);
            throw new EmployeeDaoException("DB Error: " + e.getMessage(),100);
        } finally {
            logger.trace("Exiting transferEmployeesToDepartment");
        }
    }

}

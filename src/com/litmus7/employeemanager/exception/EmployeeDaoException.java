package com.litmus7.employeemanager.exception;

public class EmployeeDaoException extends Exception {
	private int errorcode;
    public EmployeeDaoException(String message, int errorcode) {
        super(message);
        this.errorcode = errorcode;
    }

    public EmployeeDaoException(String message, Throwable cause,int errorcode) {
        super(message, cause);
        this.errorcode = errorcode;
    }
    
    public int getErrorCode() {
        return errorcode;
    }
}
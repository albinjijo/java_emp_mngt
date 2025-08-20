package com.litmus7.employeemanager.exception;

public class EmployeeServiceException extends Exception {
	private int errorcode;
    public EmployeeServiceException(String message, Throwable cause,int errorcode) {
        super(message, cause);
        this.errorcode = errorcode;
    }
    
    public EmployeeServiceException(String message, int errorcode) {
		super(message);
		this.errorcode = errorcode;
	}
    public int getErrorCode() {
        return errorcode;
    }
}
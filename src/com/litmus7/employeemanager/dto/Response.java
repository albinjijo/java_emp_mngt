package com.litmus7.employeemanager.dto;


public class Response <T>{
	private int statuscode;
    private String errormsg;
    private T data;
    private int errorcode;

    // Constructors
   

    public Response(int statuscode, String errormsg, int errorcode, T data) {
        this.statuscode = statuscode;
        this.errormsg = errormsg;
        this.data = data;
        this.errorcode = errorcode;
    }
    
    public int getStatuscode() {
        return statuscode;
    }


    public String getErrormsg() {
        return errormsg;
    }
    
    public T getData() {
        return data;
    }
    
    public int getErrorCode() {
        return errorcode;
    }

}

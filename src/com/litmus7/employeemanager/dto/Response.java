package com.litmus7.employeemanager.dto;


public class Response <T>{
	private int statuscode;
    private String errormsg;
    private T data;

    // Constructors
   

    public Response(int statuscode, String errormsg, T data) {
        this.statuscode = statuscode;
        this.errormsg = errormsg;
        this.data = data;
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

}

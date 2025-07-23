package com.litmus;

public class Response {
	private int statuscode;
    private String errormsg;
    

    // Constructors

    public Response(int statuscode, String errormsg) {
        this.statuscode = statuscode;
        this.errormsg = errormsg;
    }
    
    public int getStatuscode() {
        return statuscode;
    }


    public String getErrormsg() {
        return errormsg;
    }

}

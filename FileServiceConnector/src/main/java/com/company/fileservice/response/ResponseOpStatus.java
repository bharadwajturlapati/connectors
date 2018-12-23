package com.company.fileservice.response;

public enum ResponseOpStatus {
	
	GENERIC_EXCEPTION(9001, "Exception occurred"),
	INVALID_INPUT(406, "Invalid Input Provided"),
	SUCCESS(200, "successful");
	
	
	private int status;
	private String message;
	
	ResponseOpStatus(int statuscode, String message){
		this.setStatus(statuscode);
		this.setMessage(message);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

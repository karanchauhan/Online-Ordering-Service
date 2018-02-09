package com.zappos.onlineordering.model;

public class StatusResponse {

	String status;

	String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StatusResponse(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public StatusResponse() {
	}

}

package com.zappos.onlineordering.model;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception {

	private static final long serialVersionUID = 1L;

	private HttpStatus status;

	private String message;

	public ApiException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	ApiException(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
	}

	ApiException(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
	}

	public ApiException() {
		super();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

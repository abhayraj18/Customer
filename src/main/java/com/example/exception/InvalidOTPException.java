package com.example.exception;

/**
 * Exception class to indicate that OTP is invalid.
 * 
 * @author abhay.jain
 *
 */
public class InvalidOTPException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public InvalidOTPException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

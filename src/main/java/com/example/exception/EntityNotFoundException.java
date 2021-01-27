package com.example.exception;

/**
 * Exception class to indicate that an entity does not exist.
 * 
 * @author abhay.jain
 *
 */
public class EntityNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public EntityNotFoundException(String message) {
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

package com.example.exception;

/**
 * Exception class to indicate there are validation errors.
 * 
 * @author abhay.jain
 *
 */
public class ValidationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object errors;

	public ValidationException(Object errors) {
		super();
		this.errors = errors;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}

}
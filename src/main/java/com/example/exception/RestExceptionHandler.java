package com.example.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.util.ExceptionMessageConstants;

/**
 * Global exception handler class which can handle various types of exceptions
 * and send appropriate error response.
 * 
 * @author abhay.jain
 *
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(HttpServletRequest request, Exception ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleValidationException(HttpServletRequest request, ValidationException ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityExistsException.class)
	public ResponseEntity<Object> handleEntityExistsException(HttpServletRequest request, Exception ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Object> handleAuthenticationException(HttpServletRequest request, Exception ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(HttpServletRequest request, Exception ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InvalidOTPException.class)
	public ResponseEntity<Object> handleInvalidOTPException(HttpServletRequest request, Exception ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(HttpServletRequest request, Exception ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(HttpServletRequest request, Exception ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(HttpServletRequest request, Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(ExceptionMessageConstants.GENERIC_EXCEPTION_MESSAGE,
				HttpStatus.BAD_REQUEST);
	}

}

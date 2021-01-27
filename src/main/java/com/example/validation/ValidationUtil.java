package com.example.validation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.example.exception.ValidationException;
import com.example.util.EnumConstant.UserType;


public class ValidationUtil {

	/**
	 * Get error messages for each field in a map from Errors
	 * 
	 * @param result
	 * @return
	 */
	public static Map<String, List<String>> getErrorMessages(Errors result) {
		Map<String, List<String>> errorMap = new HashMap<>();
		result.getAllErrors().forEach(error -> {
			if (error instanceof FieldError) {
				getFieldErrors(errorMap, error);
			} else {
				getGlobalErrors(errorMap, error);
			}
		});
		return errorMap;
	}

	/**
	 * Get Global error messages from validation errors.
	 * 
	 * @param errorMap
	 * @param error
	 */
	private static void getGlobalErrors(Map<String, List<String>> errorMap, ObjectError error) {
		List<String> fieldErrors = errorMap.get(error.getCode()) == null ? new ArrayList<>()
				: errorMap.get(error.getCode());
		getValidationMessage(error, fieldErrors);
		errorMap.put(error.getCode(), fieldErrors);
	}

	/**
	 * Get Field error messages from validation errors.
	 * 
	 * @param errorMap
	 * @param error
	 */
	private static void getFieldErrors(Map<String, List<String>> errorMap, ObjectError error) {
		FieldError fieldError = (FieldError) error;
		List<String> errorList = errorMap.get(fieldError.getField()) == null ? new ArrayList<>()
				: errorMap.get(fieldError.getField());
		/*
		 * If the error comes from annotation on the field, get the default message
		 * given in the annotation message
		 * 
		 * If a value is rejected using errors.rejectValue, it will have default message
		 * as null, get the message using error code.
		 */
		if (Objects.nonNull(error.getDefaultMessage())) {
			errorList.add(error.getDefaultMessage());
		} else {
			getValidationMessage(error, errorList);
		}
		errorMap.put(fieldError.getField(), errorList);
	}
	
	/**
	 * Get validation message using error code
	 * 
	 * @param error
	 * @param errorList
	 */
	private static void getValidationMessage(ObjectError error, List<String> errorList) {
		String validationMessage = ValidationMessage.get(error.getCode());
		String message = Objects.nonNull(validationMessage) ? validationMessage : error.getDefaultMessage();
		// Format the message using the arguments passed
		message = MessageFormat.format(message, error.getArguments());
		errorList.add(message);
	}

	/**
	 * Throw @ValidationException if there are any validation errors.
	 * 
	 * @param result
	 * @throws ValidationException
	 */
	public static void checkValidationErrors(Errors result) throws ValidationException {
		if (result.hasErrors()) {
			Map<String, List<String>> errorMap = ValidationUtil.getErrorMessages(result);
			throw new ValidationException(errorMap);
		}
	}

	public static boolean isValidUserType(String userType) {
		return UserType.isCustomer(userType) || UserType.isManager(userType);
	}

}

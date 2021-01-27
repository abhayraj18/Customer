package com.example.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Validatable.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		((Validatable) target).validate(errors);
	}

}

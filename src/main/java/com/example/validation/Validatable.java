package com.example.validation;

import org.springframework.validation.Errors;

public interface Validatable {
	void validate(Errors errors);
}

package com.example.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.example.validation.Validatable;
import com.example.validation.ValidationCode;
import com.example.validation.ValidationUtil;

public class UserForm implements Validatable {

	@NotEmpty(message = "Please enter username")
	@Size(min = 6, max = 50, message = "Username should be minimum {min} characters and maximum {max} characters")
	private String userName;

	@NotEmpty(message = "Please enter phone number")
	@Size(min = 10, max = 10, message = "Phone number should have minimum {min} and maximum {max} digits")
	private String phoneNumber;

	@NotEmpty(message = "Please enter gender")
	@Pattern(regexp = "M|F|O", message = "Please enter valid gender")
	private String gender;

	private String dateOfBirth;

	@NotEmpty(message = "Please send user type")
	private String userType;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.validation.Validatable#validate(org.springframework.validation.
	 * Errors)
	 */
	@Override
	public void validate(Errors errors) {
		if (StringUtils.isNotBlank(getUserType()) && !ValidationUtil.isValidUserType(getUserType())) {
			errors.reject(ValidationCode.INVALID_USER_TYPE);
		}
	}

}

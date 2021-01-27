package com.example.form;

import javax.validation.constraints.NotBlank;

public class LoginForm {

	@NotBlank(message = "Please enter valid username")
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}

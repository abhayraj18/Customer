package com.example.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.example.validation.ValidationCode;

public class EditUserForm extends UserForm {

	@NotNull(message = "{" + ValidationCode.INVALID_ID + "}")
	@Min(value = 1, message = "{" + ValidationCode.INVALID_ID + "}")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

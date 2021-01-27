package com.example.form;

import javax.validation.constraints.NotNull;

public class OTPForm {

	@NotNull(message = "Please enter OTP")
	private Integer otp;

	@NotNull(message = "Please enter customer id")
	private Integer customerId;

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}

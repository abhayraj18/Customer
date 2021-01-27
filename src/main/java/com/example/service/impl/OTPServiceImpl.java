package com.example.service.impl;

import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.InvalidOTPException;
import com.example.service.OTPService;
import com.example.util.OTPGenerator;

@Service
public class OTPServiceImpl implements OTPService {

	@Autowired
	private OTPGenerator otpGenerator;

	@Override
	public Map<Integer, LocalTime> getOTP(String key) {
		return otpGenerator.generateOTP(key);
	}

	@Override
	public boolean isValidOTP(String key, Integer otpNumber) throws InvalidOTPException {
		Integer cacheOTP = otpGenerator.getOTPByKey(key);
		if (Objects.nonNull(cacheOTP) && cacheOTP.equals(otpNumber)) {
			otpGenerator.clearOTPFromCache(key);
			return Boolean.TRUE;
		}
		throw new InvalidOTPException("OTP is invalid");
	}
}
package com.example.service;

import java.time.LocalTime;
import java.util.Map;

import com.example.exception.InvalidOTPException;

public interface OTPService {

	/**
	 * Service to get OTP
	 * 
	 * @param key
	 * @return generated OTP Map
	 */
	Map<Integer, LocalTime> getOTP(String key);

	/**
	 * Service to validate OTP
	 * 
	 * @param key
	 * @param otpNumber
	 * @return true if OTP is valid
	 * @throws InvalidOTPException
	 */
	boolean isValidOTP(String key, Integer otpNumber) throws InvalidOTPException;

}

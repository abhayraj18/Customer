package com.example.util;

import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.validation.Patterns;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {

	private final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public static String convertToJSONString(Object object) {
		if (object instanceof String) {
			return object.toString();
		}
		String result = StringUtils.EMPTY;
		try {
			result = new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isValidId(Integer id) {
		return Objects.nonNull(id) && id > 0;
	}
	
	public static boolean isValidOTP(String otp) {
		return doesValueMatchPattern(otp, Patterns.NUMERIC_PATTERN);
	}

	public static boolean isValidEmail(String email) {
		return doesValueMatchPattern(email, Patterns.EMAIL_PATTERN);
	}
	
	public static boolean isValidName(String name) {
		return doesValueMatchPattern(name, Patterns.ALPHABETIC_NAME_PATTERN);
	}

	public static boolean isValidYear(Integer year) {
		return doesValueMatchPattern(String.valueOf(year), Patterns.YEAR_PATTERN);
	}

	private static boolean doesValueMatchPattern(String value, String patternToMatch) {
		return Pattern.matches(patternToMatch, value);
	}

	private static boolean doesValueContainPattern(String value, String patternToMatch) {
		Pattern pattern = Pattern.compile(patternToMatch);
		if (!pattern.matcher(value).find()) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(doesValueContainPattern("12345", Patterns.YEAR_PATTERN));
		System.out.println(doesValueMatchPattern("12345", Patterns.YEAR_PATTERN));
		System.out.println(doesValueContainPattern("1234", Patterns.YEAR_PATTERN));
		System.out.println(doesValueMatchPattern("1235", Patterns.YEAR_PATTERN));

		System.out.println(isValidYear(0001));
	}

}

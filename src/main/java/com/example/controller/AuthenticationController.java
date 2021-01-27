package com.example.controller;

import java.time.LocalTime;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.security.AuthenticationResponse;
import com.example.config.security.AuthenticationTokenProvider;
import com.example.config.security.UserPrincipal;
import com.example.exception.EntityNotFoundException;
import com.example.exception.InvalidOTPException;
import com.example.exception.ValidationException;
import com.example.form.LoginForm;
import com.example.form.OTPForm;
import com.example.service.OTPService;
import com.example.service.UserService;
import com.example.util.RequestParameter;
import com.example.validation.ValidationUtil;
import com.example.vo.UserVO;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthenticationTokenProvider tokenProvider;

	@Autowired
	private OTPService otpService;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticateUser(@Valid @RequestBody LoginForm loginForm,
			Errors result)
			throws AuthenticationException, ValidationException {
		ValidationUtil.checkValidationErrors(result);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginForm.getUsername(), null));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserPrincipal userDetails = (UserPrincipal) authentication.getDetails();
		String authenticationToken = tokenProvider.generateToken(userDetails.getId());
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(authenticationToken),
				HttpStatus.OK);
	}

	@GetMapping("/validateCustomer/" + RequestParameter.USER_ID_PATH_PARAM)
	public ResponseEntity<Map<Integer, LocalTime>> getUser(
			@PathVariable(RequestParameter.USER_ID) Integer userId) throws EntityNotFoundException {
		UserVO userVO = userService.getUserDetailsById(userId);
		Map<Integer, LocalTime> otpDetail = otpService.getOTP(userVO.getId().toString());
		return new ResponseEntity<Map<Integer, LocalTime>>(otpDetail, HttpStatus.OK);
	}

	@PostMapping("/validateOTP")
	public ResponseEntity<AuthenticationResponse> validateOTP(@Valid @RequestBody OTPForm otpForm, Errors result)
			throws ValidationException, InvalidOTPException {
		ValidationUtil.checkValidationErrors(result);
		otpService.isValidOTP(otpForm.getCustomerId().toString(), otpForm.getOtp());
		String authenticationToken = tokenProvider.generateToken(otpForm.getCustomerId());
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(authenticationToken),
				HttpStatus.OK);
	}

}

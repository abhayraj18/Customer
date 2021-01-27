package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.security.CurrentUser;
import com.example.config.security.UserPrincipal;
import com.example.exception.EntityExistsException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.ValidationException;
import com.example.form.EditUserForm;
import com.example.form.UserForm;
import com.example.service.UserService;
import com.example.util.Constants;
import com.example.util.ExceptionMessageConstants;
import com.example.util.RequestParameter;
import com.example.validation.FormValidator;
import com.example.validation.ValidationUtil;
import com.example.vo.UserVO;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FormValidator validator;

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<UserVO> create(@RequestBody @Validated UserForm userForm, Errors result)
			throws ValidationException, EntityExistsException, EntityNotFoundException {
		validator.validate(userForm, result);
		ValidationUtil.checkValidationErrors(result);

		UserVO userVO = userService.addUser(userForm);
		return new ResponseEntity<UserVO>(userVO, HttpStatus.OK);
	}

	@GetMapping("/get/" + RequestParameter.USER_ID_PATH_PARAM)
	@Secured({ Constants.ROLE_CUSTOMER, Constants.ROLE_MANAGER })
	public ResponseEntity<UserVO> getUser(@CurrentUser UserPrincipal currentUser,
			@PathVariable(RequestParameter.USER_ID) Integer userId)
			throws EntityNotFoundException {
		// Check if authenticated user id matches with passed id
		if (currentUser.isCustomer() && currentUser.getId() != userId) {
			throw new AccessDeniedException(ExceptionMessageConstants.ACCESS_DENIED_MESSAGE);
		}
		UserVO userVO = userService.getUserDetailsById(userId);
		return new ResponseEntity<UserVO>(userVO, HttpStatus.OK);
	}

	@GetMapping("/all")
	@Secured({ Constants.ROLE_MANAGER })
	public ResponseEntity<List<UserVO>> allUsers(@CurrentUser UserPrincipal currentUser) {
		// Check if authenticated user id matches with passed id
		if (currentUser.isCustomer()) {
			throw new AccessDeniedException(ExceptionMessageConstants.ACCESS_DENIED_MESSAGE);
		}
		List<UserVO> userVOList = userService.getAllUsers();
		return new ResponseEntity<List<UserVO>>(userVOList, HttpStatus.OK);
	}

	@PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ Constants.ROLE_MANAGER })
	public ResponseEntity<String> editUser(@CurrentUser UserPrincipal currentUser,
			@RequestBody @Validated EditUserForm userForm,
			Errors result) throws ValidationException, EntityNotFoundException, EntityExistsException {
		ValidationUtil.checkValidationErrors(result);

		if (!currentUser.isManager()) {
			throw new AccessDeniedException(ExceptionMessageConstants.UNAUTHORIZED_OPERATION_MESSAGE);
		}

		userService.editUser(userForm);
		return new ResponseEntity<String>("User information updated successfully", HttpStatus.OK);
	}

}

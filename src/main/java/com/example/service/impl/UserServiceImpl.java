package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.EntityExistsException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.ValidationException;
import com.example.form.EditUserForm;
import com.example.form.UserForm;
import com.example.model.Customer;
import com.example.model.Manager;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.RoleService;
import com.example.service.UserService;
import com.example.util.DateUtil;
import com.example.util.EnumConstant.RoleName;
import com.example.util.EnumConstant.UserType;
import com.example.util.ExceptionMessageConstants;
import com.example.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Boolean isUsernameAvailable(String username) throws EntityExistsException {
		if (userRepository.existsByUserName(username)) {
			throw new EntityExistsException("User already exists with username: " + username);
		}
		return Boolean.TRUE;
	}

	@Override
	public User getUserById(Integer userId) throws EntityNotFoundException {
		return userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User could not be found with id: " + userId));
	}

	@Override
	public UserVO getUserDetailsById(Integer userId) throws EntityNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User could not be found with id: " + userId));
		return mapToUserVO(user);
	}

	/**
	 * Map user to userVO
	 * 
	 * @param user
	 * @return
	 */
	private UserVO mapToUserVO(User user) {
		UserVO userVO = new UserVO();
		userVO.setId(user.getId());
		userVO.setUserName(user.getUserName());
		userVO.setPhoneNumber(user.getPhoneNumber());
		userVO.setGender(user.getGender());
		userVO.setDateOfBirth(DateUtil.formatLocalDateToString(user.getDateOfBirth(), DateUtil.DD_MM_YYYY_FORMAT));
		return userVO;
	}

	@Override
	public UserVO addUser(UserForm userForm)
			throws EntityNotFoundException, ValidationException, EntityExistsException {
		isUsernameAvailable(userForm.getUserName());
		User user = null;
		switch (UserType.valueOf(userForm.getUserType().toUpperCase())) {
		case CUSTOMER:
			user = getCustomer(userForm);
			break;
		case MANAGER:
			user = getManager(userForm);
			break;
		default:
			throw new ValidationException(ExceptionMessageConstants.INVALID_USER_TYPE_EXCEPTION_MESSAGE);
		}
		save(user);
		return mapToUserVO(user);
	}

	private Customer getCustomer(UserForm userForm) throws EntityNotFoundException {
		Customer customer = new Customer(userForm.getUserName(), userForm.getPhoneNumber(), userForm.getGender(),
				userForm.getDateOfBirth());
		customer.setRole(roleService.findByName(RoleName.ROLE_CUSTOMER.toString(), UserType.CUSTOMER.toString()));
		return customer;
	}

	private Manager getManager(UserForm userForm) throws EntityNotFoundException {
		Manager manager = new Manager(userForm.getUserName(), userForm.getPhoneNumber(), userForm.getGender(),
				userForm.getDateOfBirth());
		manager.setRole(roleService.findByName(RoleName.ROLE_MANAGER.toString(), UserType.MANAGER.toString()));
		return manager;
	}

	@Override
	public void editUser(EditUserForm userForm) throws EntityNotFoundException, EntityExistsException {
		User user = getUserById(userForm.getId());

		user.setDateOfBirth(DateUtil.parseStringToLocalDate(userForm.getDateOfBirth(), DateUtil.DD_MM_YYYY_FORMAT));
		user.setGender(userForm.getGender());
		user.setPhoneNumber(userForm.getPhoneNumber());
		save(user);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.service.UserService#getAllUsers()
	 */
	@Override
	public List<UserVO> getAllUsers() {
		List<UserVO> userVOList = new ArrayList<UserVO>();
		userRepository.findAll().forEach(user -> {
			UserVO userVO = mapToUserVO(user);
			userVOList.add(userVO);
		});
		return userVOList;
	}

}

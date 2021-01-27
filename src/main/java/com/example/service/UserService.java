package com.example.service;

import java.util.List;

import com.example.exception.EntityExistsException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.ValidationException;
import com.example.form.EditUserForm;
import com.example.form.UserForm;
import com.example.model.User;
import com.example.vo.UserVO;

public interface UserService {

	/**
	 * Service to get user details by id.
	 * 
	 * @param userId
	 * @return user details
	 * @throws EntityNotFoundException
	 */
	UserVO getUserDetailsById(Integer userId) throws EntityNotFoundException;

	/**
	 * Service to get user by id.
	 * 
	 * @param id
	 * @return user
	 * @throws EntityNotFoundException
	 */
	User getUserById(Integer id) throws EntityNotFoundException;

	/**
	 * Service to check if user name is available.
	 * 
	 * @param username
	 * @return true if user name is available
	 * @throws EntityExistsException
	 */
	Boolean isUsernameAvailable(String username) throws EntityExistsException;

	UserVO addUser(UserForm userForm)
			throws EntityNotFoundException, ValidationException, EntityExistsException;

	void save(User user);

	/**
	 * @param userForm
	 * @throws EntityNotFoundException
	 * @throws EntityExistsException
	 */
	void editUser(EditUserForm userForm) throws EntityNotFoundException, EntityExistsException;

	/**
	 * @return
	 */
	List<UserVO> getAllUsers();
}

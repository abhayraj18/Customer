/**
 * 
 */
package com.example.Customer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exception.EntityExistsException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.ValidationException;
import com.example.form.UserForm;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.impl.RoleServiceImpl;
import com.example.service.impl.UserServiceImpl;
import com.example.util.DateUtil;
import com.example.util.EnumConstant.RoleName;
import com.example.util.EnumConstant.UserType;

/**
 * @author abhay.jain
 *
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private RoleServiceImpl roleService;

	@Test
	public void testIsUsernameAvailableTrue() throws EntityExistsException {
		when(userRepository.existsByUserName("abc123")).thenReturn(false);
		boolean available = userService.isUsernameAvailable("abc123");
		assertTrue(available);
	}

	@Test
	public void testIsUsernameAvailableThrowsException() {
		when(userRepository.existsByUserName("abc123")).thenReturn(true);
		assertThrows(EntityExistsException.class, () -> {
			userService.isUsernameAvailable("abc123");
		});
	}

	@Test
	public void testGetUserById() throws EntityNotFoundException {
		User user = getUser("abc123", "M", "1234567890", null);
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		userService.getUserById(1);
	}

	@Test
	public void testAddUser() throws EntityExistsException, EntityNotFoundException, ValidationException {
		UserForm userForm = getUserForm("abc123", "M", "1234567890", "CUSTOMER", null);
		Role role = getRole("ROLE_CUSTOMER");
		when(userService.isUsernameAvailable(userForm.getUserName())).thenReturn(true);
		when(userRepository.existsByUserName(userForm.getUserName())).thenReturn(false);
		when(roleService.findByName(RoleName.ROLE_CUSTOMER.toString(), UserType.CUSTOMER.toString())).thenReturn(role);
		userService.addUser(userForm);
		verify(userRepository, times(1)).existsByUserName(userForm.getUserName());
	}

	/**
	 * @param string
	 * @return
	 */
	private Role getRole(String roleString) {
		Role role = new Role();
		role.setRoleName(roleString);
		return role;
	}

	private UserForm getUserForm(String userName, String gender, String phoneNumber, String userType,
			String dateOfBirth) {
		UserForm form = new UserForm();
		form.setUserName(userName);
		form.setPhoneNumber(phoneNumber);
		form.setGender(gender);
		form.setUserType(userType);
		form.setDateOfBirth(dateOfBirth);
		return form;
	}

	private User getUser(String userName, String gender, String phoneNumber, String dateOfBirth) {
		return new User(userName, phoneNumber, gender,
				DateUtil.parseStringToLocalDate(dateOfBirth, DateUtil.DD_MM_YYYY_FORMAT));
	}

}

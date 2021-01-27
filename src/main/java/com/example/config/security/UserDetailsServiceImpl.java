package com.example.config.security;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User could not be found with username: " + username));
		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Integer id) throws EntityNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User could not be found with id: " + id));
		return UserPrincipal.create(user);
	}

}

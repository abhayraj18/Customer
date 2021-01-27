package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.config.security.UserPrincipal;
import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationManager {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user = userRepository.findByUserName(authentication.getPrincipal().toString())
				.orElseThrow(() -> new UsernameNotFoundException(
						"User could not be found with username: " + authentication.getPrincipal().toString()));
		UserDetails userDetails = UserPrincipal.create(user);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				authentication.getPrincipal(), authentication.getCredentials(), userDetails.getAuthorities());
		usernamePasswordAuthenticationToken.setDetails(userDetails);
		return usernamePasswordAuthenticationToken;
	}

}

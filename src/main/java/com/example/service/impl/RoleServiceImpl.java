package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.EntityNotFoundException;
import com.example.model.Role;
import com.example.repository.RoleRepository;
import com.example.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role findByName(String name, String userType) throws EntityNotFoundException {
		return roleRepository.findByRoleName(name).orElseThrow(() -> new EntityNotFoundException("Role could not be found for " + userType));
	}

}

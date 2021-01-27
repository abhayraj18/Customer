package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.Role;

public interface RoleService {

	Role findByName(String name, String userType) throws EntityNotFoundException;

}

package com.example.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

	Optional<Role> findByRoleName(String name);

}

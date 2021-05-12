package com.orchard.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orchard.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	public Role findRoleByName(String name);

}

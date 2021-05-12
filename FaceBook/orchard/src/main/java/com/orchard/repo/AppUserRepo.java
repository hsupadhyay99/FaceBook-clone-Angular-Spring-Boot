package com.orchard.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.orchard.model.AppUser;


public interface AppUserRepo extends JpaRepository<AppUser, Long> {

	public AppUser findByUsername(String username);

	public AppUser findByEmail(String userEmail);
		
	@Query("SELECT appUser FROM AppUser appUser WHERE appUser.id=:x")
	public AppUser findUserById(@Param("x") Long id);

	public List<AppUser> findByUsernameContaining(String username);
}

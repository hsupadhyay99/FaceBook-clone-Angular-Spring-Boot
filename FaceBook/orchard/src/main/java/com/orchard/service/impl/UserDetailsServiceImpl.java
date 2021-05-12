package com.orchard.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.orchard.model.AppUser;
import com.orchard.model.UserRole;
import com.orchard.service.AccountService;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = accountService.findByUsername(username);
		if (appUser == null) {
			throw new UsernameNotFoundException("Username " + username + " was not found");
		}
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		Set<UserRole> userRoles = appUser.getUserRoles();
		userRoles.forEach(userRole -> {
			authorities.add(new SimpleGrantedAuthority(userRoles.toString()));
		});
		return new User(appUser.getUsername(), appUser.getPassword(), authorities);
	}

}
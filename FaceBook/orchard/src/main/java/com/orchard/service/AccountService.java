package com.orchard.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.orchard.model.AppUser;
import com.orchard.model.Role;

public interface AccountService {

	public AppUser saveUser(String name, String username, String email);

	public AppUser findByUsername(String username);

	public AppUser findByEmail(String userEmail);

	public List<AppUser> userList();

	public Role findUserRoleByName(String string);

	public Role saveRole(Role role);

	public void updateUserPassword(AppUser appUser, String newpassword);
	
	public AppUser updateUser(AppUser user, HashMap<String, String> request);

	public AppUser simpleSaveUser(AppUser user);

	public AppUser findUserById(Long id);
	
	public void deleteUser(AppUser appUser);

	public void resetPassword(AppUser user);

	public List<AppUser> getUsersListByUsername(String username);

	public String saveUserImage(MultipartFile multipartFile, Long userImageId);


}

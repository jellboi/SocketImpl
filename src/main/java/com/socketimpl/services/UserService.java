package com.socketimpl.services;

import java.util.List;

import com.socketimpl.beans.User;

public interface UserService {

	
	/* User CRUD */

	void registerUser(User user);

	User readUser(String emailId);

	void updateUser(User user);

	void deleteUser(User user);
	
	
	User findOrCreateUser(User inUser);
	
	
	List<User> fetchAllUsers();

	
	
	/* Invite */

	void inviteUser(String emailId);


}

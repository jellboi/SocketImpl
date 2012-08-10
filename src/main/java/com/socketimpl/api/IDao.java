package com.socketimpl.api;

import java.sql.Connection;
import java.util.List;

import com.socketimpl.beans.User;

public interface IDao {
	
	
	/* Generic */
	
	Connection getConnection();
	
	
	/* User */
	
	void insertUser(User user);
	
	User selectUserWithEmailId(String emailId);

	void updateUser(User user);
	
	void deleteUser(String emailId);
	
	
	List<User> selectAllUsers();
	
	
}

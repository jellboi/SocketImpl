package com.socketimpl.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.socketimpl.api.IDao;
import com.socketimpl.beans.User;
import com.socketimpl.daos.GenericDao;
import com.socketimpl.services.UserService;

public class UserServiceImpl implements UserService {
	
	public static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

	private IDao genericDao = GenericDao.getInstance();
	
	
	
	/* Singleton Implementation */
	
	private static final UserServiceImpl INSTANCE = new UserServiceImpl();
	
	private UserServiceImpl() {
		super();
	}
	
	public static UserServiceImpl getInstance() { 
		return INSTANCE; 
	}
	
	
	/* User CRUD */

	public void registerUser(User user) {
		if(user != null && user.getEmailId() != null) {
			genericDao.insertUser(user);
		}
	}

	public User readUser(String emailId) {
		User user = null;
		if(emailId != null) 
			user = genericDao.selectUserWithEmailId(emailId);
		return user;
	}

	public void updateUser(User user) {
		if(user != null && user.getEmailId() != null) {
			genericDao.updateUser(user);
		}
	}

	public void deleteUser(User user) {
		if(user != null && user.getEmailId() != null) {
			genericDao.deleteUser(user.getEmailId());
		}
	}
	
	
	public User findOrCreateUser(User inUser) {
		User user = null;
		if(inUser != null && inUser.getEmailId() != null) {
			user = this.readUser(inUser.getEmailId());
			if(user == null) {
				this.registerUser(inUser);
				user = this.readUser(inUser.getEmailId());
			}
		}
		return user;
	}

	
	
	public List<User> fetchAllUsers() {
		return genericDao.selectAllUsers();
	}
	
	

	/* Invite */
	
	public void inviteUser(String emailId) {
		// TODO Auto-generated method stub
		
	}

	

}


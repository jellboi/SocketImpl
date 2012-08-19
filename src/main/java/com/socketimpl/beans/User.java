package com.socketimpl.beans;

public class User extends BaseEntity {

	private String fullName;
	private String emailId;
	
	private String displayName;
	
	
	/* Constructors */
	
	public User() {
		super();
	}
	
	public User(String emailId) {
		super();
		this.emailId = emailId;
	}
	
	public User(String fullName, String emailId, String displayName) {
		super();
		this.fullName = fullName;
		this.emailId = emailId;
		this.displayName = displayName;
	}
	
	/* Getters and Setters */


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	

}

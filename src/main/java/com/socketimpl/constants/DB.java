package com.socketimpl.constants;

public class DB {
	

	/* Table names */
	
	public static final String TABLE_USERS = "users";
	
	
	
	/* Create Table statements */
	
	public static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " ("
			+ "pkey INT NOT NULL AUTO_INCREMENT PRIMARY KEY," 
			+ "full_name VARCHAR(100),"
			+ "email_id VARCHAR(100) NOT NULL,"
			+ "display_name VARCHAR(100),"
			+ "created_on TIMESTAMP(8) DEFAULT NOW(),"
			+ "CONSTRAINT uc_email_id UNIQUE (email_id)"
			+ ") TYPE=innodb;"; 
	
	
	
	

}

package com.socketimpl.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.socketimpl.api.IDao;
import com.socketimpl.beans.User;
import com.socketimpl.constants.DB;
import com.socketimpl.utils.SIProps;

public class GenericDao implements IDao {

	public static final Logger LOGGER = Logger.getLogger(GenericDao.class);

	
	/* DataBase Properties */

	public static final String DB_DRIVER = SIProps.DB_DRIVER;
	public static final String DB_URL = SIProps.DB_URL;
	public static final String DB_USERNAME = SIProps.DB_USERNAME;
	public static final String DB_PASSWORD = SIProps.DB_PASSWORD;

	private Connection connection = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	
	/* User : prepared statements */

	private static final String QRY_STR_CREATE_USER = "INSERT INTO users (full_name, email_id, display_name) VALUES(?,?,?)";
	private static final String QRY_STR_READ_USER = "SELECT * FROM users WHERE email_id=?";
	private static final String QRY_STR_UPDATE_USER = "UPDATE users SET full_name=?, display_name=? WHERE email_id=?";
	private static final String QRY_STR_DELETE_USER = "DELETE FROM users WHERE email_id=?";

	private static final String QRY_STR_READ_ALL_USER = "SELECT * FROM users";

	
	/* Singleton Implementation */

	private static final GenericDao INSTANCE = new GenericDao();

	private GenericDao() {
		super();
		this.prepareDatabaseAndcreateTables();
	}

	public static GenericDao getInstance() {
		return INSTANCE;
	}

	/* Generic */

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {
			LOGGER.info("Failed while conneting to Database at : " + DB_URL);
			e.printStackTrace();
		}
		return connection;
	}

	private void prepareDatabaseAndcreateTables() {
		connection = this.getConnection();
		if (connection != null) {
			try {
				pstmt = connection.prepareStatement(DB.CREATE_USERS_TABLE);
				pstmt.executeUpdate();
				
				LOGGER.info("Successfully created Database tables !!!");
				
			} catch (SQLException e) {
				LOGGER.info("Failed while creating Database tables !!!");
				e.printStackTrace();
			} finally {
				this.closeConnectionAndPreparedStmt();
			}
		}
	}

	private void closeConnectionAndPreparedStmt() {
		try {
			if (pstmt != null) 
				pstmt.close();
			if (connection != null)
				connection.close();
			
		} catch (Exception e) {
			LOGGER.info("Failed while closing connection or prepared statement !!!");
			e.printStackTrace();
		}
	}

	/* User */

	public void insertUser(User user) {
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QRY_STR_CREATE_USER);
			pstmt.setString(1, user.getFullName());
			pstmt.setString(2, user.getEmailId());
			pstmt.setString(3, user.getDisplayName());
			pstmt.executeUpdate();
			
			LOGGER.info("Successfully inserted user :" + user.getFullName());
			
		} catch (SQLException e) {
			LOGGER.info("Failed while inserting user !!!");
			e.printStackTrace();
			
		} finally {
			this.closeConnectionAndPreparedStmt();
			
		}
	}

	public User selectUserWithEmailId(String emailId) {
		User user = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QRY_STR_READ_USER);
			pstmt.setString(1, emailId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				user = new User(rs.getString("email_id"));
				user.setFullName(rs.getString("full_name"));
				user.setDisplayName(rs.getString("display_name"));
				user.setPkey(rs.getInt("pkey"));
				user.setCreatedOn(rs.getDate("created_on"));
			}
			
			LOGGER.info("Successfully read user :" + emailId );
			
		} catch (SQLException e) {
			LOGGER.info("Failed while reading user with email-id : " + emailId );
			e.printStackTrace();
			
		} finally {
			this.closeConnectionAndPreparedStmt();
			
		}
		return user;
	}

	public void updateUser(User user) {
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QRY_STR_UPDATE_USER);
			pstmt.setString(1, user.getFullName());
			pstmt.setString(2, user.getDisplayName());
			pstmt.setString(3, user.getEmailId());
			pstmt.executeUpdate();
			
			LOGGER.info("Successfully updated user :" + user.getFullName());
			
		} catch (SQLException e) {
			LOGGER.info("Failed while updating user !!!");
			e.printStackTrace();
			
		} finally {
			this.closeConnectionAndPreparedStmt();
			
		}
	}

	public void deleteUser(String emailId) {
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QRY_STR_DELETE_USER);
			pstmt.setString(1, emailId);
			pstmt.executeUpdate();
			
			LOGGER.info("Successfully deleted user with email :" + emailId);
			
		} catch (SQLException e) {
			LOGGER.info("Failed while deleting user !!!");
			e.printStackTrace();
			
		} finally {
			this.closeConnectionAndPreparedStmt();
			
		}
	}

	public List<User> selectAllUsers() {
		List<User> users = new ArrayList<User>();
		User user = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(QRY_STR_READ_ALL_USER);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				user = new User(rs.getString("email_id"));
				user.setFullName(rs.getString("full_name"));
				user.setDisplayName(rs.getString("display_name"));
				user.setPkey(rs.getInt("pkey"));
				user.setCreatedOn(rs.getDate("created_on"));
				users.add(user);
			}
			
			LOGGER.info("Successfully read all users !!!");

		} catch (SQLException e) {
			LOGGER.info("Failed while fetching all users !!!");
			e.printStackTrace();

		} finally {
			this.closeConnectionAndPreparedStmt();

		}
		
		return users;
	}
	

}

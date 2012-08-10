package com.socketimpl.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SIProps {
	
	public static final Logger LOGGER = Logger.getLogger(SIProps.class);
	
	private static final String PROPS_FILE_NAME = "socketimpl.properties";

	
	/* Properties */
	
	public static String DB_DRIVER;
	public static String DB_URL;
	public static String DB_USERNAME;
	public static String DB_PASSWORD;
	
	
	/* Singleton Implementation */
	
	private static final SIProps INSTANCE = new SIProps();
	
	private SIProps() {
		super();
		this.readPropertiesFile();		
	}
	
	public static SIProps getInstance() { 
		return INSTANCE; 
	}
	
	
	/* Methods */
	
	private void readPropertiesFile() {
		try {
			
			LOGGER.info("Loaded Properties from : " + PROPS_FILE_NAME );
			
			Properties props = new Properties();
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(PROPS_FILE_NAME);
			props.load(in);
			
			// DataBase
			DB_DRIVER = props.getProperty("jdbc.driverClassName");
			DB_URL = props.getProperty("jdbc.db.url");
			DB_USERNAME = props.getProperty("jdbc.db.username");
			DB_PASSWORD = props.getProperty("jdbc.db.password");
			
			LOGGER.info("Properties are loaded successfully.");
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error occured during properties file reading : " + e.getMessage());
		} 
		
	}

	
}

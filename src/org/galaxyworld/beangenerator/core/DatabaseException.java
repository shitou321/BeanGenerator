package org.galaxyworld.beangenerator.core;

@SuppressWarnings("serial")
public class DatabaseException extends Exception {

	public static final String FAILED_CREATE_CONNECTION = "Failed to create database connection.";
	
	private String message;
	
	public DatabaseException() {
		
	}

	public DatabaseException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

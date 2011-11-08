package org.galaxyworld.beangenerator.core;

@SuppressWarnings("serial")
public class GeneratorException extends Exception {
	
	public static final String FAILED_CREATE_PACKAGE_DIR = "Failed to create package directory structure.";
	
	private String message;
	
	public GeneratorException() {
		
	}

	public GeneratorException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

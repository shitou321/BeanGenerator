package org.galaxyworld.beangenerator.core;

public class Config {

	private String outputPath;
	
	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	
	private static class ConfigInstance {
		private static final Config instance = new Config();
	}
	
	public static Config getInstance() {
	    return ConfigInstance.instance;
	}
	
	private Config() {
		
	}
	
}

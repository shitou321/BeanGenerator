package org.galaxyworld.beangenerator.core;

import java.io.File;

import org.galaxyworld.beangenerator.data.CommonData;
import org.galaxyworld.beangenerator.util.Constants;
import org.galaxyworld.beangenerator.util.PathUtils;

public class Config {
	
	private String outputPath;
	
	private String appPath;
	
	private String configDirPath;
	
	private String configFilePath;
	
	private CommonData commonData;
	
	private static class ConfigInstance {
		private static final Config instance = new Config();
	}
	
	public static Config getInstance() {
	    return ConfigInstance.instance;
	}
	
	private Config() {
		appPath = PathUtils.getRuntimePath();
		StringBuilder sb = new StringBuilder(appPath);
		sb.append(File.separator);
		sb.append(Constants.CONFIG_DIR);
		configDirPath = sb.toString();
		sb.append(File.separator);
		sb.append(Constants.CONFIG_FILE);
		configFilePath = sb.toString();
	}
	
	public String getConfigDirPath() {
		return configDirPath;
	}
	
	public String getAppPath() {
		return appPath;
	}
	
	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	
	public String getConfigFilePath() {
		return configFilePath;
	}

	public CommonData getCommonData() {
		return commonData;
	}

	public void setCommonData(CommonData commonData) {
		this.commonData = commonData;
	}

}

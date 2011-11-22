/*
 * BeanGenerator
 * 
 * Copyright (C) 2011 galaxyworld.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.galaxyworld.beangenerator.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration properties for BeanGenerator. Singleton.
 * 
 * @author devbean
 * @version 0.0.1
 * @version 0.0.2 Rename from <code>Config</code> to <code>AppContext</code>
 */
public class AppContext {
	
	private String inputPath;
	
	private String outputPath;
	
	private String appPath;
	
	private String configDirPath;
	
	private String configFilePath;
	
	private Map<String, String> commonData;
	
	/**
	 * Gets the only instance.
	 * 
	 * @return configuration instance
	 */
	public static AppContext getInstance() {
	    return AppContextInstance.instance;
	}
	
	private AppContext() {
		appPath = ResourceUtils.getRuntimePath();
		StringBuilder sb = new StringBuilder(appPath);
		sb.append(File.separator);
		sb.append(Constants.CONFIG_DIR);
		configDirPath = sb.toString();
		sb.append(File.separator);
		sb.append(Constants.CONFIG_FILE);
		configFilePath = sb.toString();
		commonData = new HashMap<String, String>();
	}
	
	/**
	 * Gets configuration file directory path. The default is %APP_PATH%/config. Notice there is
	 * no path separator at the end of this string.
	 * 
	 * @return configuration file directory path
	 */
	public String getConfigDirPath() {
		return configDirPath;
	}
	
	/**
	 * Gets application path.
	 * 
	 * @return application path
	 */
	public String getAppPath() {
		return appPath;
	}
	
	/**
	 * Gets output directory path.
	 * 
	 * @return output directory path
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * Sets output directory path.
	 * 
	 * @param output directory path
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	
	/**
	 * Gets configuration file path. The default is %APP_PATH%/config/appconfig.properties.
	 * 
	 * @return configuration file path
	 */
	public String getConfigFilePath() {
		return configFilePath;
	}

	/**
	 * Add common data as following:
	 * <ul>
	 * 	<li><code>Constants.PACKAGE_NAME</code>: package name</li>
	 * 	<li><code>Constants.DEFAULT_COMMENT</code>: default comment</li>
	 * 	<li><code>Constants.DOC_AUTHOR</code>: author for @author</li>
	 * 	<li><code>Constants.DOC_VERSION</code>: version for @version</li>
	 * </ul>
	 * 
	 * @param key key for common data
	 * @param value value of this key
	 */
	public void addCommonData(String key, String value) {
		commonData.put(key, value);
	}
	
	/**
	 * Gets common data for keys:
	 * <ul>
	 * 	<li><code>Constants.PACKAGE_NAME</code>: package name</li>
	 * 	<li><code>Constants.DEFAULT_COMMENT</code>: default comment</li>
	 * 	<li><code>Constants.DOC_AUTHOR</code>: author for @author</li>
	 * 	<li><code>Constants.DOC_VERSION</code>: version for @version</li>
	 * </ul>
	 * 
	 * @param key to get
	 * @return value for this key
	 */
	public String getCommonDataValue(String key) {
		return commonData.get(key);
	}
	
	/**
	 * Gets common data as a map.
	 * 
	 * @return common data map
	 */
	public Map<String, String> getCommonDataMap() {
		return commonData;
	}
	
	/**
	 * Gets input directory path.
	 * 
	 * @return input directory path
	 */
	public String getInputPath() {
		return inputPath;
	}

	/**
	 * Sets input directory path.
	 * 
	 * @param inputPath input directory path
	 */
	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	private static class AppContextInstance {
		private static final AppContext instance = new AppContext();
	}
}

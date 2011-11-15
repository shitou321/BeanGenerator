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

/**
 * Constants used in BeanGenerator.
 * 
 * @author devbean
 * @version 0.0.1
 */
public final class Constants {
	
	/**
	 * Default configuration file directory name. Full path should be got
	 * by <code>Config.getInstance().getConfigDirPath()</code>.
	 */
	public static final String CONFIG_DIR = "config";
	
	/**
	 * Default configuration file name. Full path should be got
	 * by <code>Config.getInstance().getConfigFilePath()</code>.
	 */
	public static final String CONFIG_FILE = "appconfig.properties";
	
	/**
	 * Jar file which will be used to load JDBC driver class defined in properties file.
	 */
	public static final String PROP_JAR_PATH = "jar";
	
	/**
	 * Driver class name for JDBC defined in properties file.
	 */
	public static final String PROP_DRIVER = "driver";
	
	/**
	 * Database connection URL defined in properties file.
	 */
	public static final String PROP_URL = "url";
	
	/**
	 * Database user name defined in properties file.
	 */
	public static final String PROP_USER = "user";
	
	/**
	 * Database password defined in properties file.
	 */
	public static final String PROP_PASSWORD = "password";
	
}

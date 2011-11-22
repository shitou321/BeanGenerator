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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Resource utilities.
 * 
 * @author devbean
 * @version 0.0.1
 */
public final class ResourceUtils {
	
	private static Boolean init = false;
	
	private static ResourceBundle resourceBundle;
	
	static {
		if(!init) {
			init = true;
			Locale locale = Locale.getDefault();
			try {
				resourceBundle = ResourceBundle.getBundle("res.messages", locale);
			} catch (Exception e) {
				resourceBundle = ResourceBundle.getBundle("res.messages", new Locale("en", "US"));
			}
		}
	}
	
	/**
	 * Gets translated string from resource file.
	 * 
	 * @param key key in resource file
	 * @return message string mapping to this key
	 */
	public static final String tr(String key) {
		return resourceBundle.getString(key);
	}

	/**
	 * Reads the properties file from <i>filePath</i>.
	 * 
	 * @param filePath file path to read
	 * @return properties from this file
	 */
	public static final Properties read(String filePath) {
		Properties props = new Properties();
        try {
        	InputStream in = new BufferedInputStream(new FileInputStream(filePath));
        	props.load(in);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return props;
	}
	
	/**
	 * Gets application runtime path.
	 * 
	 * @return application runtime path
	 */
	public static final String getRuntimePath() {
		return new File(ClassLoader.getSystemClassLoader().getResource(".").getPath()).getAbsolutePath();
	}
	
	/**
	 * Loads class <i>className</i> from the jar file <i>jarFile</i>.
	 * 
	 * @param jarFile jar file to search
	 * @param className class name to load
	 * @return new class instance
	 * @throws Exception if any problem
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T loadJarFile(File jarFile, String className)
			throws Exception {
		URL[] urls = new URL[] { jarFile.toURI().toURL() };
		URLClassLoader classLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
		Class<?> cls = classLoader.loadClass(className);
		return (T)cls.newInstance();
	}
	
	/**
	 * Checks if the file with given name is a <i>class</i> file.
	 * 
	 * @param name given file name
	 * @return true if the file with given name is a <i>class</i> file
	 */
	public static final boolean isClassFile(String name) {
		return name.toLowerCase().endsWith(".class");
	}
	
}

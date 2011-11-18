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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.galaxyworld.beangenerator.core.AppContext;

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
	
	public static final void compile() {
		getFiles(AppContext.getInstance().getInputPath());
	}
	
	private static final void getFiles(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		List<String> compilationFileNames = new ArrayList<String>();
		for(File file : files) {
			if(file.isFile()) {
				String fileName = file.getName().toLowerCase();
				if(fileName.endsWith(".java")) {
					compilationFileNames.add(file.getAbsolutePath());
				}
            } else if(file.isDirectory()) {
            	getFiles(file.getPath());
            }
        }
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(compilationFileNames);
		// int results = compiler.run(null, null, null, file.getAbsolutePath());
		// System.out.println("Result code: " + results);
		Iterable<String> options = Arrays.asList("-d", "d:\\");
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, compilationUnits);
		Boolean result = task.call();
		if( result == true ) {
			System.out.println("Succeeded");
		}
	}
	
}

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

public class ResourceUtils {
	
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
	
	public static String tr(String key) {
		return resourceBundle.getString(key);
	}

	public static Properties read(String filePath) {
		Properties props = new Properties();
        try {
        	InputStream in = new BufferedInputStream(new FileInputStream(filePath));
        	props.load(in);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return props;
	}
	
	public static String getRuntimePath() {
		return new File(ClassLoader.getSystemClassLoader().getResource(".").getPath()).getAbsolutePath();
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T loadJarFile(File jarFile, String className)
			throws Exception {
		URL[] urls = new URL[] { jarFile.toURI().toURL() };
		URLClassLoader classLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
		Class<?> cls = classLoader.loadClass(className);
		return (T)cls.newInstance();
	}
	
}

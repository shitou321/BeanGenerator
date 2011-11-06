package org.galaxyworld.beangenerator.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

public class JarLoader {
	
	@SuppressWarnings("unchecked")
	public static final <T> T loadJarFile(File jarFile, String className) throws Exception {
		URL[] urls = new URL[] { jarFile.toURI().toURL() };
		URLClassLoader classLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
		Class<?> cls = classLoader.loadClass(className);
		return (T)cls.newInstance();
	}
	
	public static void main(String[] args) throws Exception {
		Properties props = PropertiesUtils.read("E:/workspace/BeanGenerator/config/jdbc.properties");
		Driver driver = loadJarFile(new File(props.getProperty("jar")), props.getProperty("driver"));		
		Connection conn = driver.connect(props.getProperty("url"), props);
		System.out.println("数据库连接成功");
	}
	
}

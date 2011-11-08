package org.galaxyworld.beangenerator.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class JarLoader {
	
	@SuppressWarnings("unchecked")
	public static final <T> T loadJarFile(File jarFile, String className)
			throws Exception {
		URL[] urls = new URL[] { jarFile.toURI().toURL() };
		URLClassLoader classLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
		Class<?> cls = classLoader.loadClass(className);
		return (T)cls.newInstance();
	}
	
}

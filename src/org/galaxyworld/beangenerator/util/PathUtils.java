package org.galaxyworld.beangenerator.util;

import java.io.File;

public class PathUtils {

	public static String getRuntimePath() {
		return new File(ClassLoader.getSystemClassLoader().getResource(".").getPath()).getAbsolutePath();
	}
	
}

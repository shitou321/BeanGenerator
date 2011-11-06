package org.galaxyworld.beangenerator.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

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
	
}

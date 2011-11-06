package org.galaxyworld.beangenerator.core;

import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public abstract class AbstractGenerator {
	
	protected Configuration cfg;

	public AbstractGenerator() {
		cfg = new Configuration();
        try {
			cfg.setDirectoryForTemplateLoading(new File("templates"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        cfg.setObjectWrapper(new DefaultObjectWrapper());
	}
	
}

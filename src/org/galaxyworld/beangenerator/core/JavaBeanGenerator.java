package org.galaxyworld.beangenerator.core;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.galaxyworld.beangenerator.data.RootData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Template;

public class JavaBeanGenerator extends AbstractGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(JavaBeanGenerator.class);
	
	private RootData root;
	
	public JavaBeanGenerator(RootData root) {
		this.root = root;
	}
	
	public void createPackageFolders() {
		if(!root.getPackageName().isEmpty()) {
			String pn = root.getPackageName();
			try {
				String outputPath = Config.getInstance().getOutputPath();
				FileUtils.forceMkdir(new File(outputPath + pn.replace(".", File.separator)));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	public void generate() {
		try {
			Template temp = cfg.getTemplate("JavaBean.ftl");
	        Writer out = new OutputStreamWriter(System.out);
	        temp.process(root.createRootMap(), out);
	        out.flush();
	        logger.info("Generates successfully.");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}

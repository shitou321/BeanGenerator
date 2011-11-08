package org.galaxyworld.beangenerator.core;

import java.io.File;
import java.io.FileOutputStream;
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
	
	private String outputFilePath;
	
	private RootData root;
	
	public JavaBeanGenerator(RootData root) {
		this.root = root;
	}
	
	private String createPackageFolders() throws GeneratorException {
		if(!root.getPackageName().isEmpty()) {
			String pn = root.getPackageName();
			try {
				outputFilePath = Config.getInstance().getOutputPath();
				outputFilePath = outputFilePath + pn.replace(".", File.separator);
				FileUtils.forceMkdir(new File(outputFilePath));
				return outputFilePath;
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new GeneratorException(GeneratorException.FAILED_CREATE_PACKAGE_DIR);
			}
		}
		return null;
	}
	
	private void generate(String fileName) {
		
	}

	public void generate() {
		try {
			Template temp = cfg.getTemplate("JavaBean.ftl");
			String packagePath = createPackageFolders();
			if(packagePath != null) {
				String path = packagePath + "\\test.java";
		        Writer out = new OutputStreamWriter(new FileOutputStream(new File(path)));
		        temp.process(root.createRootMap(), out);
		        out.flush();
		        logger.info("Success! Location: " + path);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}

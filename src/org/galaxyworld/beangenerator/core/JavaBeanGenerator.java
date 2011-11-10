package org.galaxyworld.beangenerator.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.galaxyworld.beangenerator.data.CommonData;
import org.galaxyworld.beangenerator.data.JavaBeanData;
import org.galaxyworld.beangenerator.util.BeanMapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Template;

public class JavaBeanGenerator extends AbstractGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(JavaBeanGenerator.class);
	
	private String outputFilePath;
	
	public JavaBeanGenerator() {
	}
	
	private String createPackageFolders() throws GeneratorException {
		CommonData cd = Config.getInstance().getCommonData();
		if(!cd.getPackageName().isEmpty()) {
			String pn = cd.getPackageName();
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

	public void generate(JavaBeanData data) {
		try {
			Template temp = cfg.getTemplate("JavaBean.ftl");
			String packagePath = createPackageFolders();
			if(packagePath != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(packagePath);
				sb.append(File.separator);
				sb.append(data.getClassName());
				sb.append(".java");
				String path =  sb.toString();
		        Writer out = new OutputStreamWriter(new FileOutputStream(new File(path)));
		        Map<String, Object> root = BeanMapUtils.toMap(data);
		        root.putAll(BeanMapUtils.toMap(Config.getInstance().getCommonData()));
		        temp.process(root, out);
		        out.flush();
		        logger.info("Success! Location: " + path + "; data: " + root);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}

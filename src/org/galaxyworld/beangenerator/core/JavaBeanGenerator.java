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

/**
 * JavaBean generator.
 * 
 * @author devbean
 * @version 0.0.1
 */
public class JavaBeanGenerator extends AbstractGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(JavaBeanGenerator.class);
	
	public JavaBeanGenerator() {
	}
	
	/**
	 * Creates directory structure according to package.
	 * 
	 * @return output file directory path
	 * @throws AppException if any problem
	 */
	private String createPackageFolders() throws AppException {
		CommonData cd = Config.getInstance().getCommonData();
		String pn = cd.getPackageName();
		try {
			String outputFilePath = Config.getInstance().getOutputPath();
			outputFilePath = outputFilePath + pn.replace(".", File.separator);
			FileUtils.forceMkdir(new File(outputFilePath));
			return outputFilePath;
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new AppException(AppException.FAILED_CREATE_PACKAGE_DIR);
		}
	}

	/**
	 * Generates JavaBean source code to disk.
	 * 
	 * @param data JavaBean data
	 */
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

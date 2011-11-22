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

package org.galaxyworld.beangenerator.generator;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.galaxyworld.beangenerator.event.GeneratorProcessEvent;
import org.galaxyworld.beangenerator.event.GeneratorProcessEvent.Phase;
import org.galaxyworld.beangenerator.util.AppContext;
import org.galaxyworld.beangenerator.util.AppException;
import org.galaxyworld.beangenerator.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ActionScript 3 bean code generator.
 * 
 * @author devbean
 * @version 0.0.1
 */
public class AS3BeanGenerator extends AbstractGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(AS3BeanGenerator.class);

	@Override
	public void generate() throws Exception {
		AppContext ctx = AppContext.getInstance();
		FileUtils.deleteDirectory(new File(ctx.getOutputPath()));
		java2AS3(new File(ctx.getInputPath()), new File(ctx.getOutputPath()));
	}
	
	private void java2AS3(File javaSourceDir, File outputDir) throws Exception {
		File[] javaFiles = javaSourceDir.listFiles();
		if (javaFiles != null) {
			for (File file : javaFiles) {
				if (file.isDirectory()) {
					File outputFile = new File(outputDir, file.getName());
					FileUtils.forceMkdir(outputFile);
					java2AS3(file, outputFile);
				} else {
					String fileName = file.getName();
					if (ResourceUtils.isClassFile(fileName)) {
						File asFile = new File(outputDir, fileName.replace(".class", ".as"));
						fireGeneratorProcessEvent(new GeneratorProcessEvent(Phase.ItemStarting,
								"Starting process " + asFile.getAbsolutePath(), this));
						asFile.createNewFile();
						outputAS3File(asFile, file);
					}
				}
			}
		} else {
			logger.warn("No any file in source directory.");
			AppException ex = new AppException("No any file in source directory.");
			throw ex;
		}
	}
	
	private void outputAS3File(File asFile, File javaFile) {
		// get full class name
		String javaFilePath = javaFile.getAbsolutePath();
		String className = javaFilePath.substring(AppContext.getInstance().getInputPath().length()).replace(File.separator, ".");
		System.out.println(className.substring(0, className.length() - 6));
	}

}

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
import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * The base generator. Detail generators should extends this class.
 * 
 * @author devbean
 * @version 0.0.1
 */
public abstract class AbstractGenerator {
	
	/**
	 * Configuration for <a href="http://freemarker.sourceforge.net/">FreeMarker</a>.
	 */
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

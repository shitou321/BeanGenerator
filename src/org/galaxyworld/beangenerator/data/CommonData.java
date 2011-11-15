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

package org.galaxyworld.beangenerator.data;

/**
 * Data for most source code file, such as package, author, etc.
 * 
 * @author devbean
 * @version 0.0.1
 */
public class CommonData {
	
	private String packageName;
	
	private String defaultComment;
	
	private String author;
	
	private String version;

	/**
	 * Gets default comment content if no special one.
	 * 
	 * @return default comment content
	 */
	public String getDefaultComment() {
		return defaultComment;
	}

	/**
	 * Sets default comment content.
	 * 
	 * @param defaultComment default comment
	 */
	public void setDefaultComment(String defaultComment) {
		this.defaultComment = defaultComment;
	}

	/**
	 * Gets author information for JavaDoc @author.
	 * 
	 * @return author information
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets author information.
	 * 
	 * @param author author information
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Gets version string for JavaDoc @version.
	 * 
	 * @return version string
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets version.
	 * 
	 * @param version new version string
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets package name.
	 * 
	 * @return package name
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Sets package name. BeanGenerate ensure the directory structure
	 * of this package.
	 * 
	 * @param packageName package name
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
}

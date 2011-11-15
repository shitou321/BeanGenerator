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
 * Data for class field.
 * 
 * @author devbean
 * @version 0.0.1
 */
public class FieldData {

	private String name;
	
	private String type;
	
	private String comment;
	
	/**
	 * Creates a field with name <i>name</i> and type <i>type</i>.
	 * 
	 * @param name name of this field
	 * @param type type of this field
	 */
	public FieldData(String name, String type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Creates a field with name <i>name</i>, type <i>type</i> and comment <i>comment</i>.
	 * 
	 * @param name name of this field
	 * @param type type of this field
	 * @param comment comment of this field
	 */
	public FieldData(String name, String type, String comment) {
		this.name = name;
		this.type = type;
		this.comment = comment;
	}
	
	/**
	 * Gets field name.
	 * 
	 * @return field name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets field name.
	 * 
	 * @param name new field name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets field type.
	 * 
	 * @return field type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets field type.
	 * 
	 * @param type new field type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets comment.
	 * 
	 * @return field comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets comment.
	 * 
	 * @param comment field comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}

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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Data for JavaBean.
 * 
 * @author devbean
 * @version 0.0.1
 */
public class JavaBeanData {
	
	/**
	 * Key for field comment.
	 */
	public static final String KEY_COMMENT = "comment";
	
	/**
	 * Key for field name.
	 */
	public static final String KEY_NAME = "name";
	
	/**
	 * Key for field type.
	 */
	public static final String KEY_TYPE = "type";

	private String className;
	
	private String comment;
	
	private String tableName;
	
	private Collection<Map<String, String>> fields;
	
	public JavaBeanData() {
		fields = new HashSet<Map<String, String>>();
	}
	
	/**
	 * Add a field.
	 * 
	 * @param field field to add
	 */
	public void addField(FieldData field) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put(KEY_NAME, field.getName());
		fieldMap.put(KEY_TYPE, field.getType());
		fieldMap.put(KEY_COMMENT, field.getComment());
		fields.add(fieldMap);
	}

	/**
	 * Gets fields list.
	 * 
	 * @return fields list
	 */
	public Collection<Map<String, String>> getFields() {
		return fields;
	}

	/**
	 * Sets fields list.
	 * 
	 * @param fields new fields list
	 */
	public void setFields(Collection<Map<String, String>> fields) {
		this.fields = fields;
	}

	/**
	 * Gets class name.
	 * 
	 * @return class name
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Gets class comment.
	 * 
	 * @return class comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets class name.
	 * 
	 * @param className new class name
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Sets class comment.
	 * 
	 * @param comment new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Gets database table name relates to this bean.
	 * 
	 * @return table name
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Sets database table name.
	 * 
	 * @param tableName new table name
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}

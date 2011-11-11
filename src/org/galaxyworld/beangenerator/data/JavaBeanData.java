package org.galaxyworld.beangenerator.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.galaxyworld.beangenerator.util.Constants;

public class JavaBeanData {

	private String className;
	
	private String comment;
	
	private String tableName;
	
	private Collection<Map<String, String>> fields;
	
	public JavaBeanData() {
		fields = new HashSet<Map<String, String>>();
	}
	
	/**
	 * Add a field. Each field is a map with three keys:
	 * <ul>
	 * 	<li>DataFields.NAME for field name, required;</li>
	 * 	<li>DataFields.TYPE for field type, required;</li>
	 * 	<li>DataFields.COMMENT for field comment, optional.</li>
	 * </ul>
	 * 
	 * @param field field to add
	 */
	public void addField(FieldData field) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put(Constants.DATA_KEY_NAME, field.getName());
		fieldMap.put(Constants.DATA_KEY_TYPE, field.getType());
		fieldMap.put(Constants.DATA_KEY_COMMENT, field.getComment());
		fields.add(fieldMap);
	}

	public Collection<Map<String, String>> getFields() {
		return fields;
	}

	public void setFields(Collection<Map<String, String>> fields) {
		this.fields = fields;
	}

	public String getClassName() {
		return className;
	}

	public String getComment() {
		return comment;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}

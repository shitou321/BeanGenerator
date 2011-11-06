package org.galaxyworld.beangenerator.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class RootData {

	private String packageName = DataConstants.EMPTY;
	
	private String className = DataConstants.EMPTY;
	
	private String comment = DataConstants.EMPTY;
	
	private String author = DataConstants.EMPTY;
	
	private String version = DataConstants.EMPTY;
	
	private Collection<Map<String, String>> fields;
	
	public RootData() {
		fields = new HashSet<Map<String, String>>();
	}
	
	public Map<String, Object> createRootMap() {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put(DataFields.PACKAGE, packageName);
		root.put(DataFields.CLASS, className);
		root.put(DataFields.COMMENT, comment);
		root.put(DataFields.AUTHOR, author);
		root.put(DataFields.VERSION, version);
		root.put(DataFields.FIELDS, fields);
		return root;
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
		fieldMap.put(DataFields.NAME, field.getName());
		fieldMap.put(DataFields.TYPE, field.getType());
		fieldMap.put(DataFields.COMMENT, field.getComment());
		fields.add(fieldMap);
	}

	public Collection<Map<String, String>> getFields() {
		return fields;
	}

	public void setFields(Collection<Map<String, String>> fields) {
		this.fields = fields;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public String getComment() {
		return comment;
	}

	public String getAuthor() {
		return author;
	}

	public String getVersion() {
		return version;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}

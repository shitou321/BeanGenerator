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

import java.util.List;

/**
 * Data for ActionScript 3 bean.
 * 
 * @author devbean
 * @version 0.0.1
 */
public class AS3BeanData {

	private String packageName;
	
	private List<String> imports;
	
	private boolean bindable;
	
	private boolean remote;
	
	private String remoteClass;
	
	private String className;
	
	private boolean subclass;
	
	private String superClassName;
	
	private List<FieldData> fields;

	/**
	 * Gets package name for this class.
	 * 
	 * @return package name
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Sets package name for this class.
	 * 
	 * @param packageName new package name
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * Gets import classes list.
	 * 
	 * @return import classes list
	 */
	public List<String> getImports() {
		return imports;
	}

	/**
	 * Sets imports classes list.
	 * 
	 * @param imports import classes list
	 */
	public void setImports(List<String> imports) {
		this.imports = imports;
	}

	/**
	 * Checks if the ActionScript 3 class is <i>bandable</i>.
	 * 
	 * @return true if AS3 class should be bindable
	 */
	public boolean isBindable() {
		return bindable;
	}

	/**
	 * Sets if the ActionScript 3 class is <i>bandable</i>.
	 * 
	 * @param bindable true if AS3 class should be bindable
	 */
	public void setBindable(boolean bindable) {
		this.bindable = bindable;
	}

	/**
	 * Checks if the ActionScript 3 class is remote.
	 * 
	 * @return true if AS3 class is remote
	 */
	public boolean isRemote() {
		return remote;
	}

	/**
	 * Sets the ActionScript 3 class is remote or not.
	 * 
	 * @param remote true if the AS3 class is remote
	 */
	public void setRemote(boolean remote) {
		this.remote = remote;
	}

	/**
	 * Gets remote class.
	 * 
	 * @return remote class name
	 */
	public String getRemoteClass() {
		return remoteClass;
	}

	/**
	 * Sets remote class name if remote.
	 * 
	 * @param remoteClass remote class name
	 */
	public void setRemoteClass(String remoteClass) {
		this.remoteClass = remoteClass;
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
	 * Sets class name.
	 * 
	 * @param className new class name
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Checks if the ActionScript 3 class is a subclass of some class.
	 * 
	 * @return true if the AS3 class is a subclass
	 */
	public boolean isSubclass() {
		return subclass;
	}

	/**
	 * Sets the ActionScript 3 class is a subclass or not.
	 * 
	 * @param subclass true if the AS3 class is a subclass
	 */
	public void setSubclass(boolean subclass) {
		this.subclass = subclass;
	}

	/**
	 * Gets the ActionScript 3 class's super class name if it is a subclass.
	 * 
	 * @return super class name
	 */
	public String getSuperClassName() {
		return superClassName;
	}

	/**
	 * Sets the super class name
	 * 
	 * @param superClassName new super class name
	 */
	public void setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
	}

	/**
	 * Gets the class fields.
	 * 
	 * @return fields list
	 */
	public List<FieldData> getFields() {
		return fields;
	}

	/**
	 * Sets class fields list.
	 * 
	 * @param fields new class fields list
	 */
	public void setFields(List<FieldData> fields) {
		this.fields = fields;
	}
	
}

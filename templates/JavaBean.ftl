package ${package};

/**
 * ${comment}
 *
 * @author ${author}
 * @version ${version}
 */
public class ${class?cap_first} {
	
	public ${class?cap_first}() {
	
	}
	
	<#list fields as field>
	/**
	 * ${field.comment}
	 */
	private ${field.type} ${field.name};
	
	</#list>
	<#list fields as field>
	/**
	 * Sets ${field.name} to new value.
	 * 
	 * @param ${field.name} new value
	 */
	public void set${field.name?cap_first}(${field.type} ${field.name}) {
		this.${field.name} = ${field.name};
	}
	
	/**
	 * Gets ${field.name}.
	 *
	 * @return ${field.name} value
	 */
	public ${field.type} get${field.name?cap_first}() {
		return this.${field.name};
	}
	
	</#list>
}
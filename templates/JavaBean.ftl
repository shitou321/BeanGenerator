package ${packageName};

/**
 * ${comment}
 *
 * @author ${author}
 * @version ${version}
 */
public class ${className?cap_first} {
	
	public ${className?cap_first}() {
	
	}
	
	<#list fields as field>
	/**
	 * ${field.comment}
	 */
	private ${field.type} ${field.name};
	
	</#list>
	<#list fields as field>
	public void set${field.name?cap_first}(${field.type} ${field.name}) {
		this.${field.name} = ${field.name};
	}
	
	public ${field.type} get${field.name?cap_first}() {
		return this.${field.name};
	}
	
	</#list>
}
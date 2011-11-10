package ${packageName};

/**
 * ${comment!""}
 *
 * @author ${author!""}
 * @version ${version!""}
 */
public class ${className} {
	
	public ${className}() {
	
	}
	
	<#list fields as field>
	/**
	 * ${field.comment!""}
	 */
	private ${field.type} ${field.name?uncap_first};
	
	</#list>
	<#list fields as field>
	public void set${field.name}(${field.type} ${field.name?uncap_first}) {
		this.${field.name?uncap_first} = ${field.name?uncap_first};
	}
	
	public ${field.type} get${field.name?cap_first}() {
		return this.${field.name?uncap_first};
	}
	
	</#list>
}
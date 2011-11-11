package ${packageName}
{
	<#list imports as importItem>
	import importItem;
	
	</#list>
	
	<#if bindable>
    [Bindable]
    </#if>
    <#if remote>
    [RemoteClass(alias="${remoteClass}")]
    </#if>
    public class ${className}<#if subclass> extends ${superClassName}</#if>
    {
    	<#list fields as field>
		/**
		 * ${field.comment!}
		 */
		public var ${field.name} : ${field.type};
		
		</#list>

        public function ${className}()
        {
        }

    }
}
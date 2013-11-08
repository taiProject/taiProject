<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />
<div id="pageTitle">
	<@spring.message 'files.list.title' />
</div>

${username}
<br/>
<br/>
<table border="1">
	<tr>
		<td>
			Title
		</td>
		<td>
			Description
		</td>
	</tr>
	<#if files??>
	<#list files as file>
		<tr>
			<td>
				${file.title}
			</td>
			<td>
				${file.description}
			</td>
		</tr>
	</#list>
	</#if>
</table>

<br/>
<br/>
<a href="<@spring.url '/j_spring_security_logout'/>"><@spring.message 'logout' /></a>
<#include "templates/footer.ftl">
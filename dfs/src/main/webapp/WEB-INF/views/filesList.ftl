<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />
<div id="pageTitle">
	<@spring.message 'files.list.title' />
</div>

<table class="filesTable">
	<tr id="filesTableHead">
		<th>
			Title
		</th>
		<th>
			Description
		</th>
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

<#include "templates/footer.ftl">
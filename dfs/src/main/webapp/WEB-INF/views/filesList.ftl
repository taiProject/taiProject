<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />
<div class="pageTitle">
	<@spring.message 'files.list.title' />
</div>

<div class="contentDiv">
	<table class="filesTable">
		<tr id="filesTableHead">
			<th>
				<@spring.message 'files.file.name' />
			</th>
			<th>
				<@spring.message 'files.file.description' />
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
</div>

<#include "templates/footer.ftl">
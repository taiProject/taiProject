<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />
<div class="pageTitle fontStyle">
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
			<#if isAdmin>
				<th>
				</th>
				<th>
				</th>
			</#if>
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
				<#if isAdmin>
					<td>
						<div class="tableButton">
							<@spring.message 'files.file.edit' />
						</div>
					</td>
					<td>
						<div class="tableButton">
							<@spring.message 'files.file.delete' />
						</div>
					</td>
				</#if>
			</tr>
		</#list>
		</#if>
	</table>
</div>

<#include "templates/footer.ftl">
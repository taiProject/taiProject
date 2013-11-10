<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />
<#import "templates/macros.ftl" as m />


<div class="pageTitle fontStyle">
	<@spring.message 'files.list.title' />
</div>

<div class="contentDiv">
	<#if isAdmin>
		<div class="generalButtons">
			<@m.button 'files.file.delete.all' '/deleteAll' true/>
			<@m.button 'files.file.new' '/newFile' true/>
		</div>
	</#if>
	
	<table class="filesTable">
		<tr id="filesTableHead">
			<th>
				<@spring.message 'files.file.name' />
			</th>
			<th>
				<@spring.message 'files.file.description' />
			</th>
			<th>
			</th>
			<#if isAdmin>
				<th>
				</th>
				<th>
				</th>
			</#if>
		</tr>
		<#assign cnt = 0/>
		<#if files??>
		<#list files as file>
			<tr>
				<td>
					${file.title}
				</td>
				<td>
					${file.description}
				</td>
				<td>
					<@m.button 'files.file.download' '/file/${cnt}' false/>
				</td>
				<#if isAdmin>
					<td>
						<@m.button 'files.file.edit' '/edit/${cnt}' false/>
					</td>
					<td>
						<@m.button 'files.file.delete' '/delete/${file.id}' false/>
					</td>
				</#if>
			</tr>
		<#assign cnt = cnt + 1/>
		</#list>
		</#if>
	</table>
	
	<#if isAdmin>
		<div class="generalButtons">
			<@m.button 'files.file.delete.all' '/deleteAll' true/>
			<@m.button 'files.file.new' '/newFile' true/>
		</div>
	</#if>
</div>

<#include "templates/footer.ftl">
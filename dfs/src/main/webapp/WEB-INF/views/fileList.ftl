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
					<a href="<@spring.url '/file/${cnt}'/>">
						<div class="tableButton">
							<@spring.message 'files.file.download' />
						</div>
					</a>
				</td>
				<#if isAdmin>
					<td>
						<div class="tableButton">
							<@spring.message 'files.file.edit' />
						</div>
					</td>
					<td>
						<a href="<@spring.url '/delete/${file.id}'/>">
							<div class="tableButton">
								<@spring.message 'files.file.delete' />
							</div>
						</a>
					</td>
				</#if>
			</tr>
		<#assign cnt = cnt + 1/>
		</#list>
		</#if>
	</table>
</div>

<#include "templates/footer.ftl">
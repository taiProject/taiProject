<#import "templates/spring.ftl" as spring />
<#import "templates/macros.ftl" as m />

<@m.header>
</@m.header>
<@m.body>
	<@m.mainMenu />
	
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
		
		<table class="dfsTable">
			<tr id="dfsTableHead">
				<th>
					<@spring.message 'files.file.name' />
				</th>
				<th>
					<@spring.message 'files.file.description' />
				</th>
				<th class="buttonCell">
				</th>
				<#if isAdmin>
					<th class="buttonCell">
					</th>
					<th class="buttonCell">
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
					<td class="buttonCell">
						<@m.button 'files.file.download' '/file/${cnt}' false/>
					</td>
					<#if isAdmin>
						<td class="buttonCell">
							<@m.button 'files.file.edit' '/edit/${cnt}' false/>
						</td>
						<td class="buttonCell">
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
	            </br>
				<@m.button 'files.file.delete.all' '/deleteAll' true/>
	
				<@m.button 'files.file.new' '/newFile' true/>
			</div>
		</#if>
	</div>
</@m.body>
<#import "spring.ftl" as spring />

<#macro button name url isGeneral>
<div>
	<a href="<@spring.url url/>">
		<div class="tableButton <#if isGeneral>button</#if>">
			<@spring.message name/>
		</div>
	</a>
</div>
</#macro>


<#macro header>
<html>
	<head>
		<link href="<@spring.url '/resources/images/favicon.ico'/>" rel="shortcut icon" >
		<link href="<@spring.url '/resources/styles/styles.css'/>" rel="stylesheet" type="text/css">
		<link href="<@spring.url '/resources/styles/tables.css'/>" rel="stylesheet" type="text/css">
		<link href="<@spring.url '/resources/styles/file.css'/>" rel="stylesheet" type="text/css">
		
		<#nested>
	</head>
</#macro>

<#macro body>
	<body>
		<div class="applicationLogo">
			<center>
				<img src="<@spring.url '/resources/images/logo.png'/>"/>
			</center>
		</div>
		
		<#nested>
	
	</body>
</html>
</#macro>

<#macro mainMenu>
		<div class="mainMenu">
			<a href="<@spring.url '/index'/>">
				<div class="abstractMenuButton <#if viewName="home">clickedMenuButton<#else>menuButton</#if>">
					<@spring.message 'homePage' />
				</div>
			</a>
			<a href="<@spring.url '/fileList'/>">
				<div class="abstractMenuButton <#if viewName="fileList">clickedMenuButton<#else>menuButton</#if>">
					<@spring.message 'filesList' />
				</div>
			</a>
			<#if isAdmin>
				<a href="<@spring.url '/userManagement'/>">
					<div class="abstractMenuButton <#if viewName="userManagement">clickedMenuButton<#else>menuButton</#if>">
						<@spring.message 'userManagement' />
					</div>
				</a>
			</#if>
			<a href="<@spring.url '/userInfo'/>">
				<div class="abstractMenuButton <#if viewName="userInfo">clickedMenuButton<#else>menuButton</#if>">
					<@spring.message 'account' />
				</div>
			</a>
			<a href="<@spring.url '/j_spring_security_logout'/>">
				<div class="abstractMenuButton menuButton">
					<@spring.message 'logout' />
				</div>
			</a>
		</div>
		
		<#nested>
</#macro>

<#macro fileTable>
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
</#macro>
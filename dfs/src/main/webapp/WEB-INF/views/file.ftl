<#import "templates/spring.ftl" as spring />
<#import "templates/macros.ftl" as m />

<@m.header>
</@m.header>
<@m.body>
	<@m.mainMenu />
	
	<div class="pageTitle fontStyle">
		<#if newFile>
			<@spring.message 'new.file.title' />
		<#else>
			<@spring.message 'edit.file.title' />
		</#if>
	</div>
	
	<div class="contentDiv">
	<center>
		<form method="post" action="<#if newFile><@spring.url '/uploadFile'/><#else><@spring.url '/editFile'/></#if>" enctype="multipart/form-data">
			<table class="fileTable">
				<#if newFile>
					<tr>
						<td class="fontStyle">
							<@spring.message 'file.file.location' />:
						</td>
						<td>
							<input class="loginInput fileInput" type="file" name="file" id="file" />
						</td>
					</tr>
				<#else>
					<input type="hidden" name="fileNr" value="${fileNr}"/>
				</#if>
				<tr>
					<td class="fontStyle">
						<@spring.message 'file.name' />:
					</td>
					<td>
						<input class="loginInput fileInput" type="text" name="fileName" id="fileName"  maxlength="64" <#if !newFile>value="${file.title}"</#if> />
					</td>
				</tr>
				<tr>
					<td class="fontStyle">
						<@spring.message 'file.description'/>:
					</td>
					<td>
						<textarea class="loginInput fileInput textarea" name="description" id="description" rows="5" ><#if !newFile>${file.description}</#if></textarea>
					</td>
				</tr>
				<tr>
					<td colspan=2 align="right">
						<input class="loginInput inputButton" type="submit" value="<@spring.message 'file.submit.button'/>" />
						<input class="loginInput inputButton" type="button" name="cancel" value="<@spring.message 'file.cancel.button'/>" onClick="window.location='<@spring.url '/fileList'/>';"/>
					</td>
				</tr>
			</table>
		</form>
	</center>
	</div>

</@m.body>
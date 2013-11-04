<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />

<div class="login loginBase" align="center">
	<h1><@spring.message 'login.title' /></h1>

	<form method="post" action="<@spring.url '/j_spring_security_check'/>">
		<table>
			<tr>
				<td>
					<@spring.message 'login.username' />:
				</td>
				<td>
					<input class="loginInput" type="text" name="j_username" id="j_username" size="30" maxlength="40" />
				</td>
			</tr>
			<tr>
				<td>
					<@spring.message 'login.password'/>:
				</td>
				<td>
					<input class="loginInput" type="password" name="j_password" id="j_password" size="30" maxlength="32" />
				</td>
			</tr>
			<tr>
				<td colspan=2 align="right">
					<input class="loginInput inputButton" type="submit" value="<@spring.message 'login.login.button'/>" />
				</td>
			</tr>
		</table>
	</form>

</div>

<#if message?has_content>
	<div class="loginInfo loginBase" id="<#if message == "bad credentials">error<#else>info</#if>">
		<div>
			<h2>
			<#if message == "bad credentials">
				<@spring.message 'login.bad.credentials' />
			<#else>
				<@spring.message 'login.logout.success' />
			</#if>
			</h2>
		</div
		<br/>
		<br/>
	</div
</#if>

<#include "templates/footer.ftl">
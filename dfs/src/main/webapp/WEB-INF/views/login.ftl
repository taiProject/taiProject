<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />

<div>
	<h1><@spring.message 'login.title' /></h1>

	<form method="post" action="<@spring.url '/j_spring_security_check'/>">
		<table>
			<tr>
				<td>
					<@spring.message 'login.username' />:
				</td>
				<td>
					<input type="text" name="j_username" id="j_username" size="30" maxlength="40" />
				</td>
			</tr>
			<tr>
				<td>
					<@spring.message 'login.password'/>:
				</td>
				<td>
					<input type="text" name="j_password" id="j_password" size="30" maxlength="32" />
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<input type="submit" value="<@spring.message 'login.login.button'/>" />
				</td>
			</tr>
		</table>
	</form>
</div>

<br/>

<div>
	<#if message?has_content>
		<div>
			<h3>
			<#if message == "bad credentials">
				<@spring.message 'login.bad.credentials' />
			<#else>
				<@spring.message 'login.logout.success' />
			</#if>
			</h3>
		</div
		<br/>
		<br/>
	</#if>
</div

<#include "templates/footer.ftl">
<#import "templates/spring.ftl" as spring />
<#import "templates/macros.ftl" as m />

<@m.header>
</@m.header>
<@m.body>
	<@m.mainMenu />
	
	<div class="pageTitle fontStyle">
		<@spring.message 'user.info.title' />
	</div>
	
	<div class="contentDiv">
	    <form method="post" action="<@spring.url '/editUserInfo'/>" enctype="multipart/form-data">
	        <table class="userTable">
	            <input type="hidden" type="text" name="login" id="login" maxlength="64" value="${login}" />
	            <tr>
	                <td class="fontStyle">
	                    <@spring.message 'user.login'/>
	                </td>
	                <td>
	                    <input class="loginInput fileInput" type="text" name="newLogin" id="newLogin" maxlength="64" value="${login}" />
	                </td>
	            </tr>
	            <tr>
	                <td class="fontStyle">
	                <@spring.message 'user.oldPassword'/>
	                </td>
	                <td>
	                    <input class="loginInput fileInput" type="password" name="oldPassword" id="oldPassword" maxlength="64"/>
	                </td>
	            </tr>
	            <tr>
	                <td class="fontStyle">
	                <@spring.message 'user.newPassword'/>
	                </td>
	                <td>
	                    <input class="loginInput fileInput" type="password" name="password" id="password" maxlength="64"/>
	                </td>
	            </tr>
	            <tr>
	                <td class="fontStyle">
	                <@spring.message 'user.passwordReWrite'/>
	                </td>
	                <td>
	                    <input class="loginInput fileInput" type="password" name="passwordReWrite" id="passwordReWrite" maxlength="64"/>
	                </td>
	            </tr>
	
	            <tr>
	                <td>
	                    <input type="hidden" name="role" <#if admin>value="ROLE_ADMIN"<#else>value="ROLE_USER"</#if> />
	                </td>
	            </tr>
	
	            <tr>
	                <td colspan=2 align="right">
	                    <input class="loginInput inputButton" type="submit"
	                           value="<@spring.message 'user.submit.button'/>"/>
	                    <input class="loginInput inputButton" type="button" name="cancel"
	                           value="<@spring.message 'user.cancel.button'/>"
	                           onClick="window.location='<@spring.url '/index'/>';"/>
	                </td>
	            </tr>
	        </table>
	    </form>
	</div>
	
	<#if message?has_content>
	    <div class="loginInfo loginBase" id="error">
	        <div>
	            <h2>
	                <#if message == "wrong old">
	                    <@spring.message 'user.info.wrongold' />
	                <#else>
	                    <@spring.message 'user.info.mismatch' />
	                </#if>
	            </h2>
	        </div
	        <br/>
	        <br/>
	        </div>
	    </div>
	</#if>
</@m.body>
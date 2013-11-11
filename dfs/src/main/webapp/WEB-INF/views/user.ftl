<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />

<div class="pageTitle fontStyle">
<#if newUser>
    <@spring.message 'new.user.title' />
<#else>
    <@spring.message 'edit.user.title' />
</#if>
</div>

<div class="contentDiv">
    <center>
        <#if !newUser>
            <#assign checked = (user.roleName == "ROLE_ADMIN")/>
        <#else>
            <#assign checked = false>
        </#if>
        <form method="post" action="<#if newUser><@spring.url '/newUser'/><#else><@spring.url '/editUser'/></#if>" enctype="multipart/form-data">
            <table class="userTable">
                <#if newUser>
                    <tr>
                        <td class="fontStyle">
                            <@spring.message 'user.login'/>
                        </td>
                        <td>
                            <input class="loginInput fileInput" type="text" name="login" id="login" maxlength="64" />
                        </td>
                    </tr>
                <#else>
                    <input type="hidden" name="login" value="${login}"/>
                </#if>
                <tr>
                    <td class="fontStyle">
                        <@spring.message 'user.password'/>
                    </td>
                    <td>
                        <input class="loginInput fileInput" type="text" name="password" id="password" maxlength="64" <#if !newUser>value="${user.password}"</#if>/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="radio" name="role" value="ROLE_ADMIN" <#if checked>checked</#if>>ADMIN</input>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="radio" name="role" value="ROLE_USER" <#if !checked>checked</#if>>USER</input>
                    </td>
                </tr>

                <tr>
                    <td colspan=2 align="right">
                        <input class="loginInput inputButton" type="submit"
                               value="<@spring.message 'user.submit.button'/>"/>
                        <input class="loginInput inputButton" type="button" name="cancel"
                               value="<@spring.message 'user.cancel.button'/>"
                               onClick="window.location='<@spring.url '/userManagement'/>';"/>
                    </td>
                </tr>
            </table>
        </form>
    </center>
</div>
<#import "templates/spring.ftl" as spring />
<#import "templates/macros.ftl" as m />

<@m.header>
</@m.header>
<@m.body>
	<@m.mainMenu />
	
	<div class="pageTitle fontStyle">
		<@spring.message 'users.management.title' />
	</div>
	
	<div class="contentDiv">
	    <div class="generalButtons">
	        <@m.button 'users.user.add' '/addUser' true/>
	    </div>
	
	    <table class="dfsTable">
	        <tr id="dfsTableHead">
	            <th>
	            <@spring.message 'users.user.login' />
	            </th>
	            <th>
	            <@spring.message 'users.user.role' />
	            </th>
	            <th class="buttonCell">
	            </th>
	            <th class="buttonCell">
	            </th>
	        </tr>
		    <#if users??>
		        <#list users as user>
		            <tr>
		                <td>
		                ${user.login}
		                </td>
		                <td>
		                ${user.roleName}
		                </td>
		                <td class="buttonCell">
		                    <@m.button 'users.user.edit' '/editUser/${user.login}' false/>
		                </td>
		                <td class="buttonCell">
		                    <@m.button 'users.user.delete' '/deleteUser/${user.login}' false/>
		                </td>
		            </tr>
		        </#list>
		    </#if>
	    </table>
	
	    <div class="generalButtons">
	        <@m.button 'users.user.add' '/addUser' true/>
	    </div>
	
	</div>
</@m.body>

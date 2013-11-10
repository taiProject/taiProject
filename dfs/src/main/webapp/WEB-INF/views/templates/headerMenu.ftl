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
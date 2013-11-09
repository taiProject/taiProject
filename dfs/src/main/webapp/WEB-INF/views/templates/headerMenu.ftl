<div class="mainMenu">
	<a href="<@spring.url '/index'/>">
		<div class="menuButton">
			<@spring.message 'homePage' />
		</div>
	</a>
	<a href="<@spring.url '/filesList'/>">
		<div class="menuButton">
			<@spring.message 'filesList' />
		</div>
	</a>
	<#if isAdmin>
		<a href="<@spring.url '/userManagement'/>">
			<div class="menuButton">
				<@spring.message 'userManagement' />
			</div>
		</a>
	</#if>
	<a href="<@spring.url '/userInfo'/>">
		<div class="menuButton">
			<@spring.message 'account' />
		</div>
	</a>
	<a href="<@spring.url '/j_spring_security_logout'/>">
		<div class="menuButton">
			<@spring.message 'logout' />
		</div>
	</a>
</div>
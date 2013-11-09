<div class="mainMenu">
	<ul>
		<li>
			<a href="<@spring.url '/index'/>"><@spring.message 'homePage' /></a>
		</li>
		<li>
			<a href="<@spring.url '/filesList'/>"><@spring.message 'filesList' /></a>
		</li>
		<li>
			${username}
		</li>
		<li>
			<a href="<@spring.url '/j_spring_security_logout'/>"><@spring.message 'logout' /></a>
		</li>
	</ul>
</div>
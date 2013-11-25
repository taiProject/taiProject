<#import "templates/spring.ftl" as spring />
<#import "templates/macros.ftl" as m />

<@m.header>
</@m.header>
<@m.body>
	<@m.mainMenu />
	
	<div class="pageTitle fontStyle">
		<@spring.message 'main.page.title' />
	</div>
	
	<div class="contentDiv">
	</div>
</@m.body>
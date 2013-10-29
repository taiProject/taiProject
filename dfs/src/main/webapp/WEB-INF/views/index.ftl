<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />
<div id="pageTitle">
	<@spring.message 'main.page.title' />
</div>

<#list msgs as m>
	${m}
	<br/>
</#list>

<#include "templates/footer.ftl">
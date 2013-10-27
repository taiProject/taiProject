<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />
<h2>
	<@spring.message 'main.page.title' />
</h2>

<#list msgs as m>
${m}
<br/>
</#list>

<#include "templates/footer.ftl">
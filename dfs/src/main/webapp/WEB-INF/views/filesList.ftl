<#include "templates/header.ftl">
<#import "templates/spring.ftl" as spring />
<div id="pageTitle">
	<@spring.message 'files.list.title' />
</div>

${username}
<br/>
<a href="<@spring.url '/j_spring_security_logout'/>"><@spring.message 'logout' /></a>
<#include "templates/footer.ftl">
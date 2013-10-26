<#include "header.ftl">

<h2 id="title">FTL: ${msg}</h2>

<#list msgs as m>
${m}
<br/>
</#list>

<#include "footer.ftl">
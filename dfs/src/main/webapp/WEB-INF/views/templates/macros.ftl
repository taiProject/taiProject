<#import "spring.ftl" as spring />

<#macro button name url isGeneral>
<div>
	<a href="<@spring.url url/>">
		<div class="tableButton <#if isGeneral>button</#if>">
			<@spring.message name/>
		</div>
	</a>
</div>
</#macro>
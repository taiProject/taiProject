<#import "templates/spring.ftl" as spring />
<#import "templates/macros.ftl" as m />

<@m.header>
	<link href="<@spring.url '/resources/styles/dragAndDrop.css'/>" rel="stylesheet" type="text/css">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
</@m.header>
<@m.body>
	<@m.mainMenu />
	
	<div class="pageTitle fontStyle">
		<@spring.message 'files.list.title' />
	</div>
	
	<div class="contentDiv">
		<#if isAdmin>
			<div class="generalButtons">
				<@m.button 'files.file.delete.all' '/deleteAll' true/>
				<@m.button 'files.file.new' '/newFile' true/>
			</div>
		</#if>
		
		<div id="fileTable">
			<@m.fileTable />
		</div>
		
		<#if isAdmin>
			<div class="generalButtons">
	            </br>
				<@m.button 'files.file.delete.all' '/deleteAll' true/>
	
				<@m.button 'files.file.new' '/newFile' true/>
			</div>
			<br>
			<div id="dragandrophandler">
				<@spring.message 'files.quick.upload'/>
			</div>
		</#if>
	</div>
	
	
	<script>
		function sendFileToServer(formData) {
		    var uploadURL ="<@spring.url '/quickUploadFile'/>";
		    $.ajax({
		    	url: uploadURL,
		    	type: "POST",
		    	contentType: false,
		    	processData: false,
				cache: false,
				data: formData,
				success: function(data){
					$("#fileTable").html(data);
				}
		    }); 
		}
		 
		var rowCount=0;
		function handleFileUpload(files,obj) {
			var fd = new FormData();
			for (var i = 0; i < files.length; i++) {
				fd.append('file'+i, files[i]);
			}
			sendFileToServer(fd);
		}
		
		$(document).ready(function() {
			var obj = $("#dragandrophandler");
			
			obj.on('dragenter', function (e) {
			    e.stopPropagation();
			    e.preventDefault();
			    $(this).css('border', '2px solid #0B85A1');
			});
		
			obj.on('dragover', function (e) {
			     e.stopPropagation();
			     e.preventDefault();
			});
			
			obj.on('drop', function (e) {
			     $(this).css('border', '2px dotted #0B85A1');
			     e.preventDefault();
			     var files = e.originalEvent.dataTransfer.files;
			 
			     //We need to send dropped files to Server
			     handleFileUpload(files,obj);
			});
			
			$(document).on('dragenter', function (e) {
			    e.stopPropagation();
			    e.preventDefault();
			});
			
			$(document).on('dragover', function (e) {
			  e.stopPropagation();
			  e.preventDefault();
			  obj.css('border', '2px dotted #0B85A1');
			});
			
			$(document).on('drop', function (e) {
			    e.stopPropagation();
			    e.preventDefault();
			});
		 
		});
	</script>
</@m.body>
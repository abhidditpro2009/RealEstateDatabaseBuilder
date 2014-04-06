<!DOCTYPE HTML>
<html>
<head>
	<meta name="layout" content="main"/>
    <r:require modules="bootstrap"/>			
</head>
    
<body>
	<g:render template="header" />
	<div>
		<h1 id="address">${query}</h1>
	</div>
	<div><g:render template="topProperties"/></div>
	<div class="container">
    	<div class="row">
        	<div class="col-md-8">
        		<!--Sidebar content-->
        		<g:render template="properties"/>	
        	</div>
        	<div class="col-md-4">
       	 		<!--Body content-->
       	    	<g:render template="map" />
       	    </div>
   		</div>
	</div>
</body>
</html>
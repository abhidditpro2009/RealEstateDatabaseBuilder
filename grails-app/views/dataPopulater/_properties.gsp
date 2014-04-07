<table class="table table-striped table-bordered table-condensed">
<g:each var="property" in="${properties}">
	<tr>
    	<td>${property.name}</td>
    	<td>${property.address}</td>
    	<td>${property.description}</td>
    	<td>${property.price}</td>
    </tr>
</g:each>
</table>
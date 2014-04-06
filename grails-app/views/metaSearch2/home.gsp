
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Welcome to Real Estate Meta Search Engine</title>
</head>

<body>
<h1 align="center">Welcome to Real Estate Meta Search Engine</h1>

<!-- FORM SECTION -->
<form name="form1" method="post" action="">
    <p>
	
	<p align="center"><img src="${createLinkTo(dir: 'images', file: 'search.jpg')}"  width="385" height="316"></p>
	
    <div align="center">
      <p> Street Address: <input type="text" name="street" required="required" >
      <p> City: <input type="text" name="city" required="required" >
      <p> State: <input type="text" name="State" required="required" >
    </div>
</p>
    
	   
    <p align="center">
        <input type="submit" name="event" id="submitQuery" value="Search" width="300px">
        &nbsp;&nbsp;&nbsp;&nbsp;
    </p>
</form>
<!-- END FORM SECTION -->

</body>
</html>
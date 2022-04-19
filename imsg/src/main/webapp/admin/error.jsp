<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="./images/applogo.png"
	type="image/x-icon">
</head>
<body>
	<%
	if (exception.getMessage().equals("")) {
	%>
	<div style="text-align:center; color:red;">
		<h1>404</h1>
		<p>resource not found</p>
	</div>

	<%
	}
	%>
</body>
</html>
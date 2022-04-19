<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="../images/applogo.png"
	type="image/x-icon">
<link rel="stylesheet" href="./component/css/index.css">
<title>Admin </title>
</head>
<body class="content">
	<%
	if (session.getAttribute("user") != null) {		
		response.sendRedirect("./");
		return;
	}
	%>
	<%@include file="./component/pages/message.jsp" %>
	<section class="login">
		<h3 class="title">Admin Login</h3>
		<form action="login" method="POST" id="login">
			<fieldset>
				<legend>Username</legend>
				<input type="text" name="username" id="userid">
			</fieldset>
			<fieldset>
				<legend>Password</legend>
				<input type="password" name="password" id="password">
			</fieldset>

			<button type="button" class="loginbutton" onclick="login()">Login</button>
		</form>
	</section>

	<script src="./component/js/login.js"></script>
	
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Client Login...</title>
<link rel="stylesheet" href="../admin/component/css/index.css">
</head>
<body>
	<%
	if (session.getAttribute("clientuser") != null) {		
		response.sendRedirect("./client/index.jsp");
		return;
	}
	%>
	<%@include file="../admin/component/pages/message.jsp" %>
	<section class="login">
		<h3 class="title">Client Login</h3>
		<form action="./login" method="POST" id="login">
			<fieldset>
				<legend>Username</legend>
				<input type="text" name="username" id="userid" required>
			</fieldset>
			<fieldset>
				<legend>Password</legend>
				<input type="password" name="password" id="password" required>
			</fieldset>
			<div class="usertype"><div><input type="radio" value="student" name="usertype" checked> &nbsp Student</div> <div><input type="radio" value="faculty" name="usertype"> &nbsp Faculty</div></div>
			<button type="submit" class="loginbutton">Login</button>
		</form>
	</section>
	
</body>
</html>
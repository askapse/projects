<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
if (session.getAttribute("student") == null) {
	if (session.getAttribute("faculty") == null) {
		response.sendRedirect("./login.jsp");
		return;
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/nav.css">
</head>
<body>
	<nav class="navbar">
		<ul>
			<%
			if (session.getAttribute("faculty") != null) {
			%>
			<li><a href="./faculty.jsp">Home</a></li>
			<%
			}
			%>
			<li><a href="./logout">Logout</a></li>
		</ul>
	</nav>
</body>
</html>
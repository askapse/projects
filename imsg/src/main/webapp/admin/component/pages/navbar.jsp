<%@page import="admin.entities.InClass"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="./component/css/navbar.css" rel="stylesheet">
</head>
<body>
	<%
	if (session.getAttribute("user") == null) {
		session.setAttribute("message", "Please login ...");
		response.sendRedirect("./login.jsp");
		return;
	}
	%>
	<section class="navbar">
		<p class="username">
			<%
			out.print(session.getAttribute("user"));
			%>
			logged in ...
		</p>
		<div class="allmenu">
			<ul class="menu">
				<li><a href="./">Home</a></li>
				<li><a href="faculty.jsp">Faculty</a> <%
 if (session.getAttribute("class") != null) {
 %>
				<li><a href="addstudent.jsp">Add Student</a></li>
				<li><a
					href="class.jsp?id=<%=((InClass) session.getAttribute("class")).getId()%>">Class</a></li>

				<%
				}
				if (session.getAttribute("user") != null) {
				%>
				<li><a href="logout">Logout</a></li>
				<%
				}
				%>
			</ul>
		</div>
	</section>
</body>
</html>
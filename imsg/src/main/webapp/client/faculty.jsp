<%@page import="admin.entities.InClass"%>
<%@page import="admin.dao.Database"%>
<%@page import="admin.entities.Faculty"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%
Faculty fc = (Faculty) session.getAttribute("faculty");
if (fc == null) {
	session.setAttribute("message", "Login please...");
	response.sendRedirect("./login.jsp");
	return;
}
%>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="./css/css.css">
<title><%=fc.getName()%></title>
</head>
<body>
	<div class="navcn"><%@include file="./nav.jsp"%></div>
	<section class="main f">
		<%
		for (int cid : Database.cls.getClasses(fc.getId())) {
		%>
		<div class="cls">
			<a href="./?id=<%=cid%>"><%=Database.cls.getClass(cid).getName()%></a>			
		</div>
		<%
		}
		%>
	</section>
</body>
</html>
<%@page import="admin.entities.InClass"%>
<%@page import="admin.entities.Faculty"%>
<%@page import="admin.dao.Database"%>
<%@page import="admin.entities.Student"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%
Student s = (Student) session.getAttribute("student");
Faculty f = (Faculty) session.getAttribute("faculty");
if (s == null) {
	if (f == null) {
		session.setAttribute("message", "You have login first...");
		response.sendRedirect("./login.jsp");
		return;
	}
}
%>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%
InClass cls = null;
if (f != null) {
	cls = Database.cls.getClass(Integer.parseInt(request.getParameter("id")));
	session.removeAttribute("class");
	session.setAttribute("class", cls);
}
%>
<title><%=s != null ? s.getName() + "  " + Database.cls.getClass(s.getClassid()).getName()
		: f.getName() + " " + cls.getName()%></title>
<link rel="stylesheet" href="./css/css.css">

<script src="../admin/component/js/JQuery.js"></script>
</head>
<body>
	<div class="navcn"><%@include file="./nav.jsp"%></div>

	<section class="main">

		<div class="messages" id="maincn">
			<div class="loader">
				<button onclick="loadold()">Load More</button>
				<input id="lsmsg" value="0" hidden>
			</div>			
						

		</div>
		<div class="sendmsg">
			<input type="text" id="msgsend">
			<button onclick="send()">Send</button>
		</div>

	</section>

	<script type="text/javascript" src="./js/index.js"></script>
</body>
</html>
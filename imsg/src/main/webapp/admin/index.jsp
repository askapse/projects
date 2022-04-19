<%@page import="admin.entities.InClass"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"
	import="java.util.*,admin.dao.Database"%>
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
<link rel="stylesheet" href="./component/css/home.css">

</head>
<body>
	<%
	if (session.getAttribute("user") == null) {
		session.setAttribute("message", "Please login ...");
		response.sendRedirect("./login.jsp");
		return;
	}

	//remove session class attributes
	session.removeAttribute("class");
	session.removeAttribute("stdid");
	session.removeAttribute("fid");
	
	%>
	<%@include file="./component/pages/navbar.jsp"%>
	<%@include file="./component/pages/message.jsp" %>
	<table class="classes">
		<tr class="row">
			<td class="cls" onclick="addclass()">
				<div class="vcol col"></div>
				<div class="hcol col"></div>
			</td>
			<%
			Iterator itr = Database.cls.getClasses().iterator();
			if (itr.hasNext()) {
				InClass cl = (InClass) itr.next();
			%>

			<td class="cls">
				<h3 class="h3">
					<a href="class.jsp?id=<%=cl.getId()%>"><%=cl.getName() + " &nbsp&nbsp  " + cl.getBatch()%>
						<%
						}
						%></a>
				</h3>
			</td>
		</tr>
		<%
		int tr = 0;
		while (itr.hasNext()) {
			InClass cl = (InClass) itr.next();
			if (tr == 0) {
				out.print("<tr class=\"row\">");
			}
			if (tr == 2) {
				out.print("</tr><tr class=\"row\">");
				tr = 0;
			}
		%>
		<td class="cls">
			<h3 class="h3">
				<a href="class.jsp?id=<%=cl.getId()%>"><%=cl.getName() + "\n" + cl.getBatch()%></a>
			</h3>
		</td>
		<%
		if (!itr.hasNext()) {
			out.print("<td class=\"\"></td>");
		}
		tr++;
		}
		%>
		</tr>
	</table>


	<!-- add new class model -->
	<div class="layer" id="addclass" hidden="true">
		<section class="addclass">
			<div class="addclasstitle">
				<div class="closer cl">
					<div>X</div>
				</div>
			</div>
			<form action="addclass" method="POST" class="form" id="addcls">
				<br> <br>
				<fieldset>
					<legend>Class Name</legend>
					<input type="text" name="classname" required="required">
				</fieldset>
				<br>
				<fieldset>
					<legend>Batch</legend>
					<input type="text" name="batch" required="required"
						placeholder="0000-00 format">
				</fieldset>

				<div class="btns">
					<button type="submit">Add Class</button>
					<button class="cl" type="button">close</button>
				</div>
			</form>
		</section>
	</div>

	<script type="text/javascript" src="./component/js/home.js"></script>
</body>
</html>
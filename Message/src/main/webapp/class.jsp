<%@page import="java.util.Set"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.Map"%>
<%@page import="entities.Student"%>
<%@page import="java.util.TreeMap"%>
<%@page import="dao.Database"%>
<%@page import="entities.InClass"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%
if (session.getAttribute("user") == null) {
	session.setAttribute("message", "Please login ....");
	response.sendRedirect("./");
	return;
}
InClass incl = null;
try {
	int id = Integer.parseInt(request.getParameter("id"));
	incl = Database.cls.getClass(id);
} catch (NumberFormatException e) {
	throw new Exception("");
}

if (incl == null) {
	response.sendRedirect("./home.jsp");
} else {
	//add class to the session
	session.setAttribute("class", incl);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><%=((InClass) session.getAttribute("class")).getName() + " "
		+ ((InClass) session.getAttribute("class")).getBatch()%></title>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="./images/applogo.png"
	type="image/x-icon">
<link rel="stylesheet" href="./component/css/class.css">

</head>
<body>
	<%
	if (session.getAttribute("user") == null) {
		if (incl == null) {
			response.sendRedirect("./home.jsp");
			return;
		} else {
			session.setAttribute("message", "Please login ....");
			response.sendRedirect("./");
			return;
		}
	}
	session.removeAttribute("stdid");
	%>
	<%@include file="./component/pages/navbar.jsp"%>

	<!-- message display here -->
	<%@include file="./component/pages/message.jsp"%>

	<!-- student list in the class -->
	<%
	TreeMap<Integer, Student> students = Database.st.getClassStudents(incl.getId());
	if (students.size() != 0) {
	%>
	<!-- if student found  -->
	<section class="studs">
		<table class="sttable">
			<tr>
				<th>Roll</th>
				<th>Name</th>
				<th>Mobile</th>
				<th>&nbsp</th>
				<th>E-mail</th>
				<th>Password</th>
			</tr>
			<%
			for (Integer key : students.keySet()) {
				Student st = students.get(key);
			%>
			<tr>
				<td><%=st.getRoll()%></td>
				<td><%=st.getName()%></td>
				<td><%=st.getMobile()%></td>
				<td><a href="viewupdatestudent?id=<%=st.getId()%>">view</a></td>
				<td><%=st.getEmail()%></td>
				<td><%=st.getPass()%></td>
			</tr>
			<%
			}
			%>
		</table>
	</section>
	<%
	System.gc();
	}
	if (students.size() == 0) {
	%>

	<!-- if any student not found -->
	<section class="studsnf">
		<h2>There is no student added...</h2>
	</section>
	<%
	}

	students.clear();
	students = null;
	System.gc();
	%>

	<div class="printbtn">
		<button>Print</button>
		<button>Remove</button>
		<script type="text/javascript">
		//print stident list
		document.getElementsByTagName("button")[0].onclick = () => {
			window.print();
		}
		document.getElementsByTagName("button")[1].onclick = () => {
			if(window.confirm("You want to remove class")){
				window.location.replace("./remove");
			}
		}
		</script>
	</div>

</body>
</html>
<%@page import="java.util.List"%>
<%@page import="dao.Database"%>
<%@page import="entities.Faculty"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%
if (session.getAttribute("user") == null) {
	session.setAttribute("message", "Login first...");
	response.sendRedirect("./");
	return;
}
session.removeAttribute("class");
session.removeAttribute("stdid");
session.removeAttribute("fid");
%>


<meta charset="ISO-8859-1">
<title>Faculty</title>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="./images/applogo.png"
	type="image/x-icon">
<link rel="stylesheet" href="./component/css/addfclt.css">

</head>
<body>
	<%@include file="./component/pages/navbar.jsp"%>
	<%@include file="./component/pages/message.jsp"%>
	<%
		List<Faculty> facs = Database.fac.getFaculties();
	if (facs.size() != 0) {
	%>
	<!-- if student found  -->
	<section class="studs">
		<table class="sttable">
			<tr>				
				<th>Name</th>
				<th>Mobile</th>				
				<th>E-mail</th>
				<th>&nbsp</th>
				<th>Password</th>
			</tr>
			<%
			for (Faculty st : facs) {		
			%>
			<tr>				
				<td><%=st.getName()%></td>
				<td><%=st.getMobile()%></td>				
				<td><%=st.getEmail()%></td>
				<td><a href="viewupdatefaculty?id=<%=st.getId()%>">view</a></td>
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
	if (facs.size() == 0) {
	%>

	<!-- if any student not found -->
	<section class="studsnf">
		<h2>There is no faculty added...</h2>
	</section>
	<%
	}

	facs.clear();
	facs = null;
	System.gc();
	%>

	<div class="printbtn">
		<button>Print</button>	
		<button>Add Faculty</button>	
	</div>
	<script type="text/javascript">
	document.getElementsByTagName("button")[0].onclick = () => {
		window.print();
	}
	document.getElementsByTagName("button")[1].onclick = () => {
		window.location.replace("./addfaculty.jsp");
	}
	</script>
	
	
	
	<div class="btns"></div>
</body>
</html>
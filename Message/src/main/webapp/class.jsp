<%@page import="entities.Subject"%>
<%@page import="entities.Faculty"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.TreeSet"%>
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
	session.removeAttribute("fid");
	%>
	<%@include file="./component/pages/navbar.jsp"%>

	<!-- message display here -->
	<%@include file="./component/pages/message.jsp"%>

	<!-- student list in the class -->
	<%
	TreeSet<Student> students = Database.st.getClassStudents(incl.getId());
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
			for (Student st : students) {
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


	<!-- add new class model -->
	<div class="layer" id="addclass" hidden="true">
		<section class="addclass">
			<div class="addclasstitle">
				<div class="closer cl">
					<div>X</div>
				</div>
			</div>
			<form action="updateclass" method="POST" class="form" id="addcls">
				<br> <br>
				<fieldset>
					<legend>Class Name</legend>
					<input type="text" name="classname" required="required"
						value="<%=incl.getName()%>">
				</fieldset>
				<br>
				<fieldset>
					<legend>Batch</legend>
					<input type="text" name="batch" required="required"
						placeholder="0000-00 format" value="<%=incl.getBatch()%>">
				</fieldset>

				<div class="btns">
					<button type="submit">Update</button>
					<button class="cl" type="button">close</button>
				</div>
			</form>
		</section>
	</div>

	<div class="printbtn">
		<button>Print</button>
		<button>Remove</button>
		<button>Update</button>
		<button style="font-size: 12px;">Add Subject</button>
	</div>
	<script type="text/javascript" src="./component/js/home.js"></script>


	<!-- model to add subjects to the class -->
	<div class="layer" id="addsubject" hidden="true">
		<section class="addclass">
			<div class="addclasstitle">
				<div class="closer cl">
					<div>X</div>
				</div>
			</div>
			<form action="removesubject" method="POST" class="form" id="addsubs">
				<br> <br>
				<fieldset>
					<legend>Subject Name</legend>
					<select name="sid" id="subject" required="required"
						onchange="changesub()">
						<option value="-2">Select subject</option>
						<%
						for (Subject s : Database.cls.getSubjects(incl.getId())) {
						%>
						<option value="<%=s.getId()%>"><%=s.getName()%></option>
						<%
						}
						%>
						<option value="-1">Other</option>
					</select> 
					<input type="text" id="subname" name="subname" required="required"
						placeholder="Enter subject name" hidden="true">

				</fieldset>
				<br>
				<fieldset hidden="true" id="facs">
					<legend>Faculty</legend>
					<select name="faculty" required="required" id="fcids">
						<%
						for (Faculty f : Database.fac.getFaculties()) {
						%>
						<option value="<%=f.getId()%>"><%=f.getName()%></option>
						<%
						}
						%>
					</select>
				</fieldset>

				<div class="btns">
					<button type="submit" id="sbtn" hidden="true">Remove</button>
					<button class="cl" type="button">close</button>
				</div>
			</form>
		</section>
	</div>






</body>
</html>
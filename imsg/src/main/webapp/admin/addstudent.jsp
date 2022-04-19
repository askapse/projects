<%@page import="java.time.format.DateTimeFormatterBuilder"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="admin.entities.Student"%>
<%@page import="admin.entities.InClass"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<%
if (session.getAttribute("class") == null) {
	session.setAttribute("message", "Class not found ...");
	response.sendRedirect("./");
	return;
}
%>
<!-- <meta charset="ISO-8859-1"> -->
<meta charset="UTF-8">
<title><%=((InClass) session.getAttribute("class")).getName() + " "
		+ ((InClass) session.getAttribute("class")).getBatch()%></title>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="./images/applogo.png"
	type="image/x-icon">
<link rel="stylesheet" href="./component/css/adst.css">
</head>
<body>

	<%@include file="./component/pages/navbar.jsp"%>
	<%@include file="./component/pages/message.jsp"%>
	<%
	Student stud = (Student) session.getAttribute("student");
	session.removeAttribute("student");
	%>
	<section class="adstdsec">
		<form action="<%if((session.getAttribute("update")!=null) && (stud != null))out.print("updatestudent");else out.print("addstudent"); %>" method="post">
			<fieldset>
				<legend>Name</legend>
				<input type="text" name="name" placeholder="Name of the student"
					<%if (stud != null) {
	out.print("value=\"" + stud.getName()+"\"");
}%>
					required />
			</fieldset>

			<!-- dob and gender in the same row -->
			<div class="row">
				<fieldset>
					<legend>DOB</legend>
					<input type="date" name="dob" placeholder="dd-mm-yyyy" required
						<%if (stud != null) {
	out.print(
			"value=" + stud.getDbo().toString());
}%>>
				</fieldset>
				<fieldset>
					<legend>Gender</legend>
					<select name="gender">
						<option
							<%if (stud != null) {
	if (stud.getGender() == 'M')
		out.print("selected");
}%>>Male</option>
						<option
							<%if (stud != null) {
	if (stud.getGender() == 'F')
		out.print("selected");
}%>>Female</option>
						<option
							<%if (stud != null) {
	if (stud.getGender() == 'O')
		out.print("selected");
}%>>Other</option>
					</select>
				</fieldset>

			</div>

			<!-- row end  -->

			<!-- row -->
			<div class="row">
				<fieldset>
					<legend>Mobile</legend>
					<input type="number" name="mobile" placeholder="000000000" required
						<%if (stud != null) {
	out.print("value=" + stud.getMobile());
}%>>
				</fieldset>
				<fieldset>
					<legend>Roll Number</legend>
					<input type="number" name="roll" placeholder="roll" required
						<%if (stud != null) {
	out.print("value=" + stud.getRoll());
}%>>
				</fieldset>
			</div>
			<!-- end row here -->

			<fieldset>
				<legend>E-mail</legend>
				<input type="email" name="email" placeholder="example@ex.com"
					required
					<%if (stud != null) {
	out.print("value=" + stud.getEmail());
}%>>
			</fieldset>

			<fieldset class="pas">
				<legend>Password</legend>
				<input type="password" name="password"
					<%if (stud != null) {
	out.print("value=" + stud.getPass());
}%>
					placeholder="digit lowercade uppercase special char" required>
				<input type="checkbox" id="showp">
			</fieldset>

			<fieldset>
				<legend>Address</legend>
				<textarea name="address" required><%if (stud != null) {out.print(stud.getAddress());}%></textarea>

			</fieldset>
			<div class="btns">
			<button type="submit"><%if(session.getAttribute("update") != null && stud != null)out.print("Update");else out.print("Submit");%></button>
			<%
			if(session.getAttribute("update") != null && stud != null){
				out.print("<button type=\"button\">Remove</button>"); 
				
			%>
				<script type="text/javascript">
		//remove student
		document.getElementsByTagName("button")[1].onclick = () => {
			if(window.confirm("You want to remove this student")){
				window.location.replace("./remove");
			}
		}
		</script>
			<%
				}
			%>
		</div>
		</form>
	</section>
	<script type="text/javascript">
		document.getElementById("showp").onchange=()=>{	
				let ps = document.getElementsByName("password");
				if(ps[0].getAttribute("type")=="text"){
					ps[0].setAttribute("type","password");
				}else{
					ps[0].setAttribute("type","text");
				}			
		}
	</script>
</body>
</html>
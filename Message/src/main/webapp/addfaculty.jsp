<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="entities.Faculty"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="./images/applogo.png"
	type="image/x-icon">
<link rel="stylesheet" href="./component/css/adst.css">
<title>Add Faculty</title>
</head>
<body>
	<%@include file="./component/pages/navbar.jsp"%>
	<%@include file="./component/pages/message.jsp"%>
	<%
	Faculty faculty = (Faculty) request.getAttribute("faculty");
	request.removeAttribute("faculty");
	session.removeAttribute("class");
	session.removeAttribute("stdid");
	if(faculty != null) session.setAttribute("fid",faculty.getId());
	%>
	<section class="adstdsec">
		<form
			action="<%if ((faculty != null))
	out.print("updatefaculty");
else
	out.print("addfaculty");%>"
			method="post">
			<fieldset>
				<legend>Name</legend>
				<input type="text" name="name" placeholder="Name of the faculty"
					<%if (faculty != null) {
	out.print("value=\"" + faculty.getName() + "\"");
}%>
					required />
			</fieldset>

			<div class="row">
				<fieldset>
					<legend>Gender</legend>
					<select name="gender">
						<option
							<%if (faculty != null) {
	if (faculty.getGender() == 'M')
		out.print("selected");
}%>>Male</option>
						<option
							<%if (faculty != null) {
	if (faculty.getGender() == 'F')
		out.print("selected");
}%>>Female</option>
						<option
							<%if (faculty != null) {
	if (faculty.getGender() == 'O')
		out.print("selected");
}%>>Other</option>
					</select>
				</fieldset>


				<fieldset>
					<legend>Mobile</legend>
					<input type="number" name="mobile" placeholder="000000000" required
						<%if (faculty != null) {
	out.print("value=" + faculty.getMobile());
}%>>
				</fieldset>

			</div>
			<!-- end row here -->

			<fieldset>
				<legend>E-mail</legend>
				<input type="email" name="email" placeholder="example@ex.com"
					required
					<%if (faculty != null) {
	out.print("value=" + faculty.getEmail());
}%>>
			</fieldset>

			<fieldset class="pas">
				<legend>Password</legend>
				<input type="password" name="password"
					<%if (faculty != null) {
	out.print("value=" + faculty.getPass());
}%>
					placeholder="digit lowercade uppercase special char" required>
				<input type="checkbox" id="showp">
			</fieldset>

			<fieldset>
				<legend>Address</legend>
				<textarea name="address" required><%
					if (faculty != null) {
						out.print(faculty.getAddress());
					}
					%></textarea>

			</fieldset>
			<div class="btns">
				<button type="submit">
					<%
					if (faculty != null)
						out.print("Update");
					else
						out.print("Submit");
					%>
				</button>
				<%
				if (faculty != null) {
					out.print("<button type=\"button\">Remove</button>");
				%>
				<script type="text/javascript">
		//remove faculty
		document.getElementsByTagName("button")[1].onclick = () => {
			if(window.confirm("You want to remove this faculty")){
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
	<%
	/* session.setAttribute("fid",faculty.getId()); */
	%>
</body>
</html>
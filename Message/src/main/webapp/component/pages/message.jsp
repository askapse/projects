<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
String massage = (String) session.getAttribute("message");
if (massage != null) {
%>
<div
	style="width: 500px; height: 30px; background: lime; display: flex; margin: 0 auto;"
	id="message">

	<div
		style="width: 95%; color: red; line-height: 30px; padding: 0px 10px">
		<%=massage%>

	</div>
	<div style="width: 5%; font-size: 20px; cursor: pointer;" id="closemsg">&times</div>

	<script type="text/javascript">
		document.getElementById("closemsg").onclick=()=>{
			document.getElementById("message").remove();
		}
	</script>
</div>

<%
}
session.removeAttribute("message");
%>
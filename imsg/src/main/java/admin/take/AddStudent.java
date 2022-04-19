package admin.take;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import admin.dao.Database;
import admin.entities.InClass;
import admin.entities.Student;

@WebServlet("/admin/addstudent")
public class AddStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddStudent() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doPost(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			session.setAttribute("message", "Please login...");
			response.sendRedirect("./login.jsp");
		}

		Student st = new Student();

		st.setRoll(Integer.parseInt(request.getParameter("roll")));
		st.setGender(request.getParameter("gender").charAt(0));
		st.setName(request.getParameter("name"));
		st.setClassid(((InClass)session.getAttribute("class")).getId());
		st.setDbo(LocalDate.parse(request.getParameter("dob")));
		st.setEmail(request.getParameter("email"));
		st.setPass(request.getParameter("password"));
		st.setMobile(request.getParameter("mobile"));
		st.setAddress(request.getParameter("address"));
		
		if(Database.st.checkRoll(st.getRoll(),((InClass)session.getAttribute("class")).getId())) {
			session.setAttribute("student", st);
			session.removeAttribute("update");
			session.setAttribute("message", "Given roll number previously added...");
			response.sendRedirect("./addstudent.jsp");
			return;
		}
			
		if(Database.st.addStudent(st)) {
			session.setAttribute("message", "Student added successfully...");
			response.sendRedirect("./addstudent.jsp");
			return;
		}else {
			session.setAttribute("message", "Failed to add student...");
			response.sendRedirect("./addstudent.jsp");
			return;
		}

//		doGet(request, response);
	}

}

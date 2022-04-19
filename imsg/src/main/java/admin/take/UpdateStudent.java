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

@WebServlet("/admin/updatestudent")
public class UpdateStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateStudent() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		InClass cls = (InClass) session.getAttribute("class");
		if (cls == null) {
			session.setAttribute("message", "Class not found...");
			response.sendRedirect("./");
			return;
		}

		if (session.getAttribute("stdid") == null) {
			session.setAttribute("message", "Student id not found...");
			response.sendRedirect("./class.jsp?id="+cls.getId());
			return;
		}

		Student st = new Student();
		st.setId(Integer.parseInt(session.getAttribute("stdid").toString()));
		session.removeAttribute("stdid");
		st.setRoll(Integer.parseInt(request.getParameter("roll")));
		st.setGender(request.getParameter("gender").charAt(0));
		st.setName(request.getParameter("name"));
		st.setDbo(LocalDate.parse(request.getParameter("dob")));
		st.setEmail(request.getParameter("email"));
		st.setPass(request.getParameter("password"));
		st.setMobile(request.getParameter("mobile"));
		st.setAddress(request.getParameter("address"));
		st.setClassid(cls.getId());

		if(Database.st.checkRoll(st.getRoll(), cls.getId(),st.getId())) {
			session.setAttribute("message", "Given roll number previously added...");
			session.setAttribute("student", st);
			response.sendRedirect("viewupdatestudent?id="+st.getId());
			return;
		}
		
		
		if (Database.st.addStudent(st)) {
			session.setAttribute("message", "Student updated successfully...");
			response.sendRedirect("class.jsp?id="+cls.getId());
			return;
		} else {
			session.setAttribute("message", "Failed student updation...");
			response.sendRedirect("class.jsp?id="+cls.getId());
			return;
		}
//		doGet(request, response);
	}

}

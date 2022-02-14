package take;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Database;
import entities.InClass;
import entities.Student;

/**
 * Servlet implementation class UpdateStudent
 */
@WebServlet("/updatestudent")
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
			response.sendRedirect("home.jsp");
			return;
		}

		if (session.getAttribute("stdid") == null) {
			session.setAttribute("message", "Student id not found...");
			response.sendRedirect("home.jsp");
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

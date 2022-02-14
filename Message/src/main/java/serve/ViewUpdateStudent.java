package serve;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class ViewUpdateStudent
 */
@WebServlet("/viewupdatestudent")
public class ViewUpdateStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewUpdateStudent() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
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

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			if(id == 0) throw new NumberFormatException();
		} catch (NumberFormatException e) {
			response.sendRedirect("home.jsp");
			return;
		}

		Student st = Database.st.getStudent(id, cls.getId());

		if (st == null) {
			session.setAttribute("message", "Student not found...");
			response.sendRedirect("home.jsp");
			return;
		}

		session.setAttribute("student", st);
		session.setAttribute("update", "update");
		session.setAttribute("stdid", st.getId());
		RequestDispatcher rd = request.getRequestDispatcher("./addstudent.jsp");
		rd.forward(request, response);
//		doGet(request, response);
	}

}

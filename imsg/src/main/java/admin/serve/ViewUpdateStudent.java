package admin.serve;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import admin.dao.Database;
import admin.entities.InClass;
import admin.entities.Student;

@WebServlet("/admin/viewupdatestudent")
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
			response.sendRedirect("./login.jsp");
			return;
		}

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			if (id == 0)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			response.sendRedirect("./");
			return;
		}

		if (session.getAttribute("student") == null) {
			Student st = Database.st.getStudent(id, cls.getId());

			if (st == null) {
				session.setAttribute("message", "Student not found...");
				response.sendRedirect("./class.jsp?id="+cls.getId());
				return;
			}

			session.setAttribute("student", st);
			session.setAttribute("update", "update");
			session.setAttribute("stdid", st.getId());
		} else {
			session.setAttribute("update", "update");
			session.setAttribute("stdid", ((Student) session.getAttribute("student")).getId());
		}
		RequestDispatcher rd = request.getRequestDispatcher("./addstudent.jsp");
		rd.forward(request, response);
//		doGet(request, response);
	}

}

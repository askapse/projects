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
import admin.entities.Faculty;

@WebServlet("/admin/viewupdatefaculty")
public class ViewUpdateFaculty extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewUpdateFaculty() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			session.setAttribute("message", "Please login ...");
			response.sendRedirect("./login.jsp");
			return;
		}

		Integer fid = null;
		try {
			fid = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			session.setAttribute("message", "Faculty not found ...");
			response.sendRedirect("./faculty.jsp");
			return;
		}

		Faculty f = Database.fac.getFaculty(fid);
		if(f == null) {
			session.setAttribute("message", "Faculty not found...");
			response.sendRedirect("./faculty.jsp");
			return;
		}
		request.setAttribute("faculty", f);
		RequestDispatcher rd = request.getRequestDispatcher("./addfaculty.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}

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
import entities.Faculty;

/**
 * Servlet implementation class ViewUpdateFaculty
 */
@WebServlet("/viewupdatefaculty")
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
			response.sendRedirect("./");
			return;
		}

		Integer fid = null;
		try {
			fid = Integer.parseInt(request.getParameter("id"));
		}catch (NumberFormatException e) {
			session.setAttribute("message", "Faculty not found ...");
			response.sendRedirect("./faculty.jsp");
			return;
		}		

		Faculty f = Database.fac.getFaculty(fid);	
		request.setAttribute("faculty", f);
		RequestDispatcher rd = request.getRequestDispatcher("./addfaculty.jsp");
		rd.forward(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

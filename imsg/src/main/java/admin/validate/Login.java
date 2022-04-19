package admin.validate;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import admin.dao.Database;

@WebServlet("/admin/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.sendRedirect("./login.jsp");
		return;
//		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			if (Database.admin.getAdmin(username, password) == null) {
				session.setAttribute("message", "Given credintials not found");
				response.sendRedirect("./login.jsp");
				return;
			} else {
				session.setAttribute("user", username);
				response.sendRedirect("./index.jsp");
				return;
			}
		}

//		doGet(request, response);
	}

}

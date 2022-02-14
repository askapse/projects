package validate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Database;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			if (Database.admin.getAdmin(username, password) == null) {
				session.setAttribute("message", "Given credintials not found");
				response.sendRedirect("./");
				return;
			} else {
				session.setAttribute("user", username);
				response.sendRedirect("home.jsp");
				return;
			}
		}

//		doGet(request, response);
	}

}

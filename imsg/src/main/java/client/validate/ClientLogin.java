package client.validate;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import admin.entities.Faculty;
import admin.entities.Student;
import client.dao.LoginDao;

@WebServlet("/client/login")
public class ClientLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ClientLogin() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();

		if (username == null || password == null || username.equals("") || password.equals("")) {
			session.setAttribute("message", "fields username or password id empty");
			response.sendRedirect("./login.jsp");
			return;
		}

//		PrintWriter out = response.getWriter();

		if (request.getParameter("usertype").equals("student")) {
			// student login
			Student s;
			if ((s = LoginDao.login.loginStudent(username, password)) != null) {
				session.setAttribute("student", s);
				
				response.sendRedirect("./index.jsp");
			} else {
				session.setAttribute("message", "Wrong student username or password");
				response.sendRedirect("./login.jsp");
				return;
			}
		} else if (request.getParameter("usertype").equals("faculty")) {
			// faculty login
			Faculty f;
			if ((f = LoginDao.login.loginFaculty(username, password)) != null) {
				session.setAttribute("faculty", f);				
				response.sendRedirect("./faculty.jsp");
			} else {
				session.setAttribute("message", "Wrong faculty username or password");
				response.sendRedirect("./login.jsp");
				return;
			}
		} else {
			session.setAttribute("message", "wrong user type...");
			response.sendRedirect("./login.jsp");
			return;
		}

	}
}

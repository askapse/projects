package validate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Database;
import entities.InClass;

@WebServlet("/remove")
public class Remove extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Remove() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		InClass cls = ((InClass) session.getAttribute("class"));
		
		Integer stid = (Integer) session.getAttribute("stdid");
		if (cls == null) {
			session.setAttribute("message", "Class not found...");
			response.sendRedirect("./home.jsp");
			return;
		}

		// remove class from the system
		if (stid == null) {
			if (Database.cls.removeClass(cls.getId())) {
				session.setAttribute("message", cls.getName() + " " + cls.getBatch() + " removed successfully...");
				response.sendRedirect("./home.jsp");
				return;
			} else {
				session.setAttribute("message", "Failed to remove this class...");
				response.sendRedirect("./class.jsp");
				return;
			}
		} else {
			if (Database.st.removeStudent(stid, cls.getId())) {
				session.setAttribute("message", "Student removed successfully...");
				session.removeAttribute("stdid");
				response.sendRedirect("./class.jsp?id="+cls.getId());
				return;
			} else {
				session.setAttribute("message", "Failed to remove student...");
				response.sendRedirect("./viewupdatestudent?id=" + stid);
				return;
			}
		}

//		doGet(request, response);
	}

}

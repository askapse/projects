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
		
		if (session.getAttribute("user") == null) {
			session.setAttribute("message", "Login please...");
			response.sendRedirect("./");
			return;
		}

		InClass cls = ((InClass) session.getAttribute("class"));
		Integer stid = (Integer) session.getAttribute("stdid");
		Integer fid = (Integer) session.getAttribute("fid");
		// remove class from the system

		if (stid != null) {
			if (Database.st.removeStudent(stid, cls.getId())) {
				session.setAttribute("message", "Student removed successfully...");
				session.removeAttribute("stdid");
				response.sendRedirect("./class.jsp?id=" + cls.getId());
				return;
			} else {
				session.setAttribute("message", "Failed to remove student...");
				response.sendRedirect("./viewupdatestudent?id=" + stid);
				return;
			}
		} else if (fid != null) {
			if (Database.fac.removeFaculty(fid)) {
				session.setAttribute("message", "Faculty removed successfully...");
				response.sendRedirect("./faculty.jsp");
				return;
			} else {
				session.setAttribute("message", "Failed to remove faculty...");
				response.sendRedirect("./viewupdatefaculty?id="+fid);
				return;
			}
		} else if(cls != null && session.getAttribute("sub") != null) {
			int id =(Integer) session.getAttribute("sub");
			if(Database.cls.removeSubject(id, cls.getId())) {
				session.setAttribute("message", "Subject removed successfully...");
				session.removeAttribute("sub");
				response.sendRedirect("./class.jsp?id="+cls.getId());
				return;
			}else {
				session.setAttribute("message", "Failed to remove subject...");
				response.sendRedirect("./class.jsp?id="+cls.getId());
				return;
			}
		}else if (cls != null) {
			if (Database.cls.removeClass(cls.getId())) {
				session.setAttribute("message", cls.getName() + " " + cls.getBatch() + " removed successfully...");
				response.sendRedirect("./home.jsp");
				return;
			} else {
				session.setAttribute("message", "Failed to remove this class...");
				response.sendRedirect("./class.jsp?id="+cls.getId());
				return;
			}
		}
//		doGet(request, response);
	}

}

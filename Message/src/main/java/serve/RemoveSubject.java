package serve;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.InClass;


@WebServlet("/removesubject")
public class RemoveSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemoveSubject() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null) {
			session.setAttribute("message", "Login please...");
			response.sendRedirect("./");
			return;
		}
		
		if(session.getAttribute("class") == null) {
			session.setAttribute("message", "Class not found...");
			response.sendRedirect("./home.jsp");
			return;
		}
		
		String id = request.getParameter("sid");
		try {
			session.setAttribute("sub", (Integer)Integer.parseInt(id));
			response.sendRedirect("./remove");
			return;
		}catch (NumberFormatException e) {
			session.setAttribute("message", "Invalid request....");
			response.sendRedirect("./class.jsp?id="+((InClass)session.getAttribute("class")).getId());
			return;
		}		
//		doGet(request, response);
	}

}

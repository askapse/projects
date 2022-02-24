package take;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Database;
import entities.InClass;


@WebServlet("/updateclass")
public class UpdateClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateClass() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("user")==null) {
			session.setAttribute("message",	"You are not logged in,please login ...");
			response.sendRedirect("./");
			return;
		}
		
		if(session.getAttribute("class")==null) {
			session.setAttribute("message",	"Class not found...");
			response.sendRedirect("./home.jsp");
			return;
		}
		
		String name = request.getParameter("classname");
		String batch = request.getParameter("batch");
		
		if(!batch.matches("[0-9]+[-][0-9]+") || name == null) {
			session.setAttribute("message", "Enter correct batch format...");
			response.sendRedirect("home.jsp");
			return;
		}
		
		InClass cls = new InClass();
		cls.setBatch(batch);
		cls.setName(name);
		cls.setId(((InClass)session.getAttribute("class")).getId());
		if(Database.cls.addClass(cls)) {
			session.setAttribute("message", "Class updated successfully...");
			response.sendRedirect("home.jsp");
			return;
		}else {
			session.setAttribute("message", "Failed to update class ,please try again...");
			response.sendRedirect("home.jsp");
			return;
		}
//		doGet(request, response);
	}

}

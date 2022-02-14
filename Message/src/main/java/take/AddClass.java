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


@WebServlet("/addclass")
public class AddClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddClass() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("user")==null) {
			response.sendRedirect("./?error=You are not logged in,please login ...");
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
		if(Database.cls.addClass(cls)) {
			session.setAttribute("message", "Class added successfully...");
			response.sendRedirect("home.jsp");
			return;
		}else {
			session.setAttribute("message", "Failed to add class ,please try again...");
			response.sendRedirect("home.jsp");
			return;
		}
//		doGet(request, response);
	}

}

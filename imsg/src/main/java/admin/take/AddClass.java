package admin.take;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import admin.dao.Database;
import admin.entities.InClass;


@WebServlet("/admin/addclass")
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
			session.setAttribute("message",	"You are not logged in,please login ...");
			response.sendRedirect("./login.jsp");
			return;
		}
		
		String name = request.getParameter("classname");
		String batch = request.getParameter("batch");
		
		if(!batch.matches("[0-9]+[-][0-9]+") || name == null) {
			session.setAttribute("message", "Enter correct batch format...");
			response.sendRedirect("./login.jsp");
			return;
		}
		
		InClass cls = new InClass();
		cls.setBatch(batch);
		cls.setName(name);
		if(Database.cls.addClass(cls)) {
			session.setAttribute("message", "Class added successfully...");
			response.sendRedirect("./");
			return;
		}else {
			session.setAttribute("message", "Failed to add class ,please try again...");
			response.sendRedirect("./");
			return;
		}
//		doGet(request, response);
	}

}

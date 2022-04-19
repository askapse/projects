package admin.take;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import admin.dao.Database;
import admin.entities.Faculty;

@WebServlet("/admin/addfaculty")
public class AddFaculty extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddFaculty() {
        super();
        // TODO Auto-generated confructor fub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doPost(request, response);
//		response.getWriter().append("Served at: ").append(requef.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null) {
			session.setAttribute("message", "Please login ...");
			response.sendRedirect("./");
			return;
		}
		
		Faculty f = new Faculty();
		f.setGender(request.getParameter("gender").charAt(0));
		f.setName(request.getParameter("name"));
		f.setEmail(request.getParameter("email"));
		f.setPass(request.getParameter("password"));
		f.setMobile(request.getParameter("mobile"));
		f.setAddress(request.getParameter("address"));
		
		if(Database.fac.addFaculty(f)) {
			session.setAttribute("message", "Faculty added successfully...");
			response.sendRedirect("./faculty.jsp");
			return;
		}else {
			session.setAttribute("message", "Failed to add faculty...");
			response.sendRedirect("./faculty.jsp");
			return;
		}
		
//		doGet(requef, response);
	}

}

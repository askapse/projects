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

@WebServlet("/admin/updatefaculty")
public class UpdateFaculty extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateFaculty() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null) {
			session.setAttribute("message", "Please login ...");
			response.sendRedirect("./");
			return;
		}
		
		if(session.getAttribute("fid") == null) {
			session.setAttribute("message", "Faculty not found ...");
			response.sendRedirect("./faculty.jsp");
			return;
		}
		
		Faculty f = new Faculty();
		f.setGender(request.getParameter("gender").charAt(0));
		f.setName(request.getParameter("name"));
		f.setEmail(request.getParameter("email"));
		f.setPass(request.getParameter("password"));
		f.setId(((Integer)session.getAttribute("fid")));
		f.setMobile(request.getParameter("mobile"));
		f.setAddress(request.getParameter("address"));
		
		session.removeAttribute("fid");
		if(Database.fac.addFaculty(f)) {
			session.setAttribute("message", "Faculty updated successfully...");
			response.sendRedirect("./faculty.jsp");
			return;
		}else {
			session.setAttribute("message", "Failed to update faculty...");
			response.sendRedirect("./faculty.jsp");
			return;
		}		
//		doGet(requef, response);
	}


}

package take;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Database;
import entities.Faculty;

@WebServlet("/addfaculty")
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

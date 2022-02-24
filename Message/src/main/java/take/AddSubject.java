package take;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.mapping.Subclass;

import dao.Database;
import entities.InClass;
import entities.Subject;

@WebServlet("/addsubject")
public class AddSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public AddSubject() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		InClass incl = (InClass) session.getAttribute("class");
		
		if(session.getAttribute("user") == null || incl == null) {
			session.setAttribute("message", "Class not found...");
			response.sendRedirect("./home.jsp");
			return;
		}
		//variable to store data from user
		String name = null;
		int fid = 0;
		try {
			name = request.getParameter("subname");
			if(name == null) {
				throw new NumberFormatException();
			}
			fid = Integer.parseInt(request.getParameter("faculty"));
		}catch (NumberFormatException e) {
			session.setAttribute("message","Invalid input...");
			response.sendRedirect("class.jsp?id="+incl.getId());
			return;
		}
	
		Subject sub = new Subject();
		sub.setCid(incl.getId());
		sub.setName(name);
		sub.setFid(fid);
		if(Database.cls.addSubject(sub)) {
			session.setAttribute("message", "Subject added successfully....");
			response.sendRedirect("class.jsp?id="+incl.getId());
			return;
		}else {
			session.setAttribute("message", "Failed to add subject....");
			response.sendRedirect("class.jsp?id="+incl.getId());
			return;
		}		
//		doGet(request, response);
	}

}

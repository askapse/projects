package datagrabber;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.CustomerRecords;
import databaselogic.HotelRecords;


@WebServlet("/updateemail")
public class UpdateEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateEmail() {
        super();
        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");
		
		HttpSession session=request.getSession();
		String email=(String)session.getAttribute("email");
		String usertype=(String)session.getAttribute("usertype");
		long id=(long)session.getAttribute("userid");
		if(email == null || usertype == null) {
			session.invalidate();
			return;
		}
		
		if(usertype.equals("hotel")) {
			if(HotelRecords.updateEmail(email, id)) {
				out.print("{\"result\":true}");
			}else {
				out.print("{\"result\":false}");
			}	
			
		}else if(usertype.equals("customer")){
			if(CustomerRecords.updateEmail(email, id)) {
				out.print("{\"result\":true}");
			}else {
				out.print("{\"result\":false}");
			}
		}
		
//		doGet(request, response);
	}

}

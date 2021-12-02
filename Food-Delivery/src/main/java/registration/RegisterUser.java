package registration;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.CustomerUser;
import databaselogic.HotelUser;
import entities.TmpRegDataStorage;

@WebServlet("/registeruser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterUser() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");
		
		HttpSession session=request.getSession();
		
			TmpRegDataStorage regdata=(TmpRegDataStorage)session.getAttribute("userdata");
			
			if(regdata==null) {
				response.sendRedirect("./");
			}
			
		regdata.setFname(request.getParameter("fname"));
		regdata.setLname(request.getParameter("lname"));
		regdata.setDob(request.getParameter("dob"));
		regdata.setHome(request.getParameter("hname"));
		regdata.setArea(request.getParameter("area"));
		regdata.setCity(request.getParameter("city"));
		regdata.setSubdist(request.getParameter("subdist"));
		regdata.setDist(request.getParameter("dist"));
		regdata.setZipcode(request.getParameter("zipcode"));
		regdata.setPass(request.getParameter("pass"));
		
		if(regdata.getUsertype().equals("hotel")) {
			if(HotelUser.addUser(regdata)) {
				out.print("{\"result\":true}");
			}else {
				out.print("{\"result\":false}");
			}
		}else {
			if(CustomerUser.addUser(regdata)) {
				out.print("{\"result\":true}");
			}else {
				out.print("{\"result\":false}");
			}
		}
		
//		doGet(request, response);
	}

}

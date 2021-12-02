package datagrabber;

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

@WebServlet("/updateprofile")
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateProfile() {
        super();    
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");
		
		HttpSession session=request.getSession();
			String usertype=(String)session.getAttribute("usertype");
			long userid=(long)session.getAttribute("userid");
			if(usertype == null) {
				session.invalidate();
				return;
			}
			
			TmpRegDataStorage regdata=new TmpRegDataStorage();
		
			regdata.setFname(request.getParameter("fname"));
			regdata.setLname(request.getParameter("lname"));
			regdata.setDob(request.getParameter("dob"));
			regdata.setHome(request.getParameter("hname"));
			regdata.setArea(request.getParameter("area"));
			regdata.setCity(request.getParameter("city"));
			regdata.setSubdist(request.getParameter("subdist"));
			regdata.setDist(request.getParameter("dist"));
			regdata.setZipcode(request.getParameter("zipcode"));
			regdata.setMobile(request.getParameter("mobile"));
			
			
			if(usertype.equals("hotel")) {
				if(HotelUser.updateUser(regdata,userid)) {
					out.print("{\"result\":true}");
				}else {
					out.print("{\"result\":false}");
				}
			}else if(usertype.equals("customer")){
				if(CustomerUser.updateUser(regdata,userid)) {
					out.print("{\"result\":true}");
				}else {
					out.print("{\"result\":false}");
				}
			}
						
		
//		doGet(request, response);
	}

}

package hotel;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import customer.entities.CustomerEntity;
import databaselogic.CustomerRecords;
import databaselogic.HotelRecords;
import hotel.entities.*;

@WebServlet("/gethotelprofile")
public class GetHotelProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public GetHotelProfile() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();		
			long userid=(long)session.getAttribute("userid");
			String usertype=(String)session.getAttribute("usertype");
			if(!usertype.equals("hotel")) {
				session.invalidate();
				return;
			}
			
			
			PrintWriter out=response.getWriter();
			response.setContentType("application/json");
			if(usertype.equals("hotel")) {	
				HotelEntity hotel=HotelRecords.getHotelEnt(userid);
				if(hotel == null) {
					out.print("{\"result\":false,\"msg\":\"User not found\"}");
				}else {
					out.print("{"
							+ "\"result\":true,"
							+ "\"fname\":\""+hotel.getFname()+"\","
							+ "\"lname\":\""+hotel.getLname()+"\","
							+ "\"dob\":\""+hotel.getDob()+"\","
							+ "\"hotelname\":\""+hotel.getAddress().getHotelname()+"\","
							+ "\"dist\":\""+hotel.getAddress().getDist()+"\","
							+ "\"subdist\":\""+hotel.getAddress().getSubdist()+"\","
							+ "\"city\":\""+hotel.getAddress().getCity()+"\","
							+ "\"area\":\""+hotel.getAddress().getArea()+"\","
							+ "\"zipcode\":\""+hotel.getAddress().getZipcode()+"\","
							+ "\"mobile\":"+hotel.getMobile()+","
							+ "\"email\":\""+hotel.getAuth().getEmail()+"\""
							+ "}");
				}	
			}else {
				out.print("{\"result\":false,\"msg\":\"Request not valid\"}");
			}
			
		
//		doGet(request, response);
	}

}

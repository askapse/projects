package customer;

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


@WebServlet("/getcustomerprofile")
public class GetCustomerProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetCustomerProfile() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();		
		long userid=(long)session.getAttribute("userid");
		String usertype=(String)session.getAttribute("usertype");
		if(!usertype.equals("customer")) {
			session.invalidate();
			return;
		}		
		
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");
		
		if(usertype.equals("customer")) {
			CustomerEntity customer=CustomerRecords.getCustomerEnt(userid);
			if(customer == null) {
				out.print("{\"result\":false,\"msg\":\"User not found\"}");
			}else {
				out.print("{"
						+ "\"result\":true,"
						+ "\"fname\":\""+customer.getFname()+"\","
						+ "\"lname\":\""+customer.getLname()+"\","
						+ "\"dob\":\""+customer.getDob()+"\","
						+ "\"hotelname\":\""+customer.getAddress().getHome()+"\","
						+ "\"dist\":\""+customer.getAddress().getDist()+"\","
						+ "\"subdist\":\""+customer.getAddress().getSubdist()+"\","
						+ "\"city\":\""+customer.getAddress().getCity()+"\","
						+ "\"area\":\""+customer.getAddress().getArea()+"\","
						+ "\"zipcode\":\""+customer.getAddress().getZipcode()+"\","
						+ "\"mobile\":"+customer.getMobile()+","
						+ "\"email\":\""+customer.getAuth().getEmail()+"\""
						+ "}");
			}
		}else {
			out.print("{\"result\":false,\"msg\":\"Request not valid\"}");
		}

		
//		doGet(request, response);
	}

}

package hotel.datagrabber;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databaselogic.CustomerRecords;
import databaselogic.HotelRecords;
import emails.Email;

@WebServlet("/passrecovery")
public class PassRecovery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public PassRecovery() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
			
		response.setContentType("application/json");
		
		String email=request.getParameter("email");
		String user=request.getParameter("usertype");
		
		if(user.equals("hotel")) {
			int test=HotelRecords.checkEmail(email);
			if(test==1){
				String pass=HotelRecords.getPass(email);
				
				if(pass.equals("error")){
					out.print("{\"result\":false,\"msg\":\"server error occures while send password\"}");
					return;
				}
				Email e_mail=new Email();
				
				e_mail.setTo(email);
				e_mail.setSubject("Foodei user password recovery");
				e_mail.setMsg("Password for foodei username \n\n"+email+"\n\n is ...\n\n\n"+pass+"\n\n\n\nType of user is "+user);
				
				if(e_mail.send()) {
					out.print("{\"result\":true}");
				}else {
					out.print("{\"result\":false,\"msg\":\"Error while sending password\"}");
				}
				
			}else if(test==0) {
				out.print("{\"result\":false,\"msg\":\"Given email not registered with us\"}");
			}else if(test==-1) {
				out.print("{\"result\":false,\"msg\":\"Server error to check credentials\"}");
			}
			
		}else {
			int test=CustomerRecords.checkEmail(email);
			if(test==1){
				String pass=CustomerRecords.getPass(email);
				
				if(pass.equals("error")){
					out.print("{\"result\":false,\"msg\":\"server error occures while send password\"}");
					return;
				}
				
				Email e_mail=new Email();
				
				e_mail.setTo(email);
				e_mail.setSubject("Foodei user password recovery");
				e_mail.setMsg("Password for foodei username \n\n"+email+"\n\n is ...\n\n\n"+pass+"\n\n\n\nType of user is "+user);
				
				if(e_mail.send()) {
					out.print("{\"result\":true}");
				}else {
					out.print("{\"result\":false,\"msg\":\"Error while sending password\"}");
				}
				
			}else if(test==0) {
				out.print("{\"result\":false,\"msg\":\"Given email not registered with us\"}");
			}else if(test==-1) {
				out.print("{\"result\":false,\"msg\":\"Server error to check credentials\"}");
			}	
		}
		
		
//		doGet(request, response);
	}

}

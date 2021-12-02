package registration;

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
import emails.Email;
import emails.OtpManager;
import entities.TmpRegDataStorage;


@WebServlet("/contacttoemail")
public class ContactToEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ContactToEmail() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email").toLowerCase();
		String mobile = request.getParameter("mobile");
		String usertype = request.getParameter("usertype");		
		HttpSession session = request.getSession();
		
		TmpRegDataStorage regdata=new TmpRegDataStorage();
		
		response.setContentType("application/json"); // set content type to json..........
		
		int emailres;
		int mobileres;
		
		if(usertype.equals("hotel")) {
			emailres = HotelRecords.checkEmail(email);
		}else {
			emailres = CustomerRecords.checkEmail(email);
		}
		
		// email found in the database previously stored....
		if (emailres == 1) {
			out.print("{\"result\":false,\"msg\":\"Previously used email with other account\"}");
		} else if (emailres == 0) {
			
			// here email id not found in database condition excuted..........
			
			if(usertype.equals("hotel")) {
				mobileres = HotelRecords.checkMobile(mobile); // check mobile no. in the database......
			}else {
				mobileres = CustomerRecords.checkMobile(mobile); // check mobile no. in the database......
			}
			
			//checking mobile response after check mobile no in the database..........
			if (mobileres == 1) { 				// present mobile no. in the database.......
				out.print("{\"result\":false,\"msg\":\"Previously used mobile number with other account\"}");
			} else if (mobileres == 0) { 		// mobile no. not found in the database......
				int otp = OtpManager.getOtp();
				Email e_mail = new Email();
				
				e_mail.setTo(email);
				e_mail.setSubject("OTP from foodie...");
				e_mail.setMsg("Foodie registration being processing ....\nFor further process otp is \n\n" + otp);
				// send otp through email.........
				if (e_mail.send()) {
					regdata.setEmail(email);
					regdata.setMobile(mobile);
					regdata.setUsertype(usertype);
					
					session.setAttribute("otp", otp);
					session.setAttribute("userdata",regdata); //set regdata to new request use........					
					out.print("{\"result\":true}");
				} else {
					// otp send fails cause of email id not present or server error...........
					out.print("{\"result\":false,\"msg\":\"otp couldn't send please check email address is correct\"}");
				}
			} else if (mobileres == -1) {
				out.print("{\"result\":false,\"msg\":\"server error checking mobile on server \"}");
			}
		} else if (emailres == -1) {
			// server error while checking the email id in the database........
			out.print("{\"result\":false,\"msg\":\"server error checking email id on server \"}");
		}
		
//		doGet(request, response);
	}

}


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

@WebServlet("/RecoverPassword")
public class RecoverPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RecoverPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String usertype = request.getParameter("usertype");

		String pass = null; // defining variable to store password form the database....

		if (usertype.equals("hotel")) {		//hotel password recovery......
			int test = HotelRecords.checkEmail(email);
			if (test == 1) { // email id present in database.... so sending password by email
				pass = HotelRecords.getPass(email);
			} else if (test == 0) { // email id not in database....
				out.print("{\"result\":false}");
			} else if (test == -1) {
				out.print("{\"result\":false,\"msg\":\"server error\"}");
			}
		}else { 		//customer password recovery ........
			int test = CustomerRecords.checkEmail(email);
			if (test == 1) { 	// email id present in database.... so sending password by email
				pass = CustomerRecords.getPass(email);
			} else if (test == 0) { 	// email id not in database....
				out.print("{\"result\":false}");
			} else if (test == -1) {
				out.print("{\"result\":false,\"msg\":\"server error\"}");
			}
		}

		
		//sending password by email if password recover from the database.....
		if(pass!=null) {		//check if password recover from the database or not for the given email id respective account in the system....
			Email e_mail = new Email();
			
			e_mail.setTo(email);	//set recipient addressss.....
			e_mail.setSubject("Yout password recover request excuted....");		//set subject for the email....
			e_mail.setMsg("Your password for --- \n" + email + "\n\nPassword --\n" + pass);		//set email body 
			
			if (e_mail.send()) {				//sending actual email.......	
				out.print("{\"result\":true}");
			} else {
				out.print("{\"result\":false,\"msg\":\"could not send password on mail id please try again some time\"}");
			}
		}
		
//		doGet(request, response);
	}

}

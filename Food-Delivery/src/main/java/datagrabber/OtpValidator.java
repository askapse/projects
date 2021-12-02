package datagrabber;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.TmpRegDataStorage;

@WebServlet("/otpvalidator")
public class OtpValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OtpValidator() {
		super();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter();
			response.setContentType("application/json");
		
		HttpSession session=request.getSession();
			TmpRegDataStorage regdata=(TmpRegDataStorage)session.getAttribute("userdata");
			
			//check user from the registration way or not 
			//if user try to access directly by the url server send redirect to the index page....
			if(regdata==null) {
				session.invalidate();
				response.sendRedirect("./");
			}
						
			String userotp=request.getParameter("otp");				//getting otp from the user 
				int serverotp=(int)session.getAttribute("otp");		//geting otp from the server side generated and send to the user email
			
				if(userotp.equals(Integer.toString(serverotp))) {		//compare email otp enterd by the user and send by the server are same or not....
					session.removeAttribute("otp");
					out.print("{\"result\":true}");
				}else {
					out.print("{\"result\":false}");
				}
	}
}
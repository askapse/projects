

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OTPVerifier
 */
@WebServlet("/otpverifier")
public class OTPVerifier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OTPVerifier() {
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
			if(usertype == null) {
				session.invalidate();
				return;
			}			
			
		String userotp=request.getParameter("otp");				//getting otp from the user 
		int serverotp=(int)session.getAttribute("otp");		//geting otp from the server side generated and send to the user email
	
		if(userotp.equals(Integer.toString(serverotp))) {		//compare email otp enterd by the user and send by the server are same or not....
			session.removeAttribute("otp");
			out.print("{\"result\":true}");
		}else {
			out.print("{\"result\":false}");
		}
		
		
//		doGet(request, response);
	}

}

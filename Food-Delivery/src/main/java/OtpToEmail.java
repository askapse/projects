

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import emails.Email;
import emails.OtpManager;


@WebServlet("/otptoemail")
public class OtpToEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OtpToEmail() {
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
			
			
		String email=(String)request.getParameter("email");
			Email e_mail=new Email();
				e_mail.setTo(email);
				e_mail.setSubject("NO_REPLY");
				int otp=OtpManager.getOtp();
				e_mail.setMsg("E-mail id chenge request verification...\n\n\nOTP "+otp);
		
				if(e_mail.send()) {
					session.setAttribute("otp", otp);
					session.setAttribute("email", email);
					out.print("{\"result\":true}");
				}else {
					out.print("{\"result\":false,\"msg\":\"failed to send otp try again\"}");
				}
				
//		doGet(request, response);
	}

}

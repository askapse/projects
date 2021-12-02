package hotel;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.HotelRecords;


@WebServlet("/hpassupdate")
public class HPassUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HPassUpdate() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		HttpSession session = request.getSession();
			String user = session.getAttribute("usertype").toString();
				if(!user.equals("hotel")) {
					session.invalidate();
					out.print("{\"result\":\"reload\"}");
					return;
				}
			
			//gettting userid from the session 
			long id = (long) session.getAttribute("userid");
			
			//getting new password from the user			
			String pass = request.getParameter("pass");
				if(pass == null) {
					out.print("{\"result\":false,\"msg\":\"didn't grab the new password\"}");
					return;
				}
				
				int chker = HotelRecords.updatePassWord(pass, id);
				
				if(chker == 1) {
					out.print("{\"result\":true}");
				}else if(chker == 0) {
					out.print("{\"result\":false,\"msg\":\"Password not changed please try again later...\"}");
				}else if(chker == -1) {
					out.print("{\"result\":false,\"msg\":\"Error while updating password please try again...\"}");
				}	
			
//		doGet(request, response);
	}

}
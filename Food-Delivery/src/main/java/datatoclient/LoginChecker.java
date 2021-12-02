package datatoclient;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/loginchecker")
public class LoginChecker extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public LoginChecker() {
        super();
 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
		//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
//		response.setContentType("application/json");
		
		HttpSession session=request.getSession();			
			String usertype=(String)session.getAttribute("usertype");	
			if(usertype != null) {
				out.print("{\"result\":true,\"user\":\""+usertype+"\"}");	
			}else {
				out.print("{\"result\":false}");
			}
		
//		doGet(request, response);
	}

	
//	public static void main(String args[]) {
//		Object a=10l;
//		String s=a.toString();
//		System.out.println(s);
//	}
}

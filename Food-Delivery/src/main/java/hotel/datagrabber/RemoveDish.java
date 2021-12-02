package hotel.datagrabber;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.HotelRecords;


@WebServlet("/removemydish")
public class RemoveDish extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public RemoveDish() {
        super();    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");
		
		HttpSession  session=request.getSession();
		String usertype=(String)session.getAttribute("usertype");
		long userid=(long)session.getAttribute("userid");
		
		if(usertype == null) {
			session.invalidate();
			return;
		}
				
		int dishid=Integer.parseInt(request.getParameter("id"));
		
		int res=HotelRecords.removeDish(dishid, userid);
		if(res == 1) {
			out.print("{\"result\":true}");
		}else if(res == 0) {
			out.print("{\"result\":false,\"msg\":\"bad request may be this dish didn't belongs to you\"}");
		}else if(res == -1) {
			out.print("{\"result\":false,\"msg\":\"server unable to remove dish please try again\"}");
		}
		
//		doGet(request, response);
	}

}

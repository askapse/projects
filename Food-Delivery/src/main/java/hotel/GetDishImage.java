package hotel;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.HotelRecords;

@WebServlet("/getdishimage")
public class GetDishImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetDishImage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		HttpSession session=request.getSession();
			String usertype=(String)session.getAttribute("usertype");
			long userid=(long)session.getAttribute("userid");
			if(!usertype.equals("hotel")) {
				session.invalidate();
//				response.getWriter().print("{\"reload\":true}");
				return;			
			}
		
			long dishid=Long.parseLong(request.getParameter("id"));
			
			
			byte [] image=HotelRecords.getDishImage(dishid, userid);
					
				if(image == null) {
					throw new NullPointerException();
				}else {
					
					response.setContentType("image/png");
					response.setContentLength(image.length);
						
					OutputStream out=response.getOutputStream();
						out.write(image, 0, image.length);
						
						out.flush();
					response.flushBuffer();
					
				}
				
				
				
			
//		doGet(request, response);
	}

}

package customer;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.CustomerRecords;
import databaselogic.HotelRecords;


@WebServlet("/dishImage")
public class DishImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public DishImage() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String usertype=(String)session.getAttribute("usertype");
		if(!usertype.equals("customer")) {
			session.invalidate();
			return;
		}else {				
					byte [] image=HotelRecords.getDishImage(Long.parseLong(request.getParameter("dishid")));
						if(image== null) {
							throw new NullPointerException();
							
						}else {
							//write image on the response
							response.setContentType("image/png");
							response.setContentLength(image.length);
								
							OutputStream outimg=response.getOutputStream();
								outimg.write(image, 0, image.length);
								
								outimg.flush();
							response.flushBuffer();
						}			
			}

		
//		doGet(request, response);
	}

}

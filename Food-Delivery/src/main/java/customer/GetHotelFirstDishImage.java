package customer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.CustomerRecords;
import databaselogic.HotelRecords;


@WebServlet("/gethotelfirstdishimage")
public class GetHotelFirstDishImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public GetHotelFirstDishImage() {
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
			}
			
			long hid=Long.parseLong(request.getParameter("hotelid"));  //get hotel id from the user
			long dishid=CustomerRecords.getHotelFirstDishId(hid);     //get image first dish id of the hotel
			if(hid == -1) {
				throw new NullPointerException();
			}else {				
						byte [] image=HotelRecords.getDishImage(dishid, hid);
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

package hotel.datagrabber;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import databaselogic.HotelRecords;
import hotel.entities.Dish;

/**
 * Servlet implementation class NewDish
 */
@WebServlet("/addnewdish")
@MultipartConfig
public class NewDish extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NewDish() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");
		
		Dish dish=new Dish();
		
		Part imgfile=request.getPart("imagefile");
		dish.setDishname(request.getParameter("dishname"));
		dish.setPrice(Float.parseFloat(request.getParameter("price")));
		dish.setHotelid((long)request.getSession().getAttribute("userid"));
		
		if(imgfile!=null) {
			InputStream inimg=imgfile.getInputStream();
			byte []img=inimg.readAllBytes();
				
				dish.setImage(img);
			
				if(HotelRecords.addDish(dish)) {
					out.print("{\"result\":true}");
				}else {
					out.print("{\"result\":false,\"msg\":\"Fail dish adding operation\"}");
				}						
		}else {
			out.print("{\"result\":false,\"msg\":\"File not uploaded correctly\"}");
		}
		
//		doGet(request, response);
	}

}

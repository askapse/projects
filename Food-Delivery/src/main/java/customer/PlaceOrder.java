package customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.HotelRecords;
import databaselogic.Orders;
import hotel.OrderToHotelEmailComposer;
import orders.entities.DishOrder;

@WebServlet("/placeorder")
public class PlaceOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public PlaceOrder() {
        super();      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		HttpSession session=request.getSession();
			String usertype=(String)session.getAttribute("usertype");
			
			if(!usertype.equals("customer")) {
				session.invalidate();
				return;
			}
		
		long hid=Long.parseLong(request.getParameter("hotel"));
		long customer=(long)session.getAttribute("userid");
			
		List<Long> dishid=new ArrayList<>();
		List<Long> qnt=new ArrayList<>();
 		String dishes="";
		
		
		if(dishid.size()>10) {
			out.print("{\"result\":false,\"msg\":\"you try to brich system\"}");
			return;
		}
		
		
		for(String did:request.getParameter("ids").split("[\\[\\],]")) {
			
			if(!did.trim().isEmpty())
				dishid.add(Long.parseLong(did.toString().trim()));

		}
		
		for(String cnt:request.getParameter("count").split("[\\[\\],]")) {
			if(!cnt.trim().isEmpty())
				qnt.add(Long.parseLong(cnt.toString().trim()));

		}
		
		//dish and hotel id checking
		if(HotelRecords.checkIdDish(hid,dishid)) {
				dishes=dishid.toString()+"|"+qnt.toString();
			
			DishOrder ord=new DishOrder();
				ord.setOrddish(dishes);
				ord.setCustomerid(customer);
				ord.setHotelid(hid);
				ord.setStatus("pending");
				Date dt=new Date();
				ord.setDatetime(new Date());
			if(Orders.newOrder(ord)) {
				Thread eth=new Thread(new Runnable() {
					
					@Override
					public void run() {
						OrderToHotelEmailComposer ordnemail=new OrderToHotelEmailComposer(dishid, qnt, hid, customer);
						ordnemail.sendOrderInfo();
						
					}
				});
								
				//start new Thread to send email of order info to the hotel... 
				eth.start();
				
				out.print("{\"result\":true}");				
			}else {
				out.print("{\"result\":false,\"msg\":\"Some error occurs please try again\"}");
			}
			
		}else {
			out.print("{\"result\":false,\"msg\":\"you try to brich system\"}");			
		}	
		
//		doGet(request, response);
	}

}


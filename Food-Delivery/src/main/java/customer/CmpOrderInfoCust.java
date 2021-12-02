package customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.HotelRecords;
import databaselogic.Orders;
import hotel.entities.Dish;

@WebServlet("/cmporderinfocust")
public class CmpOrderInfoCust extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public CmpOrderInfoCust() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{PrintWriter out=response.getWriter();
		response.setContentType("application/json");
		
//		getting info from the use for basic ids 
		HttpSession session=request.getSession();
			String usertype=(String)session.getAttribute("usertype");
			long userid=(long)session.getAttribute("userid");
			
				//checking user is valid or not
				if(!usertype.equals("customer")) {
					session.invalidate();					
					//return reload response for the user end
					out.print("{\"result\":\"reload\"}");
					out.close();
					return;				
				}				
		//getting order id from the user..........
			long ordid=Long.parseLong(request.getParameter("id").trim());
			
			//check order belongs to same hotel or not
			List order=Orders.getCOrder(ordid,userid);
				if(order.size() == 1) {
					
					Object[] ordobj=((Object[])order.get(0));
					
				//dishes and quantity in the order from 
					
					String [] dishinfo=((String)ordobj[0]).split("[|]");
					
					String [] dishids=dishinfo[0].split("[\\[\\],]"); //get dish ids
					
					String [] quantity=dishinfo[1].split("[\\[\\],]"); //get dish qunatity					
					float totalbill=0;
					out.print("{\"result\":true,\"dishes\":[");
					
						for(int i=0;i<dishids.length;i++) {
							if(!dishids[i].equals("")) {
								Dish dish=HotelRecords.getDishNoImg(Long.parseLong(dishids[i].trim()));
								System.out.println(dish);
								out.print("{\"name\":\""+dish.getDishname()+"\","
										+ "\"price\":"+dish.getPrice()+","
										+ "\"quantity\":"+Integer.parseInt(quantity[i].trim())+"}");
										totalbill+=(dish.getPrice()*Integer.parseInt(quantity[i].trim()));
								if(i != dishids.length-1) {
									out.print(" ,");
								}
							}							
						}
						
					//get customer details from the database...
					long hotelid=(long)ordobj[1];    //customer id for another details...
					String namemb=HotelRecords.getName(hotelid);  //customer name and mobile no....					
					out.print("],\"name\":\""+namemb+"\","							
							+ "\"address\":\""+HotelRecords.getHAdd(hotelid)+"\","
							+ "\"totalbill\":"+totalbill+","
							+ "\"status\":\""+ordobj[2].toString()+"\","
							+ "\"mobile\":\""+HotelRecords.getMobile(hotelid)+"\","
							+ "\"reason\":\""+ordobj[3]+"\","
							+ "\"by\":\""+((ordobj[4].toString().charAt(0))=='h'?"Hotel":"Customer")+"\"}");
										
										
				}else if(order.size() == 0) {
					out.print("{\"result\":false,\"msg\":\"system breach detected...}");
				}else if(order.size() == -1){
					out.print("{\"result\":false,\"msg\":\"Server fails ...\"}");					
				}		

		
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
//		doGet(request, response);
	}

}

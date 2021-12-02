package hotel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.CustomerRecords;
import databaselogic.HotelRecords;
import databaselogic.Orders;
import emails.Email;
import hotel.entities.Dish;

@WebServlet("/cancelorderbyhotel")
public class CancelOrderByHotel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CancelOrderByHotel() {
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
			if(!usertype.equals("hotel")){
				out.print("{\"result\":\"reload\"}");
				session.invalidate();
				out.close();
				return;
			}
		
		long userid=(long)session.getAttribute("userid");		
		String reason=request.getParameter("reason");
		long ordid=Long.parseLong(request.getParameter("id").trim());
		
		
		
		int res=Orders.cancelOrder(ordid, userid, reason,'h');
			if(res==1) {
				//send mail to the customer for cancelling order
				Thread t=new Thread(new Runnable() {					
					@Override
					public void run() {
						List ord=Orders.getOrder(ordid, userid);
						String emailstr="your Order for \" "+HotelRecords.getName(userid)+" \"\n\n\n";
						
						//order details
						String [] dishinfo=(((Object[])ord.get(0))[0]).toString().split("[|]");
						
						String [] dishids=dishinfo[0].split("[\\[\\],]"); //get dish ids
						
						String [] quantity=dishinfo[1].split("[\\[\\],]"); //get dish qunatity					
						for(int i=0;i<dishids.length;i++) {
							if(!dishids[i].equals("")) {
								Dish dish=HotelRecords.getDishNoImg(Long.parseLong(dishids[i].trim()));
								System.out.println(dish);
								emailstr+=String.format("%-30s", dish.getDishname())+" :"+String.format("%-8d",Integer.parseInt(quantity[i].toString().trim()))+" Price :"+dish.getPrice()+"\n";																		
							}							
						}
						
						emailstr+="Has been canceled by hotel due to ...\n\n"+reason;
						
						
							
						Email e_mail=new Email();
							e_mail.setTo(CustomerRecords.getEmail((long)((Object[])ord.get(0))[1]));
							e_mail.setSubject("Order canceled ");
							e_mail.setMsg(emailstr);
							
							e_mail.send();
					}
				});
				t.start();  //start the thread for the sending mail 
				t.setPriority(1);
				
				out.print("{\"result\":true}");
			}else if(res==0) {
				out.print("{\"result\":false,\"msg\":\"You trying to brich the system\"}");
			}else if(res==-1) {
				out.print("{\"result\":false,\"msg\":\"server fails to cancel order\"}");
			}		
//		doGet(request, response);
	}

}

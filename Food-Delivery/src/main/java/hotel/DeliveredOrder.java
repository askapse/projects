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
import hotel.entities.SellOrder;

@WebServlet("/deliveredorderbyhotel")
public class DeliveredOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeliveredOrder() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");

		HttpSession session = request.getSession();
		String usertype = (String) session.getAttribute("usertype");
		if (!usertype.equals("hotel")) {
			out.print("{\"result\":\"reload\"}");
			session.invalidate();
			out.close();
			return;
		}

		long userid = (long) session.getAttribute("userid");
		long ordid = Long.parseLong(request.getParameter("id").trim());
		
		int res = Orders.deliverOrder(ordid, userid);
		if (res == 1) {
			// send mail to the customer for accepting order
			Thread mailer = new Thread(new Runnable() {
				@Override
				public void run() {
					List ord = Orders.getOrder(ordid, userid);
					String emailstr = "your Order for \" " + HotelRecords.getName(userid) + " \"\n\n\n";

					// order details
					String[] dishinfo = (((Object[]) ord.get(0))[0]).toString().split("[|]");

					String[] dishids = dishinfo[0].split("[\\[\\],]"); // get dish ids

					String[] quantity = dishinfo[1].split("[\\[\\],]"); // get dish qunatity
					
					float totalbill=0;//totale bill calculator
					
					for (int i = 0; i < dishids.length; i++) {
						if (!dishids[i].equals("")) {
							Dish dish = HotelRecords.getDishNoImg(Long.parseLong(dishids[i].trim()));							
							emailstr += String.format("%-30s", dish.getDishname()) + " :"
									+ String.format("%-8d", Integer.parseInt(quantity[i].toString().trim()))
									+ " Price :" + dish.getPrice() + "\n\n";
							totalbill+=Integer.parseInt(quantity[i].toString().trim())*dish.getPrice();
						}
					}

					emailstr += "Has been Delivered Sucessfully by hotel.\n\n\n\nTotal Bill Amount is - "+totalbill;

					Email e_mail = new Email();
					e_mail.setTo(CustomerRecords.getEmail((long) ((Object[]) ord.get(0))[1]));
					e_mail.setSubject("Order Delivered Successfully ");
					e_mail.setMsg(emailstr);

					e_mail.send();
				
			
		     //save sells to the database...	
					SellOrder sell=new SellOrder();
						sell.setCustid((long) ((Object[]) ord.get(0))[1]);
						sell.setOrderid(ordid);
						sell.setHotelid(userid);
						sell.setTbill(totalbill);
							
					Orders.addSellOrder(sell);
				}
			}); 
			
			mailer.start(); // start the thread for the sending mail
			mailer.setPriority(1);

			
			
			
			out.print("{\"result\":true}");
		} else if (res == 0) {
			out.print("{\"result\":false,\"msg\":\"You trying to brich the system\"}");
		} else if (res == -1) {
			out.print("{\"result\":false,\"msg\":\"server fails to accept order\"}");
		}
//		doGet(request, response);
	}

}

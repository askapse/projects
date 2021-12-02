package hotel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import customer.entities.CustomerAddress;
import databaselogic.CustomerRecords;
import databaselogic.HotelRecords;

@WebServlet("/getorders")
public class GetOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetOrders() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		String usertype = (String) session.getAttribute("usertype");

		if (!usertype.equals("hotel")) {
			session.invalidate();
			return;
		}

		String date = request.getParameter("date");
		if (date == null) {
			out.print("false");
			return;
		}

		long hotelid=(long)session.getAttribute("userid");
		
		List orders=HotelRecords.getDateOrder(hotelid, date);
		
		Iterator orditr=orders.iterator();
		
		//print the json string for the orders details fot the hotel user.....
		
		int d=orders.size();
		
		String status[]=new String[d];
		long ordid[]=new long[d];
		String[] datetime=new String[d];
		long [] cid=new long[d];
		
		int i=0;
		while(orditr.hasNext()) {
			Object [] obj=(Object[])orditr.next();
			
			//assigning all date to the data array....
			status[i]=(String)obj[0];
			ordid[i]=(long)obj[1];
			datetime[i]=(String)obj[2].toString();
			cid[i]=(long)obj[3];	
			
			i+=1;
		}
		
		out.print("{\"status\":[");
		
		
		for(int j=0;j<d;j++) {
			out.print("\""+status[j]+"\"");
			if(j!=d-1)
				out.print(" ,");
		}		
		out.print("],\"ordid\":[");
		
		for(int j=0;j<d;j++) {
			out.print(ordid[j]);
			if(j!=d-1)
				out.print(" ,");
		}
		out.print("],\"time\":[");
		
		for(int j=0;j<d;j++) {
			out.print("\""+(datetime[j].split(" "))[1]+"\"");
			if(j!=d-1)
				out.print(" ,");
		}		
		out.print("],\"caddress\":[");
		
		for(int j=0;j<d;j++) {
			out.print("\""+CustomerRecords.getCustomerAdd(cid[j])+"\"");
			if(j!=d-1)
				out.print(" ,");
		}
		out.print("],\"cid\":[");
		
		for(int j=0;j<d;j++) {
			out.print(cid[j]);
			if(j!=d-1)
				out.print(" ,");
		}
		out.print("]}");
		
		
//		doGet(request, response);
	}

}

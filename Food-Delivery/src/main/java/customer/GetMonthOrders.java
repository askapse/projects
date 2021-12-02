package customer;

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

import databaselogic.CustomerRecords;
import databaselogic.HotelRecords;

@WebServlet("/getmonthorders")
public class GetMonthOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetMonthOrders() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		String usertype = (String) session.getAttribute("usertype");

		if (!usertype.equals("customer")) {
			out.print("{\"result\":\"reload\"}");
			session.invalidate();
			return;
		}

		String date = request.getParameter("date");
		if (date == null) {
			out.print("{\"result\":false,\"msg\":\"Please provide date\"}");
			return;
		}

		long custid=(long)session.getAttribute("userid");
		
		List orders=CustomerRecords.getMonthOrder(custid, date);
		
		Iterator orditr=orders.iterator();
		
		//print the json string for the orders details fot the hotel user.....
		
		int d=orders.size();
		
		String status[]=new String[d];
		long ordid[]=new long[d];
		String[] datetime=new String[d];
		long [] hid=new long[d];
		
		int i=0;
		while(orditr.hasNext()) {
			Object [] obj=(Object[])orditr.next();
			
			//assigning all date to the data array....
			status[i]=(String)obj[0];
			ordid[i]=(long)obj[1];
			datetime[i]=obj[2].toString();
			hid[i]=(long)obj[3];	
			
			i+=1;
		}				
		
		out.print("{\"result\":true,\"status\":[");
		
		
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
		out.print("],\"name\":[");
		
		for(int j=0;j<d;j++) {
			out.print("\""+HotelRecords.getName(hid[j])+"\"");			
			if(j!=d-1)
				out.print(" ,");
		}
		out.print("],\"dtime\":[");
		
		for(int j=0;j<d;j++) {
			out.print("\""+datetime[j]+"\"");
			if(j!=d-1)
				out.print(" ,");
		}				
		out.print("]}");

		
//		doGet(request, response);
	}

}

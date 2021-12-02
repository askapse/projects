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

import databaselogic.HotelRecords;

@WebServlet("/gethoteldishes")
public class GetHotelDishes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetHotelDishes() {
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
			
			long hid=Long.parseLong(request.getParameter("hotelid"));
				
			List dishes=HotelRecords.getDishList(hid);
				if(dishes == null) {
					out.print("{\"result\":false,\"msg\":\"Error while retriving dishes from the server\"}");
				}else if(dishes.size() == 0) {
					out.print("{\"result\":false,\"msg\":\"This hotel doesn't added any dish yet\"}");
				}else {
					int d=dishes.size();
					long dishid[]=new long[d];
					String dishname[]=new String[d];
					float price[]=new float[d];
				
					Iterator itr=dishes.iterator();		
					int i=0;
						while(itr.hasNext()) {
							Object[] row=(Object[])itr.next();
							dishid[i]=(long)row[0];
							dishname[i]=(String)row[1];
							price[i]=(float)row[2];
							i++;
						}
				
					out.print("{\"result\":true,\"id\":[");
					for(int j=0;j<d;j++) {
						out.print(""+dishid[j]+"");
						if(j!=d-1) {
							out.print(", ");
						}
					}
					out.print("],");
				
					out.print("\"name\":[");
					for(int j=0;j<d;j++) {
						out.print("\""+dishname[j]+"\"");
						if(j!=d-1) {
							out.print(", ");
						}
					}
				
					out.print("],");
				
					out.print("\"price\":[");
					for(int j=0;j<d;j++) {
						out.print(""+price[j]+"");
						if(j!=d-1) {
							out.print(", ");
						}
					}
				
					out.print("]}");
				
				}
				out.close();

				
 			
		
		
//		doGet(request, response);
	}

}

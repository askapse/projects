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

@WebServlet("/gethotellist")
public class GetHotelList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetHotelList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		
		HttpSession session=request.getSession();
			String usertype=(String)session.getAttribute("usertype");
				if(!usertype.equals("customer")) {
					session.invalidate();
					return;
				}
//			System.out.println(usertype);	
			int zipcode=(int)session.getAttribute("zipcode");
			List hotels=CustomerRecords.getZipcodeHotels(zipcode);
				if(hotels == null) {
					out.print("error"); //error while retriving data....
				}else if(hotels.size() == 0) {
					out.print("false");//no hotel found
				}else {
					//make json string.........
					int d=hotels.size();
					
					long hotelid[]=new long[d];
					String hotelname[]=new String[d];
					String area[]=new String[d];
					String city[]=new String[d];
					
					Iterator itr=hotels.iterator();
					int i=0;
					while(itr.hasNext()) {											
						Object []row=(Object[])itr.next();	
						hotelid[i]=(long)row[0];
						hotelname[i]=(String)row[1];
						area[i]=(String)row[2];
						city[i]=(String)row[3];
						
						i+=1;
					}
					
					out.print("{\"hotelid\":[");
					for(int j=0;j<d;j++) {
						out.print(hotelid[j]);
						
						if(j != d-1) {
							out.print(", ");
						}
					}
					out.print("],\"hotelname\":[");
					
					for(int j=0;j<d;j++) {
						out.print("\""+hotelname[j]+"\"");
						
						if(j != d-1) {
							out.print(", ");
						}
					}
					out.print("],\"area\":[");
					
					for(int j=0;j<d;j++) {
						out.print("\""+area[j]+"\"");
						
						if(j != d-1) {
							out.print(", ");
						}
					}
					out.print("],\"city\":[");
					
					for(int j=0;j<d;j++) {
						out.print("\""+city[j]+"\"");
						
						if(j != d-1) {
							out.print(", ");
						}
					}
					out.print("]}");
				}
		
		
		
//		doGet(request, response);
	}

}

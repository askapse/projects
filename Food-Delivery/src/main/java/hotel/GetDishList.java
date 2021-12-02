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

import databaselogic.HotelRecords;

@WebServlet("/getdishlist")
public class GetDishList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetDishList() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");
		
		HttpSession session=request.getSession();
		String usertype=(String)session.getAttribute("usertype");
		long userid=(long)session.getAttribute("userid");
		
		if(!usertype.equals("hotel")) {
			session.invalidate();
			return;
		}
		
		List alldishes=HotelRecords.getDishList(userid);
		if(alldishes.size()==0) {
			out.print("false");			
		}else {
			
			int d=alldishes.size();
			long dishid[]=new long[d];
			String dishname[]=new String[d];
			float price[]=new float[d];
		
			Iterator itr=alldishes.iterator();		
			int i=0;
				while(itr.hasNext()) {
					Object[] row=(Object[])itr.next();
					dishid[i]=(long)row[0];
					dishname[i]=(String)row[1];
					price[i]=(float)row[2];
					i++;
				}
		
			out.print("{\"id\":[");
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

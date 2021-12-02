package customer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.CustomerRecords;
import databaselogic.HotelRecords;

@WebServlet("/customerlogin")
public class CustomerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CustomerLogin() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");	
		
		String username=request.getParameter("username").toLowerCase();
		String pass=request.getParameter("pass");
		String user=request.getParameter("user");
		
		if(user.equals("customer")){
			//customer login credincial checkup...
			int test=CustomerRecords.checkEmail(username);			//checking email to the database for the user varification...
			if(test==1){	//if email is present in the database check the password related to the user...
				String serverpass=CustomerRecords.getPass(username);
				if(!serverpass.equals("error")) {
					if(serverpass.equals(pass)) {
						//password maches to the user ....
						
						long id=CustomerRecords.getId(username);
						int zipcode=CustomerRecords.getZipCode(id);
						
						if(id!=-1) {							
							HttpSession session=request.getSession();
							session.setAttribute("userid",id);
							session.setAttribute("zipcode",zipcode);
							session.setAttribute("usertype","customer");
							out.print("{\"result\":true}");		//valid credencials													
						}else {
							out.print("{\"result\":false,\"msg\":\"server error occurs\"}");
						}
																			
					}else {
						out.print("{\"result\":false,\"msg\":\"Please enter correct password\"}");
					}
				}else {
					out.print("{\"result\":false,\"msg\":\"error while checking password\"}");
				}
			}else if(test == 0 ) {
				out.print("{\"result\":false,\"msg\":\"Email id not registered with any account\"}");
			}else if(test == -1) {
				out.print("{\"result\":false,\"msg\":\"error while checking email id \"}");
			}
			
		}else {
			out.print("{\"result\":false,\"msg\":\"You are not valid user to login \"}");
		}	
	}

}

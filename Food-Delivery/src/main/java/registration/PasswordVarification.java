package registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaselogic.CustomerUser;
import databaselogic.HotelUser;
import entities.TmpRegDataStorage;

@WebServlet("/passwordVarification")
public class PasswordVarification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PasswordVarification() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		TmpRegDataStorage regdata = (TmpRegDataStorage) session.getAttribute("userdata");

		if (regdata == null) {
			session.invalidate();
			response.sendRedirect("./");
		}

		String pass = request.getParameter("pass");
		String repass = request.getParameter("repass");

		if (pass.equals(repass)) {
			if (pass.length() == 8) {
				if (Pattern.compile("[a-z]").matcher(pass).find()) {
					if (Pattern.compile("[A-Z]").matcher(pass).find()) {
						if (Pattern.compile("[0-9]").matcher(pass).find()) {
							if (Pattern.compile("[!@#$%^&*)(+=._-]").matcher(pass).find()) {

								if (regdata.getUsertype() == "hotel") { // usertype check and call the function to store
																		
									if (new HotelUser().addUser(regdata)) {// data in the database.....
										out.print(true);
									} else
										out.print(false);
								} else { // usertype customer call the function
									if (new CustomerUser().addUser(regdata)) {  //data in the database.......
										out.print(true);
									} else
										out.print(false);
								}
							}
						} else
							out.print(false);
					} else
						out.print(false);
				} else
					out.print(false);
			} else
				out.print(false);
		} else
			out.print(false);
	}

}
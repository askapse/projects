package take;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Database;
import entities.InClass;
import entities.Student;

@WebServlet("/addstudent")
public class AddStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddStudent() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doPost(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			session.setAttribute("message", "Please login...");
			response.sendRedirect("./");
		}

		Student st = new Student();

		st.setRoll(Integer.parseInt(request.getParameter("roll")));
		st.setGender(request.getParameter("gender").charAt(0));
		st.setName(request.getParameter("name"));
		st.setClassid(((InClass)session.getAttribute("class")).getId());
		st.setDbo(LocalDate.parse(request.getParameter("dob")));
		st.setEmail(request.getParameter("email"));
		st.setPass(request.getParameter("password"));
		st.setMobile(request.getParameter("mobile"));
		st.setAddress(request.getParameter("address"));
		
		if(Database.st.checkRoll(st.getRoll(),((InClass)session.getAttribute("class")).getId())) {
			session.setAttribute("student", st);
			session.setAttribute("message", "Given roll number previously added...");
			response.sendRedirect("./addstudent.jsp");
			return;
		}
			
		if(Database.st.addStudent(st)) {
			session.setAttribute("message", "Student added successfully...");
			response.sendRedirect("./addstudent.jsp");
			return;
		}else {
			session.setAttribute("message", "Failed to add student...");
			response.sendRedirect("./addstudent.jsp");
			return;
		}

//		doGet(request, response);
	}

}

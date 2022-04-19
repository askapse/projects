package client.take;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import admin.entities.Faculty;
import admin.entities.InClass;
import admin.entities.Student;
import client.dao.MessageDao;
import client.entities.Message;

@WebServlet("/client/sendmsg")
public class SendMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SendMsg() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		Message msg = new Message();
		msg.setText(request.getParameter("text"));

		Student s = null;
		Faculty f = null;
		Integer classid = null;

		// try to initialize values in the perticular variables
		try {
			if ((f = (Faculty) session.getAttribute("faculty")) != null) {
				InClass cls = (InClass) session.getAttribute("class");
				classid = cls.getId();
				msg.setSenderId(f.getId());
				msg.setType('f');
				msg.setTime(LocalDateTime.now());
			} else if ((s = (Student) session.getAttribute("student")) != null) {
				msg.setSenderId(s.getId());
				msg.setType('s');
				msg.setTime(LocalDateTime.now());
				classid = s.getClassid();
			} else {
				out.print("{\"response\":null}");
				return;
			}

			msg.setTime(LocalDateTime.now());
			int id;
			if ((id = MessageDao.msgdao.sendMessage(msg, classid)) > 0) {
				out.print("{\"response\":true,\"id\":" + id + "}");
				return;
			} else {
				out.print("{\"response\":false}");
				return;
			}
		} catch (Exception e) {			
			out.print("{\"response\":false}");
		}

//		doGet(request, response);
	}

}

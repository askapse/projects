package message;

import java.util.List;
import java.util.TreeSet;

import admin.dao.Database;
import admin.entities.Faculty;
import admin.entities.InClass;
import admin.entities.Student;
import client.dao.MessageDao;
import client.entities.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("/msgapp")
public class MessageApp {

	// get students list
	@GET
	@Path("getstudents")
	@Produces(MediaType.APPLICATION_JSON)
	public TreeSet<Student> getStudents(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		Student s = (Student) session.getAttribute("student");
		InClass cls = (InClass) session.getAttribute("class");

		if (s != null) {
			return Database.st.getClassStudents(s.getClassid());
		} else if (cls != null) {
			return Database.st.getClassStudents(cls.getId());
		}
		return null;
	}

	// get students list
	@GET
	@Path("getfaculties")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Faculty> getFaculties(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		Student s = (Student) session.getAttribute("student");
		InClass cls = (InClass) session.getAttribute("class");

		if (s != null) {
			return Database.fac.getClassFaculties(s.getClassid());
		} else if (cls != null) {
			return Database.fac.getClassFaculties(cls.getId());
		}
		return null;
	}

	@GET
	@Path("getid")
	@Produces(MediaType.APPLICATION_JSON)
	public String getId(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		Faculty fac;
		Student st;

		if ((fac = (Faculty) session.getAttribute("faculty")) != null) {
			return "{\"id\":" + fac.getId() + "}";
		} else if ((st = (Student) session.getAttribute("student")) != null) {
			return "{\"id\":" + st.getId() + "}";
		}
		return "{}";
	}

	@GET
	@Path("getlastmsg/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(@PathParam("id") long lastmsg, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		Student s = (Student) session.getAttribute("student");
		InClass cls = (InClass) session.getAttribute("class");
		if (s != null) {			
			return MessageDao.msgdao.getMessagesAfter(s.getClassid(), lastmsg);
		} else if (cls != null) {
			return MessageDao.msgdao.getMessagesAfter(cls.getId(), lastmsg);
		}
		return null;
	}

	@GET
	@Path("getfirstmsg/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getFirstMessages(@PathParam("id") long lastmsg, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		Student s = (Student) session.getAttribute("student");
		InClass cls = (InClass) session.getAttribute("class");
		if (s != null) {			
			return MessageDao.msgdao.getMessagesBefore(s.getClassid(), lastmsg);
		} else if (cls != null) {
			return MessageDao.msgdao.getMessagesBefore(cls.getId(), lastmsg);
		}
		return null;
	}

}
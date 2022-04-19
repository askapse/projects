package client.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import admin.entities.Faculty;
import admin.entities.Student;
import admin.dao.Factory;

public class LoginDao {
	public static LoginDao login;
	
	private LoginDao() {	}
	
	static {
		login = new LoginDao();
	}
	
	public Student loginStudent(String username,String password) {
		Student s = null;

		try {
			Session session = Factory.studenttable.openSession();
			session.beginTransaction();
			
			Query<Student> q = session.createQuery("from Student where email=:e and pass=:p");
			q.setParameter("e", username);
			q.setParameter("p", password);
			
			s = q.getResultList().get(0);
			
			session.getTransaction().commit();
			session.close();
			
			return s;
		} catch (Exception e) {
			return s;
		}
	}
	
	public Faculty loginFaculty(String username,String password) {
		Faculty f = null;
		try {
			Session session = Factory.facultytable.openSession();
			session.beginTransaction();
			
			Query<Faculty> q = session.createQuery("from Faculty where email= :e and pass= :p");
			q.setParameter("e", username);
			q.setParameter("p", password);
			
			f = q.getResultList().get(0);
			
			session.getTransaction().commit();
			session.close();
			return f;
		}catch (Exception e) {
			e.printStackTrace();
			return f;
		}
	}
	
	public static void main(String[] args) {
		
			System.out.println(LoginDao.login.loginFaculty("askapse0@gmail.com", "Abhi@9999"));
	}
	
}

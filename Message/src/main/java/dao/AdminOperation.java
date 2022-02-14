package dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import entities.Admin;

public abstract class AdminOperation {

//	get the admin from database 
	public Admin getAdmin(String username, String password) {
		try {
			Session session = Factory.admintable.openSession();
			session.beginTransaction();

			@SuppressWarnings("unchecked")
			Query<Admin> q = session.createQuery("from Admin where username= :u and password= :p");
			q.setParameter("u", username);
			q.setParameter("p", password);

			Admin admin = q.getResultList().get(0);

			session.getTransaction().commit();
			session.close();
			return admin;
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}

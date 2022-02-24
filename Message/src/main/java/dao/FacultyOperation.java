package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import entities.Faculty;

abstract public class FacultyOperation {
	public boolean addFaculty(Faculty f) {
		try {
			Session session = Factory.facultytable.openSession();
			session.beginTransaction();

			session.saveOrUpdate(f);

			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean removeFaculty(int id) {
		try {
			Session session = Factory.facultytable.openSession();
			session.beginTransaction();

			Query<Faculty> q = session.createQuery("delete from Faculty where id= :id");
			q.setParameter("id", id);

			int i = q.executeUpdate();

			session.getTransaction().commit();
			session.close();

			if (i > 0)
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public List<Faculty> getFaculties() {
		List<Faculty> f = new ArrayList<Faculty>();
		try {
			Session session = Factory.facultytable.openSession();
			session.beginTransaction();

			Query<Faculty> q = session.createQuery("from Faculty");
			f = q.getResultList();

			session.getTransaction().commit();
			session.close();
			return f;
		} catch (Exception e) {
			return f;
		}
	}

	public Faculty getFaculty(int id) {
		Faculty f = null;
		try {
			Session session = Factory.facultytable.openSession();
			session.beginTransaction();

			Query<Faculty> q = session.createQuery("from Faculty where id= :id");
			q.setParameter("id", id);
			f = q.getResultList().get(0);

			session.getTransaction().commit();
			session.close();
			return f;
		} catch (Exception e) {
			return f;
		}
	}
}

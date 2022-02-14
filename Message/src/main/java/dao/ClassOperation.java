package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entities.InClass;

public abstract class ClassOperation {

	// save new class in the database
	public boolean addClass(InClass cls) {
		try {
			Session session = Factory.classtable.openSession();
			session.beginTransaction();

			session.save(cls);

			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// get class by id from the database
	public InClass getClass(int id) {
		InClass cls = null;
		try {
			Session session = Factory.classtable.openSession();
			session.beginTransaction();

			cls = session.get(InClass.class, id);

			session.getTransaction().commit();
			session.close();
			return cls;
		} catch (Exception e) {
			return cls;
		}
	}

	// get all classes
	public List<InClass> getClasses() {
		List<InClass> cls = new ArrayList<>();
		try {
			Session session = Factory.classtable.openSession();
			session.beginTransaction();

			cls = ((List<InClass>) session.createQuery("from InClass").getResultList());

			session.getTransaction().commit();
			session.close();
			return cls;
		} catch (Exception e) {
			return cls;
		}
	}

	// remove class
	public boolean removeClass(int id) {
		try {
			Session cs = Factory.classtable.openSession();
			Session ss = Factory.studenttable.openSession();
			ss.beginTransaction();
			ss.createQuery("delete from Student where classid=" + id).executeUpdate();
			ss.getTransaction().commit();
			cs.beginTransaction();
			cs.createQuery("delete from InClass where id=" + id).executeUpdate();
			cs.getTransaction().commit();

			cs.close();
			ss.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}
}

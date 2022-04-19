package admin.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import admin.entities.InClass;
import admin.entities.Subject;
import client.dao.MessageDao;

public abstract class ClassOperation {

	// save new class in the database
	public boolean addClass(InClass cls) {
		try {
			Session session = Factory.classtable.openSession();
			session.beginTransaction();

			session.saveOrUpdate(cls);
			if (!MessageDao.msgdao.checkTable("message" + cls.getId()))
				MessageDao.msgdao.createTable("message" + cls.getId());

			session.getTransaction().commit();

			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// get class by id from the database
	public InClass getClass(int id) {
		System.out.println(id);
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
			if (MessageDao.msgdao.checkTable("message" + id))
				MessageDao.msgdao.dropTable("message" + id);
			cs.getTransaction().commit();

			cs.close();
			ss.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	// subject manipulation to be here

	public boolean addSubject(Subject s) {
		try {
			Session session = Factory.subjecttable.openSession();
			session.beginTransaction();

			session.saveOrUpdate(s);

			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

		
	public boolean removeSubject(int sid, int cid) {
		try {
			Session session = Factory.subjecttable.openSession();
			session.beginTransaction();

			Query<Subject> q = session.createQuery("delete from Subject where id= :sid and cid= :cid");
			q.setParameter("sid", sid);
			q.setParameter("cid", cid);
			int i = q.executeUpdate();

			session.getTransaction().commit();
			session.close();
			if (i > 0)
				return true;
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean removeSubject(int cid) {
		try {
			Session session = Factory.subjecttable.openSession();
			session.beginTransaction();

			Query<Subject> q = session.createQuery("delete from Subject where cid= :cid");			
			q.setParameter("cid", cid);
			int i = q.executeUpdate();

			session.getTransaction().commit();
			session.close();
			if (i > 0)
				return true;
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	
	// get subjects belogns to the perticular class
	public List<Subject> getSubjects(int cid) {
		List<Subject> l = new ArrayList<>();
		try {
			Session session = Factory.subjecttable.openSession();
			session.beginTransaction();

			Query<Subject> q = session.createQuery("from Subject where cid= :cid");
			q.setParameter("cid", cid);
			l = q.getResultList();

			session.getTransaction().commit();
			session.close();
			return l;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return l;
		}
	}

	public List<Integer> getClasses(int fid) {
		List<Integer> l = null;
		try {
			Session session = Factory.subjecttable.openSession();
			session.beginTransaction();

			Query<Integer> q = session.createQuery("select cid from Subject where fid= :fid");
			q.setParameter("fid", fid);
			l = q.getResultList();
			session.getTransaction().commit();
			session.close();
			return l;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return l;
		}
	}
}

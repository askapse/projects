package dao;

import java.util.List;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.query.Query;

import entities.Student;

public abstract class StudentOperation {
	// checking email of the student
	public boolean checkRoll(int roll, int classid) {
		try {
			Session session = Factory.studenttable.openSession();
			session.beginTransaction();

			Query<String> q = session.createQuery("roll from Student where roll= :r and classid= :id");
			q.setParameter("r", roll);
			q.setParameter("id", classid);
			List<String> l = q.getResultList();

			session.getTransaction().commit();
			session.close();
			if (l.size() == 1)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	// add student to the database
	public boolean addStudent(Student s) {
		try {
			Session session = Factory.studenttable.openSession();
			session.beginTransaction();

			session.saveOrUpdate(s);

			session.getTransaction().commit();
			session.close();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// get studnet using class id and student id
	public Student getStudent(int sid, int cid) {
		Student st = null;
		try {
			Session session = Factory.studenttable.openSession();
			session.beginTransaction();

			Query<Student> q = session.createQuery("from Student where id= :sid and classid= :cid");
			q.setParameter("sid", sid);
			q.setParameter("cid", cid);

			st = q.getResultList().get(0);

			session.getTransaction().commit();
			session.close();
			return st;
		} catch (IndexOutOfBoundsException e) {
//			System.out.println(e.getMessage());
			return st;
		}
	}

	// get class students from the database
	public TreeMap<Integer, Student> getClassStudents(int id) {
		TreeMap<Integer, Student> students = new TreeMap<>();
		try {
			Session session = Factory.studenttable.openSession();
			session.beginTransaction();

			Query<Student> q = session.createQuery("from Student where classid= :id");
			q.setParameter("id", id);
			List<Student> l = q.getResultList();

			session.getTransaction().commit();
			session.close();

			for (Student s : l) {
				students.put(s.getRoll(), s);
			}

			return students;
		} catch (Exception e) {
			return students;
		}
	}

	// remove student from system
	public boolean removeStudent(int sid, int cid) {
		try {
			Session session = Factory.studenttable.openSession();
			session.beginTransaction();
			Query<Student> q = session.createQuery("delete from Student where id= :s and classid= :cid");
			q.setParameter("s", sid);
			q.setParameter("cid", cid);
			q.executeUpdate();
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

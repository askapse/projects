package admin.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import admin.entities.Admin;
import admin.entities.Faculty;
import admin.entities.InClass;
import admin.entities.Student;
import admin.entities.Subject;

public interface Factory {
	
	//universal Configuration object
	Configuration con  = new Configuration().configure("hibernate.cfg.xml");
	
	//session factory object for the admin table used over the dao layer
	SessionFactory admintable = con.addAnnotatedClass(Admin.class).buildSessionFactory();
	
	//session factory object for the class table used over the dao layer
	SessionFactory classtable = con.addAnnotatedClass(InClass.class).buildSessionFactory();
	
	//session factory object for the student table used over the dao layer
	SessionFactory studenttable = con.addAnnotatedClass(Student.class).buildSessionFactory();	
	
	//session factory object for the faculty table used over the dao layer
	SessionFactory facultytable = con.addAnnotatedClass(Faculty.class).buildSessionFactory();
	
	//session factory object for the subject table used over the dao layer
	SessionFactory subjecttable = con.addAnnotatedClass(Subject.class).buildSessionFactory();
	
}

package dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entities.Admin;
import entities.InClass;
import entities.Student;

interface Factory {
	
	//universal Configuration object
	Configuration con  = new Configuration().configure("hibernate.cfg.xml");
	
	//session factory object for the admin table used over the dao layer
	SessionFactory admintable = con.addAnnotatedClass(Admin.class).buildSessionFactory();
	
	//session factory object for the class table used over the dao layer
	SessionFactory classtable = con.addAnnotatedClass(InClass.class).buildSessionFactory();
	
	//session factory object for the student table used over the dao layer
	SessionFactory studenttable = con.addAnnotatedClass(Student.class).buildSessionFactory();	
	
}

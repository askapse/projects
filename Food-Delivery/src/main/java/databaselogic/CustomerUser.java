package databaselogic;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import customer.entities.CustomerAddress;
import customer.entities.CustomerAuth;
import customer.entities.CustomerEntity;
import entities.TmpRegDataStorage;
import hotel.entities.HotelAddress;
import hotel.entities.HotelEntity;

public class CustomerUser {
	public static boolean addUser(TmpRegDataStorage regdata) {

		CustomerEntity customer = new CustomerEntity();
		CustomerAuth customerauth = new CustomerAuth();
		CustomerAddress customeradd = new CustomerAddress();

		// customer table info seting
		customer.setMobile(regdata.getMobile());
		customer.setFname(regdata.getFname());
		customer.setLname(regdata.getLname());
		customer.setDob(regdata.getDob());

		// customer address
		customeradd.setDist(regdata.getDist());
		customeradd.setSubdist(regdata.getSubdist());
		customeradd.setCity(regdata.getCity());
		customeradd.setArea(regdata.getArea());
		customeradd.setHome(regdata.getHome());
		customeradd.setZipcode(regdata.getZipcode());

		// customer suthantication info
		customerauth.setEmail(regdata.getEmail());
		customerauth.setPass(regdata.getPass());

		// add embeded address boject to the main customer object
		customer.setAddress(customeradd);

		// add relational table object CustomerAuth
		customer.setAuth(customerauth);

		customerauth.setCustomer(customer);

		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml")
					.addAnnotatedClass(CustomerEntity.class).addAnnotatedClass(CustomerAuth.class);

			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			session.save(customer);
			session.save(customerauth);

			session.getTransaction().commit();
			session.close();
			sf.close();
			return true;
		} catch (Exception e) {

			return false;

		}
	}

	public static boolean updateUser(TmpRegDataStorage regdata, long userid) {

		CustomerEntity customer = new CustomerEntity();
		CustomerAddress customeradd = new CustomerAddress();

		// set userid
		customer.setUserid(userid);

		// customer table info seting
		customer.setMobile(regdata.getMobile());
		customer.setFname(regdata.getFname());
		customer.setLname(regdata.getLname());
		customer.setDob(regdata.getDob());

		// customer address
		customeradd.setDist(regdata.getDist());
		customeradd.setSubdist(regdata.getSubdist());
		customeradd.setCity(regdata.getCity());
		customeradd.setArea(regdata.getArea());
		customeradd.setHome(regdata.getHome());
		customeradd.setZipcode(regdata.getZipcode());

		// add embeded address boject to the main customer object
		customer.setAddress(customeradd);

		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml")
					.addAnnotatedClass(CustomerEntity.class).addAnnotatedClass(CustomerAuth.class);

			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			session.update(customer);

			session.getTransaction().commit();
			session.close();
			sf.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

//get customer info from the database........
	public static CustomerEntity getCustomer(long id) {
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml")
					.addAnnotatedClass(CustomerEntity.class).addAnnotatedClass(CustomerAuth.class);

			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

				CustomerEntity cust=session.get(CustomerEntity.class,id);

			session.getTransaction().commit();
			session.close();
			sf.close();
			return cust;
		} catch (Exception e) {
			return null;
		}
	}

}

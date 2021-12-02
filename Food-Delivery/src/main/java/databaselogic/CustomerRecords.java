package databaselogic;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import customer.entities.CustomerAddress;
import customer.entities.CustomerAuth;
import customer.entities.CustomerEntity;
import hotel.entities.Dish;
import hotel.entities.HotelAuth;
import hotel.entities.HotelEntity;
import orders.entities.DishOrder;

public class CustomerRecords {

	// check email is present in the database previously or not
	public static int checkEmail(String email) {
		Query q;
		List l;
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml").addAnnotatedClass(CustomerAuth.class)
					.addAnnotatedClass(CustomerEntity.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();
			// create query to find the email in the database...........
			q = session.createQuery("select count(email) from customerauth where email='" + email + "'");
			l = q.list(); // it change to be a CustomerAuth

			session.getTransaction().commit();
			session.close();
			sf.close();

			return Integer.parseInt(l.get(0).toString());
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	// check mobile is present in the database previously or not
	public static int checkMobile(String mobile) {
		Query q;
		List l;
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml")
					.addAnnotatedClass(CustomerEntity.class).addAnnotatedClass(CustomerAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();
			// create query to find the email in the database...........

			q = session.createQuery("select count(mobile) from customer where mobile='" + mobile + "'");
			l = q.list(); // it change to be a CustomerAuth

			session.getTransaction().commit();
			session.close();
			sf.close();

			return Integer.parseInt(l.get(0).toString());
		} catch (Exception e) {
			return -1;
		}
	}

	public static String getPass(String email) {
		Query q;
		List l;
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml").addAnnotatedClass(CustomerAuth.class)
					.addAnnotatedClass(CustomerEntity.class);

			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();
			// create query to find the email in the database...........

			q = session.createQuery("select pass from customerauth where email='" + email + "'");
			l = q.list(); // it change to be a CustomerAuth

			session.getTransaction().commit();
			session.close();
			sf.close();
			return l.get(0).toString();
		} catch (Exception e) {
			System.out.println(e);
			return "error";
		}
	}

	// getting id of the user from the database
	public static long getId(String email) {
		List l;
		Query q;

		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml").addAnnotatedClass(CustomerAuth.class)
					.addAnnotatedClass(CustomerEntity.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select id from customerauth where email='" + email + "'");
			l = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();
			return Long.parseLong(l.get(0).toString());
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	// customer all data get from here
	public static CustomerEntity getCustomerEnt(long userid) {
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml")
					.addAnnotatedClass(CustomerEntity.class).addAnnotatedClass(CustomerAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			CustomerEntity customer = session.get(CustomerEntity.class, userid);

			session.getTransaction().commit();
			session.close();
			sf.close();
			return customer;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// update email of the customer
	public static boolean updateEmail(String email, long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml").addAnnotatedClass(CustomerAuth.class)
					.addAnnotatedClass(CustomerEntity.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("update customerauth set email='" + email + "' where id=" + id);
			q.executeUpdate();

			session.getTransaction().commit();
			session.close();
			sf.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	// get customer zipcode for the session
	public static int getZipCode(long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml")
					.addAnnotatedClass(CustomerEntity.class).addAnnotatedClass(CustomerAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();
			// create query to find the email in the database...........

			q = session.createNativeQuery("select zipcode from customer where userid=" + id);
			int zipcode = Integer.parseInt((String) q.uniqueResult());

			session.getTransaction().commit();
			session.close();
			sf.close();
			return zipcode;
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	// get hotel list from the zipcode
	public static List getZipcodeHotels(int zipcode) {
		Query q;
		List l;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelEntity.class)
					.addAnnotatedClass(HotelAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session
					.createNativeQuery("select userid,hotelname,area,city from hotel where zipcode='" + zipcode + "'");
			l = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();
			return l;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	// get hotel first dish image from the database........
	public static long getHotelFirstDishId(long hid) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select min(dishid) from dish where hotelid=" + hid+ " and flag=''");
			long id = (long) q.list().get(0);

			session.getTransaction().commit();
			session.close();
			sf.close();
			return id;
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	// get customer name and address from the database using customer id.....
	public static String getCustomerAdd(long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml")
					.addAnnotatedClass(CustomerEntity.class).addAnnotatedClass(CustomerAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select address from customer where userid=" + id);
			List l = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			CustomerAddress adr = (CustomerAddress) l.get(0);

			return (adr.getHome() + " ," + adr.getArea() + " ," + adr.getCity() + " ," + adr.getSubdist() + " ,"
					+ adr.getDist() + " - " + adr.getZipcode());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

//get customer name and mobile 
	public static String[] getNameMobile(long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml")
					.addAnnotatedClass(CustomerEntity.class).addAnnotatedClass(CustomerAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select fname,lname,mobile from customer where userid=" + id);
			List l = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			String[] str = new String[2];
			str[0] = (String) ((Object[]) l.get(0))[0] + " " + (String) ((Object[]) l.get(0))[1];
			str[1] = (String) ((Object[]) l.get(0))[2];
			return str;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// get customer emailid form the customer id
	public static String getEmail(long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml")
					.addAnnotatedClass(CustomerEntity.class).addAnnotatedClass(CustomerAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select email from customerauth where id=" + id);
			List l = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			return l.get(0).toString();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// get customer name form the customer id
	public static String getName(long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Customer.cfg.xml")
					.addAnnotatedClass(CustomerEntity.class).addAnnotatedClass(CustomerAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select fname,lname from customer where userid=" + id);
			List l = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			return ((Object[])l.get(0))[0].toString()+" "+((Object[])l.get(0))[1].toString();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	//getting hotel orders of the perticular date........
	public static List getMonthOrder(long id, String date) {
			Query q;
			List orders;

			try {
				Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(DishOrder.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				q = session.createNativeQuery("select status,ordid,datetime,hotelid from dishorder where customerid=" + id
						+ " and datetime  like  '" + date + "%'");

				orders = q.list();

				session.getTransaction().commit();
				session.close();
				sf.close();
				return orders;
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}

	// update password
		public static int updatePassWord(String pass,long id) {
			try {
				Configuration con = new Configuration().configure("Customer.cfg.xml").addAnnotatedClass(CustomerEntity.class)
						.addAnnotatedClass(CustomerAuth.class);

				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				Query q = session.createQuery("update customerauth set pass='"+pass+"' where id="+id);
				int i = q.executeUpdate();
				session.getTransaction().commit();
				session.close();
				sf.close();
				if(i>0) {
					return 1;
				}else {
					return 0;
				}
			} catch (Exception e) {
				return -1;
			}
		}

	
}

package databaselogic;

import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import customer.entities.CustomerAddress;
import customer.entities.CustomerAuth;
import customer.entities.CustomerEntity;
import hotel.entities.Dish;
import hotel.entities.HotelAddress;
import hotel.entities.HotelAuth;
import hotel.entities.HotelEntity;

public class HotelRecords {

	// check email is present in the database previously or not
	public static int checkEmail(String email) {
		Query q;
		List l;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelAuth.class)
					.addAnnotatedClass(HotelEntity.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();
			// create query to find the email in the database...........

			q = session.createQuery("select count(email) from hotelauth where email='" + email + "'");
			l = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			return Integer.parseInt(l.get(0).toString());

		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

//get email id from the hotel id...
	public static String getEmail(long id) {

		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelAuth.class)
					.addAnnotatedClass(HotelEntity.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();
			// create query to find the email in the database...........

			Query q = session.createQuery("select email from hotelauth where id=" + id);
			String email = q.uniqueResult().toString();

			session.getTransaction().commit();
			session.close();
			sf.close();

			return email;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// check mobile is present in the database previously or not
	public static int checkMobile(String mobile) {
		Query q;
		List l;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelEntity.class)
					.addAnnotatedClass(HotelAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();
			// create query to find the email in the database...........

			q = session.createQuery("select count(mobile) from hotel where mobile='" + mobile + "'");
			l = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			return Integer.parseInt(l.get(0).toString());

		} catch (Exception e) {
			return -1;
		}
	}

	// get password from the database to check user entered password is correct or
	// not....
	public static String getPass(String email) {
		Query q;
		List l;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelAuth.class)
					.addAnnotatedClass(HotelEntity.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();
			// create query to find the email in the database...........

			q = session.createQuery("select pass from hotelauth where email='" + email + "'");
			l = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();
			return l.get(0).toString();
		} catch (Exception e) {
			return "error";
		}
	}

	// getting id of the user from the database
	public static long getId(String email) {
		List l;
		Query q;

		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelAuth.class)
					.addAnnotatedClass(HotelEntity.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select id from hotelauth where email='" + email + "'");
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

//	add new dish to the database........
	public static boolean addDish(Dish dish) {
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			session.save(dish);

			session.getTransaction().commit();
			session.close();
			sf.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

//	get the dishlist object from the database....
	public static List getDishList(long userid) {
		Query q;
		List dishes;

		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery(
					"select dishid,dishname,price from dish where hotelid=" + userid + " and not flag='r'");

			dishes = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();
			return dishes;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

//get dish by the id 
	public static Dish getDishNoImg(long id) {
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			List l = session.createQuery("select dishname,price from dish where dishid=" + id + " and not flag='r'")
					.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			Dish dish = new Dish();
			dish.setDishname(((Object[]) l.get(0))[0].toString());
			dish.setPrice(Float.parseFloat(((Object[]) l.get(0))[1].toString()));
			return dish;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

//remove dish from the database.........
	public static int removeDish(int dishid, long userid) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("update dish set flag='r' where dishid=" + dishid + " and hotelid=" + userid);
			int res = q.executeUpdate();

			session.getTransaction().commit();
			session.close();
			sf.close();
			return res;
		} catch (Exception e) {
			return -1;
		}
	}

//hotel all data fromthe database..........
	public static HotelEntity getHotelEnt(long userid) {
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelEntity.class)
					.addAnnotatedClass(HotelAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			HotelEntity hotel = session.get(HotelEntity.class, userid);
			HotelAuth auth = session.get(HotelAuth.class, userid);

			session.getTransaction().commit();
			session.close();
			sf.close();
			hotel.setAuth(auth);
			return hotel;
		} catch (Exception e) {
			return null;
		}
	}

//getting dish image from the database.....
	public static byte[] getDishImage(long dishid, long hotelid) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select image from dish where dishid=" + dishid + " and hotelid=" + hotelid);
			byte[] img = (byte[]) q.list().get(0);

			session.getTransaction().commit();
			session.close();
			sf.close();
			return img;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

//getting dish image from the database.....
	public static byte[] getDishImage(long dishid) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select image from dish where dishid=" + dishid);
			byte[] img = (byte[]) q.list().get(0);

			session.getTransaction().commit();
			session.close();
			sf.close();
			return img;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

//update email of the user.........
	public static boolean updateEmail(String email, long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelAuth.class)
					.addAnnotatedClass(HotelEntity.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("update hotelauth set email='" + email + "' where id=" + id);
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

//checking hotel id and dish id for confirm user request for the correct order....
	public static boolean checkIdDish(long hid, List<Long> dids) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select dishid from dish where hotelid=" + hid);
			List dishes = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			for (long dish : dids) {
				if (!dishes.contains(dish)) {
					return false; // did not contain any dish from the user privided
				}
			}

			// have all dishes all right
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

//getting hotel orders of the perticular date........
	public static List getDateOrder(long hid, String date) {
		Query q;
		List orders;

		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createNativeQuery("select status,ordid,datetime,customerid from dishorder where hotelid=" + hid
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

	// get hotel name from id
	public static String getName(long hid) {
		Query q;
		List orders;

		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createNativeQuery("select hotelname from hotel where userid=" + hid);

			String name = q.list().get(0).toString();

			session.getTransaction().commit();
			session.close();
			sf.close();
			return name;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// get address address from the database using customer id.....
	public static String getHAdd(long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelEntity.class)
					.addAnnotatedClass(HotelAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select address from hotel where userid=" + id);
			List l = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			HotelAddress adr = (HotelAddress) l.get(0);

			return (adr.getArea() + " ," + adr.getCity() + " ," + adr.getSubdist() + " ," + adr.getDist() + " - "
					+ adr.getZipcode());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// get mobile
	public static String getMobile(long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelEntity.class)
					.addAnnotatedClass(HotelAuth.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("select mobile from hotel where userid=" + id);
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

	// update password
	public static int updatePassWord(String pass,long id) {
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(HotelEntity.class)
					.addAnnotatedClass(HotelAuth.class);

			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			Query q = session.createQuery("update hotelauth set pass='"+pass+"' where id="+id);
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
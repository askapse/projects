package databaselogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import hotel.entities.Dish;
import hotel.entities.SellOrder;
import orders.entities.DishOrder;

public class Orders {

	// place new order
	public static boolean newOrder(DishOrder order) {

		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(DishOrder.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			session.save(order);

			session.getTransaction().commit();
			session.close();
			sf.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	// check order belongs to or not from the hotel user
	public static List getOrder(long ordid, long hotelid) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session
					.createNativeQuery("select orddish,customerid,status,reason,canceledby from dishorder where ordid="
							+ ordid + " and hotelid=" + hotelid);

			List orddata = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			return orddata;

		} catch (Exception e) {
			return null;
		}
	}

	
	// check order belongs to or not from the hotel user
	public static List getCOrder(long ordid, long custid) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session
					.createNativeQuery("select orddish,hotelid,status,reason,canceledby from dishorder where ordid="
							+ ordid + " and customerid=" + custid);

			List orddata = q.list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			return orddata;

		} catch (Exception e) {
			return null;
		}
	}
	
	
	// cancel the order by the hotel end ... or customer end
	public static int cancelOrder(long ordid, long id, String reason, char cancelby) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(DishOrder.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("update dishorder set status='cancel',reason='" + reason + "',canceledby='"
					+ cancelby + "' where ordid=" + ordid + " and hotelid=" + id);

			int chk = q.executeUpdate();

			session.getTransaction().commit();
			session.close();
			sf.close();

			return chk;

		} catch (Exception e) {
			return -1;
		}
	}

	
	// cancel the order by the hotel end ... or customer end
		public static int cancelCOrder(long ordid, long id, String reason, char cancelby) {
			Query q;
			try {
				Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(DishOrder.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				q = session.createQuery("update dishorder set status='cancel',reason='" + reason + "',canceledby='"
						+ cancelby + "' where ordid=" + ordid + " and customerid=" + id);

				int chk = q.executeUpdate();

				session.getTransaction().commit();
				session.close();
				sf.close();

				return chk;

			} catch (Exception e) {
				return -1;
			}
		}
	
	
	

	// accept the order by the hotel end ... or customer end
	public static int acceptOrder(long ordid, long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(DishOrder.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("update dishorder set status='accepted',reason='-' ,canceledby='-' where ordid="
					+ ordid + " and hotelid=" + id);

			int chk = q.executeUpdate();

			session.getTransaction().commit();
			session.close();
			sf.close();

			return chk;

		} catch (Exception e) {System.out.println(e);
			return -1;
		}

	}

	
	// deliver the order by the hotel end ... or customer end
	public static int deliverOrder(long ordid, long id) {
		Query q;
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(DishOrder.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			q = session.createQuery("update dishorder set status='delivered',reason='-' ,canceledby='-' where ordid="
					+ ordid + " and hotelid=" + id);

			int chk = q.executeUpdate();

			session.getTransaction().commit();
			session.close();
			sf.close();

			return chk;

		} catch (Exception e) {System.out.println(e);
			return -1;
		}

	}
	
	public static void main(String[] args) {
		System.out.println('a' == 'a' ? "ok" : "not ok");
	}
	
	
	
	//add succesfull sell order to the database
	public static boolean addSellOrder(SellOrder order) {
		order.setDatetime(new Date());
		try {
			Configuration con = new Configuration().configure("Hotel.cfg.xml").addAnnotatedClass(SellOrder.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();
				
				session.save(order);

			session.getTransaction().commit();
			session.close();
			sf.close();

			return true;

		} catch (Exception e) {System.out.println(e);
			return false;
		}		
	}
}

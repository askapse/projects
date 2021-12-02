package databaselogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import cafe.Files;
import cafe.The_Cafe;
import entities.Dish;

public class DishManager {
	// add new dish to the database
	public static int addDish(Dish dish) {

		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return 0;
		} else {
			try {
				con.addAnnotatedClass(Dish.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				int chker = (int) session.save(dish);

				session.getTransaction().commit();
				session.close();
				sf.close();

				if (chker == 0) {
					return 0;
				} else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Files.logger("new dish added named :" + dish.getName());
						}
					}).start();
					return 1;
				}

			} catch (Exception e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("Failed to add dish reason : " + e.getMessage());
					}
				}).start();
				return -1;
			}
		}
	}

	// update the dish information from the database
	public static boolean updateDish(Dish dish) {

		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(Dish.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				session.update(dish);

				session.getTransaction().commit();
				session.close();
				sf.close();
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("update dish having name :" + dish.getName());
					}
				}).start();
				return true;
			} catch (Exception e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("Failed to update dish reason : " + e.getMessage());
					}
				}).start();
				return false;
			}
		}
	}

	// change flag of the dish as removed
	public static int removeDish(long id) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return 0;
		} else {
			try {
				con.addAnnotatedClass(Dish.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				int chker = session.createQuery("update dish set flag='r' where id=" + id).executeUpdate();

				session.getTransaction().commit();
				session.close();
				sf.close();
				if (chker >= 0) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Files.logger("remove dish from the system");
						}
					}).start();
					return 1;
				} else {
					return 0;
				}
			} catch (Exception e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("Failed to remove dish from system reason : " + e.getMessage());
					}
				}).start();
				System.out.println(e);
				return -1;
			}
		}
	}

	// fatch all the dishes from the database and return to the calling object
	public static List<Dish> getAllDish() {
		List<Dish> dishes = new ArrayList();
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return dishes;
		} else {
			try {
				con.addAnnotatedClass(Dish.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				Query q = session.createQuery("from dish where flag = 'p'");
				List l = q.list();

				session.getTransaction().commit();
				session.close();
				sf.close();

				if (l.size() == 0) {
					return dishes;
				} else {
					Iterator itr = l.iterator();
					while (itr.hasNext()) {
						dishes.add((Dish) itr.next());
					}
					return dishes;
				}
			} catch (Exception e) {
				return dishes;
			}
		}
	}

	// check name in all the table
	public static boolean checkRDishName(String name) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			con.addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			long chker = (long) session
					.createQuery("select count(name) from dish where name='" + name + "' and flag='r'").list().get(0);

			session.getTransaction().commit();
			session.close();
			sf.close();

			if (chker == 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	// check the dish name not in another id
	public static boolean checkDishNameOtherId(String name, int id) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			con.addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			long chker = (long) session
					.createQuery("select count(name) from dish where name='" + name + "' and not dishid=" + id).list()
					.get(0);

			session.getTransaction().commit();
			session.close();
			sf.close();

			if (chker == 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	// update the remmoved dish flag and price as entered by the user
	public static boolean recoverDish(String name, float price) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			con.addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			int chker = session
					.createQuery("update dish set flag='p' , price=" + price + "where name='" + name.trim() + "'")
					.executeUpdate();

			session.getTransaction().commit();
			session.close();
			sf.close();

			if (chker == 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	// get dish by the name of the dish
	public static Dish dishByName(String name) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return null;
		} else {
			try {
				con.addAnnotatedClass(Dish.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				Dish dish = (Dish) session.createQuery("from dish where name=\'" + name + "\'").list().get(0);

				session.getTransaction().commit();
				session.close();
				sf.close();
				return dish;
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}
	}
}

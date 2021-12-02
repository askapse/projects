package databaselogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import cafe.The_Cafe;
import entities.BillDish;
import entities.BillSave;
import entities.Cupons;
import entities.Dish;

public class Billing {

	// add new cupon
	public static boolean addCupon(Cupons cupon) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(Cupons.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				long chk = (long) session.save(cupon);

				session.getTransaction().commit();
				session.close();
				sf.close();
				if (chk > 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
	}

	// chekc cupon percentage
	public static int checkCuponPer(long cupon) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return -1;
		} else {
			try {
				con.addAnnotatedClass(Cupons.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				List l = session.createQuery("select per from cupons where cnum=" + cupon + " and flag='p'").list();

				session.getTransaction().commit();
				session.close();
				sf.close();

				if (l.size() == 0) {
					return 0;
				} else {
					return ((int) l.get(0));
				}
			} catch (Exception e) {
				return -1;
			}
		}
	}

	// chekc cupon is present or not
	public static int checkCupon(long cupon) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return 0;
		} else {
			try {
				con.addAnnotatedClass(Cupons.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				long chk = (long) session
						.createQuery("select count(cnum) from cupons where cnum=" + cupon + " and flag='p'").list()
						.get(0);

				session.getTransaction().commit();
				session.close();
				sf.close();

				return Integer.parseInt(chk + "");
			} catch (Exception e) {
				return -1;
			}
		}
	}

	public static boolean useCupon(long cupon) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(Cupons.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				long chk = (long) session.createQuery("update cupons set flag='r' where cnum=" + cupon + "")
						.executeUpdate();

				session.getTransaction().commit();
				session.close();
				sf.close();

				if (chk > 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
	}

	// save new bill to the database
	public static boolean addBill(BillSave bill) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(BillSave.class).addAnnotatedClass(BillDish.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				long chk = (long) session.save(bill);

				session.getTransaction().commit();
				session.close();
				sf.close();

				if (chk >= 0) {
					return true;
				} else {
					return false;
				}

			} catch (Exception e) {
				System.out.println(e);
				return false;
			}
		}
	}

	// get bill from the id
	public static List getBill(long id) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return null;
		} else {

			con.addAnnotatedClass(BillSave.class).addAnnotatedClass(BillDish.class).addAnnotatedClass(Dish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			List bl = session.createQuery("select dcpn,discount,section,tble,tax from bill where id=" + id).list();
			List dsh = session.createQuery("select dishid,qnt from billdish where bid =" + id).list();

			List<Integer> qnts = new ArrayList<Integer>();
			List<Dish> dd = new ArrayList<Dish>();
			// adding dishes name priice and quantiti to the lists
			for (int i = 0; i < dsh.size(); i++) {
				Object[] o = (Object[]) dsh.get(i);
				List dls = session.createQuery("select name , price from dish where dishid=" + (long) o[0]).list();

				Dish d = new Dish();
				d.setName(((Object[]) dls.get(0))[0].toString()); // name
				d.setPrice((float) ((Object[]) dls.get(0))[1]); // price

				qnts.add((int) o[1]); // quantity
				dd.add(d);

				d = null;
				System.gc();
			}

			session.getTransaction().commit();
			session.close();
			sf.close();

			List bslv = new ArrayList();
			Object[] o = (Object[]) bl.get(0);
			bslv.add(o[0].toString()); // descount cupon
			bslv.add(Float.parseFloat(o[1].toString())); // discount
			bslv.add(o[2].toString()); // section
			bslv.add(o[3].toString()); // table
			bslv.add(Float.parseFloat(o[4].toString())); // tax on  bill

			bslv.add(dd); // adding dishes
			bslv.add(qnts); // adding quantities

			o = null;
			System.gc();

			return bslv;
		}
	}

	// check cupon in database
	public static boolean checkCuponToAdd(long cpn) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			con.addAnnotatedClass(Cupons.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			long chk = (long) session.createQuery("select count(cnum) from cupons where cnum=" + cpn).list().get(0);

			session.getTransaction().commit();
			session.close();
			sf.close();

			System.out.println(chk);
			if (chk == 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	// get statement fromthe database
	public static List getStatement(String date) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return null;
		} else {
			con.addAnnotatedClass(BillSave.class).addAnnotatedClass(BillDish.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			List l = session.createQuery("select cname,date,tbill,id,tax from bill where date like '" + date + "'").list();

			session.getTransaction().commit();
			session.close();
			sf.close();

			if (l.size() == 0) {
				return null;
			} else {

				List<String> names = new ArrayList<String>();
				List<String> dte = new ArrayList<String>();
				List<Float> tbill = new ArrayList<Float>();
				List<Long> id = new ArrayList<Long>();

				Iterator itr = l.iterator();
				while (itr.hasNext()) {
					Object[] o = (Object[]) itr.next();
					names.add(o[0].toString());
					dte.add(o[1].toString());
					tbill.add((float) o[2] + (float)o[4]);
					id.add((Long) o[3]);
				}

				List data = new ArrayList();
				data.add(names);
				data.add(dte);
				data.add(tbill);
				data.add(id);

				return data;
			}
		}
	}

}

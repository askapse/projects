package databaselogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import cafe.Files;
import cafe.The_Cafe;
import entities.Section;
import entities.Tbl;

public class TableSectionManager {
	// adding sesction to the databse
	public static boolean addSection(Section section) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(Section.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				long chker = (long) session.save(section);

				session.getTransaction().commit();
				session.close();
				sf.close();
				if (chker > 0) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Files.logger("new section added named as " + section.getName());
						}
					}).start();
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("Failed to add section reason : " + e.getMessage());
					}
				}).start();
				return false;
			}
		}
	}

	// change the section flag as r for removed sign section from the database
	public static boolean removeSection(long id) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(Section.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				int chker = session.createQuery("update section set flag='r' where id=" + id).executeUpdate();

				session.getTransaction().commit();
				session.close();
				sf.close();

				if (chker > 0) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Files.logger("removed a section from the system");
						}
					}).start();
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("Failed to remove section reason : " + e.getMessage());
					}
				}).start();
				return false;
			}
		}
	}

	// update the section table information
	public static boolean updateSection(Section section) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(Section.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				session.update(section);

				session.getTransaction().commit();
				session.close();
				sf.close();

				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("section updated named as " + section.getName());
					}
				}).start();
				return true;
			} catch (Exception e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("Failed to update section reason : " + e.getMessage());
					}
				}).start();
				return false;
			}
		}
	}

	// get all sections form the database
	public static List<Section> getAllSection() {
		List<Section> secs = new ArrayList<Section>();
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return secs;
		} else {
			try {
				con.addAnnotatedClass(Section.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				List l = session.createQuery("from section where flag = 'p'").list();

				session.getTransaction().commit();
				session.close();
				sf.close();
				if (l.size() == 0) {
					return secs;
				} else {
					Iterator itr = l.iterator();
					while (itr.hasNext()) {
						secs.add((Section) itr.next());
					}
					return secs;
				}
			} catch (Exception e) {
				return secs;
			}
		}
	}

	// get section id by name of the section
	public static long getSecId(String name) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return 0;
		} else {
			try {
				con.addAnnotatedClass(Section.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				long id = (long) session.createQuery("select id from section where name='" + name + "'").list().get(0);

				session.getTransaction().commit();
				session.close();
				sf.close();

				return id;
			} catch (Exception e) {
				return -1;
			}
		}
	}

	// check removed name of the section in databse
	public static boolean checkRSection(String name) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			con.addAnnotatedClass(Section.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			long flg = (long) session
					.createQuery("select count(name) from section where name='" + name + "' and flag='r'").list()
					.get(0);

			session.getTransaction().commit();
			session.close();
			sf.close();

			if (flg == 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	// recover removed section
	public static boolean recoverSectionByName(String name) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			con.addAnnotatedClass(Section.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			int chkr = session.createQuery("update section set flag='p' where name='" + name + "'").executeUpdate();

			session.getTransaction().commit();
			session.close();
			sf.close();

			if (chkr > 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	// add table
	public static boolean addTable(Tbl table) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(Tbl.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				long chker = (long) session.save(table);

				session.getTransaction().commit();
				session.close();
				sf.close();
				if (chker > 0) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Files.logger("new table added named as " + table.getName());
						}
					}).start();
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("Failed to add table reason : " + e.getMessage());
					}
				}).start();
				return false;
			}
		}
	}

	// change the section flag as r for removed sign section from the database
	public static boolean removeTable(long id) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(Tbl.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				int chker = session.createQuery("update tbl set flag='r' where id=" + id).executeUpdate();

				session.getTransaction().commit();
				session.close();
				sf.close();

				if (chker > 0) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Files.logger("removed a table from the system");
						}
					}).start();
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("Failed to remove table reason : " + e.getMessage());
					}
				}).start();
				return false;
			}
		}
	}

	// update the section table information
	public static boolean updateSection(Tbl table) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(Tbl.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				session.update(table);

				session.getTransaction().commit();
				session.close();
				sf.close();

				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("table updated named as " + table.getName());
					}
				}).start();
				return true;
			} catch (Exception e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("Failed to update table reason : " + e.getMessage());
					}
				}).start();
				return false;
			}
		}
	}

	// get all tables form the database
	public static List<Tbl> getAllTable() {
		List<Tbl> tables = new ArrayList<Tbl>();
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return tables;
		} else {
			try {
				con.addAnnotatedClass(Tbl.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				List l = session.createQuery("from tbl where flag = 'p'").list();

				session.getTransaction().commit();
				session.close();
				sf.close();
				if (l.size() == 0) {
					return tables;
				} else {
					Iterator itr = l.iterator();
					while (itr.hasNext()) {
						tables.add((Tbl) itr.next());
					}
					return tables;
				}
			} catch (Exception e) {
				return tables;
			}
		}
	}

	// check removed name of the table in database
	public static boolean checkRTable(String name, long sid) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			con.addAnnotatedClass(Tbl.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			long flg = (long) session
					.createQuery("select count(name) from tbl where name='" + name + "' and flag='r' and secid=" + sid)
					.list().get(0);

			session.getTransaction().commit();
			session.close();
			sf.close();

			if (flg == 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	// recover removed table
	public static boolean recoverTableByName(String name, long sid) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			con.addAnnotatedClass(Tbl.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			session.beginTransaction();

			int chkr = session.createQuery("update tbl set flag='p' where name='" + name + "' and secid=" + sid)
					.executeUpdate();

			session.getTransaction().commit();
			session.close();
			sf.close();

			new Thread(new Runnable() {
				@Override
				public void run() {
					Files.logger("table recovered named" + name);
				}
			}).start();
			if (chkr > 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	// get section id by name of the tables
	public static long getTableId(String name) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return -1;
		} else {
			try {
				con.addAnnotatedClass(Tbl.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				long id = (long) session.createQuery("select id from tbl where name='" + name + "'").list().get(0);

				session.getTransaction().commit();
				session.close();
				sf.close();

				return id;
			} catch (Exception e) {
				return -1;
			}
		}
	}

	// set flag 'r' for all section tables
	public static boolean removeAllSectionTable(long sid) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return false;
		} else {
			try {
				con.addAnnotatedClass(Tbl.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				int chkr = session.createQuery("update tbl set flag='r' where secid=" + sid).executeUpdate();

				session.getTransaction().commit();
				sf.close();
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("remove all table having in section  " + sid);
					}
				}).start();
				return true;
			} catch (Exception e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("failed to remove all table having in section  " + sid);
					}
				}).start();
				return false;
			}
		}
	}

	// recover all removed tables for removed section
	public static List<Tbl> recoverRecovedSecTbl(long sid) {
		Configuration con = The_Cafe.getConnection();
		if (con == null) {
			return null;
		} else {
			try {
				con.addAnnotatedClass(Tbl.class);
				SessionFactory sf = con.buildSessionFactory();
				Session session = sf.openSession();
				session.beginTransaction();

				session.createQuery("update tbl set  flag='p' where secid=" + sid).executeUpdate();
				List l = session.createQuery("from tbl where secid=" + sid + " and flag='p'").list();

				session.getTransaction().commit();
				sf.close();
				new Thread(new Runnable() {
					@Override
					public void run() {
						Files.logger("remove all table having in section " + sid);
					}
				}).start();

				List<Tbl> tables = new ArrayList<Tbl>();
				Iterator itr = l.iterator();
				while (itr.hasNext()) {
					tables.add((Tbl) itr.next());
				}

				return tables;
			} catch (Exception e) {
				return null;
			}
		}
	}
}

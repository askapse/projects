package userInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class FeeReport {
	Scanner in;
	public static FeeReport freereport;
	static Admin admin;
	static Accountant accountant;
	static public Connection con;
	static public File datadir;
	private boolean flag;

	public FeeReport() {
		in = new Scanner(System.in);
		freereport = this;
		boolean f = false;
		System.out.println("Select the operation mode ....\n1.Database mode \n2.Filesystem mode");
		System.out.print("\nEnter mode : ");		
		while (!f) {
			char ch = in.nextLine().charAt(0);
			switch (ch) {
			case '1':
				flag = true;
				f = true;
				break;
			case '2':
				flag=false;
				f=true;
				break;
			default:
				System.out.print("Wrong input retry : ");
			}
		}

		if (flag) {
			System.out.println("Connecting to database....");
			// get connection

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "abhi", "kali");
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			// check connection established or not
			if (con != null) {
				System.out.println("Connection Successful...");
				System.out.println("Checking database ....");
				if (!checkDB(con)) {
					System.out.print("Enter ADMIN username : ");
					String user = in.nextLine();
					System.out.print("Enter ADMIN password : ");
					String pass = in.nextLine();
					System.out.println("Creating database ...");
					if (createDB(user, pass)) {
						System.out.println("Database created successfully ....");
						System.out.println("Creating admin ...");

					} else
						System.err.println("Error creating database please check manually, "
								+ "\nif database created please drop it and restart the application.");
				}
				this.menu();
			} else {
				System.out.println("Connection not established....\nexist from application...");
			}
		} else {
			// check connection established or not
			System.out.println("Checking filesystem.....");
			if (!checkDB()) {
				System.out.print("Enter ADMIN username : ");
				String user = in.nextLine();
				System.out.print("Enter ADMIN password : ");
				String pass = in.nextLine();
				System.out.println("Creating filesystem ...");
				if (createDB(new StringBuilder(pass), new StringBuilder(user))) {
					System.out.println("Filesystem created successfully ....");
					System.out.println("Creating admin ...");

				} else
					System.err.println("Error creating database please check manually, "
							+ "\nif database created please drop it and restart the application.");
			}
			this.menu();
		}
	}

	// checkDB function to operate on files
	private boolean checkDB() {
		datadir = new File(System.getProperty("user.home") + "\\feereport");
		if (!datadir.exists()) {
			return false;
		} else {
			if (datadir.isDirectory()) {
				File fp[] = datadir.listFiles();
				if (fp.length == 0)
					return false;
				else if (fp.length == 3) {
					boolean s = false;
					boolean a = false;
					for (File f : fp) {
						if (f.getName().equals("student"))
							s = true;
						if (f.getName().equals("accountant"))
							a = true;
					}
					return a && s;
				}

				else if (fp.length > 3) {
					for (File f : fp) {
						if (!(f.getName().equals("student") || f.getName().equals("accountant")
								|| f.getName().equals("admin")))
							f.delete();
					}
					checkDB();
				}
			}
			return false;
		}
	}

	// create DB
	private boolean createDB(StringBuilder pass, StringBuilder user) {

		try {
			File dir = new File(System.getProperty("user.home") + "\\feereport");
			File adm = null;
			if (!dir.exists()) {
				dir.mkdir();
				new File(dir.getAbsolutePath() + "\\student").createNewFile();
				new File(dir.getAbsolutePath() + "\\accountant").createNewFile();
				adm = new File(dir.getAbsolutePath() + "\\admin");
				adm.createNewFile();
			}

			if (adm != null) {
				FileOutputStream fout = new FileOutputStream(adm);
				ObjectOutputStream oin = new ObjectOutputStream(fout);
				char[][] usrps = new char[2][1];
				usrps[0] = (user + "").toCharArray();
				usrps[1] = (pass + "").toCharArray();

				oin.writeObject(usrps);
				oin.close();
				fout.close();
			}

			return true;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	// cheking the Db of the system is available or not
	private boolean checkDB(Connection con) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("show databases");
			while (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase("freereport")) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return false;
	}

	// create DB
	private boolean createDB(String user, String pass) {

		try {
			Statement st = con.createStatement();
			st.execute("create database freereport");
			st.execute("use freereport");
			st.execute(
					"create table accountant(name varchar(30),email varchar(30),mobile varchar(20),password varchar(50))");
			st.execute(
					"create table student(roll int primary key,name varchar(40),email varchar(30),course varchar(20),"
							+ "contact varchar(10),address varchar(60),city varchar(20),state varchar(20),contry varchar(20),totalfee float,paidfee float,duefee float)");
			st.execute("create table admin(user varchar(30),password varchar(40))");
			st.executeUpdate("insert into admin values(\"" + user + "\",\"" + pass + "\")");
			st.close();
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	// main menu of the application
	void menu() {

		char ch = ' ';
		System.out.println("1. Admin Login ");
		System.out.println("2. Accountant Login");
		System.out.println("3. Exit\n");

		while (!Character.isDigit(ch)) {
			System.out.print("Select : ");
			ch = (char) (in.nextLine().charAt(0));
		}
		switch (ch) {
		case '1':
			admin = new Admin(flag);
			break;
		case '2':
			accountant = new Accountant(flag);
			break;
		case '3':
			try {
				finalize();
			} catch (Throwable e) {				
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.out.println("Select correct option ");
			menu();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		in.close();
		if (con!=null) {
			con.close();
			System.out.println("Database connection ended succesfully...");
		} else {
			System.out.println("Exitted from application.....");
		}
	}

	// application startup
	public static void main(String[] args) {
		new FeeReport();
	}
}

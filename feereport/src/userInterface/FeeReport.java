package userInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class FeeReport {
	Scanner in;
	static FeeReport freereport;
	static Admin admin;
	static Accountant accountant;
	static public Connection con;

	public FeeReport() {
		in = new Scanner(System.in);
		freereport = this;
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
			if (checkDB(con)) {
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
			admin = new Admin();
			break;
		case '2':
			accountant = new Accountant();
			break;
		case '3':
			try {
				con.close(); // closing database connection and exist fromthe application
				System.out.println("Database connection ended succesfully...");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
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
		if (!con.isClosed()) {
			con.close();
		}
	}

	// application startup
	public static void main(String[] args) {
		new FeeReport();
	}
}

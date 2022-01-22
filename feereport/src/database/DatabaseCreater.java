package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreater {
	private Connection con;
	public DatabaseCreater(Connection con,String username,String password) {
		this.con = con;
		System.out.println("Creating database ...");
		if(create(username,password)) {
			System.out.println("Database created successfully ....");
			System.out.println("Creating admin ...");
			
		}else System.err.println("Error creating database please check manually, "
				+ "\nif database created please drop it and restart the application.");
		
	}
	
	
	private boolean create(String user,String pass) {

		try {
			Statement st = con.createStatement();
			st.execute("create database freereport");
			st.execute("use freereport");
			st.execute("create table accountant(name varchar(30),email varchar(30),mobile varchar(20),password varchar(50))");
			st.execute("create table student(roll int primary key,name varchar(40),email varchar(30),course varchar(20),"
					+ "contact varchar(10),address varchar(60),city varchar(20),state varchar(20),contry varchar(20),totalfee float,paidfee float,duefee float)");
			st.execute("create table admin(user varchar(30),password varchar(40))");
			st.executeUpdate("insert into admin values(\""+user+"\",\""+pass+"\")");
			st.close();
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	

	public static boolean check(Connection con) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("show databases");
			while(rs.next()) {				
				if(rs.getString(1).equalsIgnoreCase("freereport")) {
					return true;
				}				
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return false;
	}
	
	

}

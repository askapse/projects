package userInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import database.ConnectionGrabber;
import database.DatabaseCreater;

public class FeeReport implements ConnectionGrabber{
	Scanner in;
	static FeeReport freereport;
	static Admin admin;
	static Accountant accountant;
	static public Connection con;
	public FeeReport() {
		in = new Scanner(System.in);
		freereport = this;
		System.out.println("Connecting to database....");
		con = ConnectionGrabber.getConnection();
		//check connection established or not
		if(con!=null) {
			System.out.println("Connection Successful...");
			System.out.println("Checking database ....");			
			if(!DatabaseCreater.check(con)) {
				System.out.print("Enter ADMIN username : ");
				String user = in.nextLine();
				System.out.print("Enter ADMIN password : ");
				String pass = in.nextLine();
				DatabaseCreater dbcr = new DatabaseCreater(con,user,pass);				
			}			
			this.menu();
		}else {
			System.out.println("Connection not established....\nexist from application...");
		}		
	}
	
	
	//main menu of the application
	void menu(){
		
		char ch=' ';
		System.out.println("1. Admin Login ");
		System.out.println("2. Accountant Login");
		System.out.println("3. Exit\n");
		
		
		while(!Character.isDigit(ch)) {			
			System.out.print("Select : ");
			ch = (char)(in.nextLine().charAt(0));
		}
		switch(ch) {
		case '1':
			admin = new Admin();
			break;
		case '2':
			accountant = new Accountant();
			break;
		case '3':
			try {
				con.close();			//closing database connection and exist fromthe application
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
		if(!con.isClosed()) {
			con.close();
		}
		super.finalize();
	}
	//application startup
	public static void main(String[] args) {
		new FeeReport();			
	}	
}

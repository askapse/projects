package userInterface;

import java.util.Scanner;

import database.AdminData;
import database.AdminDataF;
import database.AdminDataMap;
import entities.Accountant;

public class Admin{
	Scanner in;
	AdminDataMap add;
	Admin(boolean flag){
		//check user selection fot the data operations on
		if(flag) add = new AdminData();
		else add = new AdminDataF();
		
		in = new Scanner(System.in);		
		while(!login());
	}
	
	private boolean login() {
		System.out.print("Username : ");
		String user = in.nextLine();
		System.out.print("Password : ");
		String pass = in.nextLine();
		if(add.checkUser(user)) {
			if(add.checkPass(user, pass)) {
				menu();
				return true;
			}else {
				System.err.println("Invalid password\n");
				return false;
			}
		}else {
			System.err.println("Username not found\n");
			return false;
		}
		
		
	}
	
	private void addAC() {
		String name;
		String email;
		String password;
		String mobile;
		Accountant ac = new Accountant();
		boolean flag = false;
		while(!flag) {
			System.out.print("Name : ");
			while(!(name=in.nextLine()).matches("[A-Za-z]+")) {
				System.out.print("\nInvalid name please re-enter : ");
			}
			if(!add.checkACName(name)) {		//check avilability of the name for the accountant
				flag=true;
				ac.setName(name);
			}else {
				System.out.print("\nName already present enter another :: ");
			}
		}
		
		System.out.print("Email : ");
		while(!(email=in.nextLine()).matches("[A-Za-z0-9]+[@][a-z]+[.][a-z]+")) {
			System.out.print("\nInvalid email re-enter : ");
		}
		ac.setEmail(email);
		System.out.print("Password : ");
		while(!((password=in.nextLine()).trim().length()>4)) {
			System.out.print("\nShort password re-enter : ");
		}
		ac.setPassword(password);
		System.out.print("Mobile : ");
		while(!(mobile=in.nextLine()).trim().matches("[0-9]{10}")) {
			System.out.print("\nInvalid mobile : ");
		}
		ac.setContact(mobile);
		
		
		if(add.saveAccountant(ac)) {
			System.out.print("\nAccountant added succesfully ...\n");
		}else System.err.print("\nError please try again later...\n");

		//menu after exist from addAC
		menu();
	}
	
	private void viewAC() {
		Accountant [] ac = add.getAccountantList();
		if(ac.length==0) {
			System.out.println("No recoreds found");
		}else {
			for(Accountant a : ac) {
				System.out.printf("%-30s  %-30s  %-10s  %s\n",a.getName(),a.getEmail(),a.getContact(),a.getPassword());
			}
		}
		//menu open for the admin panel
		menu();
	}
	
	private void logout() {
		FeeReport.admin = null;
		System.out.println("Admin logged off......");
		FeeReport.freereport.menu();		
	}
void menu(){		
		char ch=' ';
		System.out.println("1. Add Accountant ");
		System.out.println("2. View Accountant");
		System.out.println("3. Logout\n");
		
		
		while(!Character.isDigit(ch)) {			
			System.out.print("Select : ");
			ch = (char)(in.nextLine().charAt(0));
		}
		switch(ch) {
		case '1':
			addAC();
			break;
		case '2':
			viewAC();
			break;
		case '3':
			logout();			
			break;
		default:
			System.out.println("Select correct option ");
			menu();
		}					
	}
}

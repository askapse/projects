package userInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

import database.AccountantDataQuery;
import entities.Student;

public class Accountant extends AccountantDataQuery {
	Scanner in;

	Accountant() {
		in = new Scanner(System.in);
		while (!login())
			;
	}

//	accountant authorization 
	private boolean login() {
		System.out.print("Accountant name : ");
		String user = in.nextLine();
		System.out.print("Password : ");
		String pass = in.nextLine();
		if (checkUser(user)) {
			if (checkPass(user, pass)) {
				menu();
				return true;
			} else {
				System.err.println("Invalid password\n");
				return false;
			}
		} else {
			System.err.println("Accountant name not found\n");
			return false;
		}
	}

	private void addStudent() {
		Student st = new Student();
		String name;
		String email;
		String mobile;
		String course;
		String address;
		String city;
		String state;
		String contry;
		String rl;
		boolean flag = false;
		// get name from user
		System.out.println("Student Name : ");
		while (!(name = in.nextLine()).trim().matches("[A-Za-z ]+")) {
			System.out.print("\nInvalid re-enter : ");
		}
		st.setName(name);

		// roll no.
		while (!flag) {
			System.out.print("Student roll no. : ");
			while (!(rl = in.nextLine()).trim().matches("[0-9]{1,}")) {
				System.out.print("Enter valid roll no. : ");
			}
			if (!checkStudent(Integer.parseInt(rl))) {
				st.setRoll(Integer.parseInt(rl));
				flag = true;
			}
		}
		flag = false;

		// get email from user
		System.out.print("Student E-mail : ");
		while (!(email = in.nextLine()).trim().matches("[A-Za-z0-9]+[@][a-z]+[.][a-z]+")) {
			System.out.print("Invalid e-mail re-enter : ");
		}
		st.setEmail(email);

		// get mobile no.
		System.out.print("Student Contact : ");
		while (!(mobile = in.nextLine()).trim().matches("[0-9]{10}")) {
			System.out.print("Invalid re-enter : ");
		}
		st.setContact(mobile);

//		get couser 
		System.out.print("Student course : ");
		while (!(course = in.nextLine()).trim().matches("[A-Za-z]+")) {
			System.out.print("Invalid re-enter : ");
		}
		st.setCourse(course);

		// address
		System.out.print("Student Address : ");
		while (!(address = in.nextLine()).trim().matches("[A-Za-z0-9 -]+")) {
			System.out.print("Invalid characters re-enter : ");
		}
		st.setAddress(address);

		// city
		System.out.print("Student City : ");
		while (!(city = in.nextLine()).trim().matches("[A-Za-z]+")) {
			System.out.print("Invalid characters re-enter : ");
		}
		st.setCity(city);

		// state
		System.out.print("Student State : ");
		while (!(state = in.nextLine()).trim().matches("[A-Za-z]+")) {
			System.out.print("Invalid characters re-enter : ");
		}
		st.setState(state);

		System.out.print("Student Contry : ");
		while (!(contry = in.nextLine()).trim().matches("[A-Za-z ]+")) {
			System.out.print("Invalid characters re-enter : ");
		}
		st.setContry(contry);

//		Total fee of the student 
		System.out.print("Total fee : ");
		while (!flag) {
			try {
				float tf = in.nextFloat();
				st.setTfee(tf);
				flag = true;
			} catch (InputMismatchException e) {
				System.err.println("Input valid amount : ");
			}
		}
		flag = false;

		// paid fee
		System.out.print("Paid fee : ");
		while (!flag) {
			try {
				float tf = in.nextFloat();
				st.setPaid(tf);
				flag = true;
			} catch (InputMismatchException e) {
				System.err.println("Input valid amount : ");
			}
		}

		// set due fee
		st.setDue((st.getTfee() - st.getPaid()));

		if (saveStudent(st))
			System.out.print("Student added successfully...\n");
		else
			System.err.println("Error adding student ....\n");

		// manu display
		menu();
	}

	// view student in the system
	private void viewStudent() {
		Student st[] = getStudent();
		if (st.length == 0)
			System.out.println("No record found ....");
		else {
//			System.out.printf("%-4d %-25s %-25s %-10s %-10s %-30s %-15s %-15s %-15s %8s %8s %8s",
//					"Roll","Name",s.getEmail(),s.getCourse(),s.getContact(),s.getContact(),
//					s.getAddress(),s.getCity(),s.getState(),s.getContry(),s.getTfee(),s.getPaid(),s.getDue());
			for (Student s : st) {
				System.out.printf("%-4d %-25s %-25s %-10s %-10s %-30s %-15s %-15s %-15s %8s %8s %8s\n", s.getRoll(),
						s.getName(), s.getEmail(), s.getCourse(), s.getContact(), s.getAddress(), s.getCity(),
						s.getState(), s.getContry(), s.getTfee(), s.getPaid(), s.getDue());
			}
		}

//		menu of the accountant
		menu();
	}

	private void editStudent() {
		String rl;
		int r = 0;
		boolean flag = false;
		// roll no.
		while (!flag) {
			System.out.print("Student roll no. : ");
			while (!(rl = in.nextLine()).trim().matches("[0-9]{1,}")) {
				System.out.print("Enter valid roll no. : ");
			}
			if (checkStudent(Integer.parseInt(rl))) {
				r = (Integer.parseInt(rl));
				flag = true;
			}
		}
		flag = false;

		// get student previous details
		Student s = getStudent(r);
		if (s != null) {
			while (!flag) {
				char ch = ' ';
				System.out.println("1. Name ");
				System.out.println("2. E-mail ");
				System.out.println("3. Contact ");
				System.out.println("4. Course ");
				System.out.println("5. Address");
				System.out.println("6. City");
				System.out.println("7. State  ");
				System.out.println("8. Contry ");
				System.out.println("9. Exit ");
				System.out.print("\nSelect :");
				while (!Character.isDigit(ch)) {
					try {
						System.out.print("Select : ");
						ch = (char) ((in.nextLine()).trim().charAt(0));
					} catch (Exception e) {
						ch = ' ';
						System.out.println();
					}
				}

				switch (ch) {
				case '1':

					String name;
					System.out.print("\nName : " + s.getName());
					System.out.print("\nNew Name : ");
					while (!(name = in.nextLine()).trim().matches("[A-Za-z ]+")) {
						System.out.print("\nInvalid re-enter : ");
					}
					if (updateStudent(r, "name", name))
						System.out.println("Name updated successfully ...");
					else
						System.err.println("Error.... ");
					break;
				case '2':
					String email;
					System.out.print("\nE-mail : " + s.getEmail());
					System.out.print("\nnew E-mail : ");
					while (!(email = in.nextLine()).trim().matches("[A-Za-z0-9]+[@][a-z]+[.][a-z]+")) {
						System.out.print("Invalid e-mail re-enter : ");
					}
					if (updateStudent(r, "email", email))
						System.out.println("E-mail updated successfully ...");
					else
						System.err.println("Error.... ");
					break;
				case '3':
					String mobile;
					System.out.print("\nContact : " + s.getContact());
					System.out.print("\nnew Contact : ");
					while (!(mobile = in.nextLine()).trim().matches("[0-9]{10}")) {
						System.out.print("Invalid re-enter : ");
					}
					if (updateStudent(r, "contact", mobile))
						System.out.println("Contact updated successfully ...");
					else
						System.err.println("Error.... ");
					break;
				case '4':
					String course;
					System.out.print("\nCourse : " + s.getCourse());
					System.out.print("\nnew course : ");
					while (!(course = in.nextLine()).trim().matches("[A-Za-z]+")) {
						System.out.print("Invalid re-enter : ");
					}
					if (updateStudent(r, "course", course))
						System.out.println("Course updated successfully ...");
					else
						System.err.println("Error.... ");
					break;
				case '5':
					String address;
					System.out.print("\nAddress : " + s.getAddress());
					System.out.print("\nnew Address : ");
					while (!(address = in.nextLine()).trim().matches("[A-Za-z0-9 -]+")) {
						System.out.print("Invalid characters re-enter : ");
					}
					if (updateStudent(r, "address", address))
						System.out.println("Address updated successfully ...");
					else
						System.err.println("Error.... ");
					break;
				case '6':
					String city;
					System.out.print("\nCity : " + s.getCity());
					System.out.print("\nnew City : ");
					while (!(city = in.nextLine()).trim().matches("[A-Za-z]+")) {
						System.out.print("Invalid characters re-enter : ");
					}
					if (updateStudent(r, "city", city))
						System.out.println("City updated successfully ...");
					else
						System.err.println("Error.... ");
					break;
				case '7':
					String state;
					System.out.print("\nState : " + s.getState());
					System.out.print("\nnew State : ");
					while (!(state = in.nextLine()).trim().matches("[A-Za-z]+")) {
						System.out.print("Invalid characters re-enter : ");
					}
					if (updateStudent(r, "state", state))
						System.out.println("State updated successfully ...");
					else
						System.err.println("Error.... ");
					break;
				case '8':
					String contry;
					System.out.print("\nContry : ");
					System.out.print("\nnew Contry : ");
					while (!(contry = in.nextLine()).trim().matches("[A-Za-z ]+")) {
						System.out.print("Invalid characters re-enter : ");
					}
					if (updateStudent(r, "contry", contry))
						System.out.println("Contry updated successfully ...");
					else
						System.err.println("Error.... ");
					break;
				case '9':
					flag = true;
					break;
				default:
					System.out.println("Select correct option ....\n");
				}

			}
		} else {
			System.out.print("No record found");
		}

		// menu
		menu();
	}

	// due fee fill here by the student roll no.
	private void dueFee() {
		String rl;
		int r = 0;
		boolean flag = false;
		// roll no.
		while (!flag) {
			System.out.print("Student roll no. : ");
			while (!(rl = in.nextLine()).trim().matches("[0-9]{1,}")) {
				System.out.print("Enter valid roll no. : ");
			}
			if (checkStudent(Integer.parseInt(rl))) {
				r = (Integer.parseInt(rl));
				flag = true;
			}
		}
		flag = false;
		float paiddue[] = duePaidFee(r); // 0 index is paid fee and 1 index is due fee
		if (paiddue.length == 0) {
			System.out.println("No record found ");
		} else {
			float fee = 0;
			System.out.println("Paid : " + paiddue[0] + " Due : " + paiddue[1]);
			System.out.print("Add amount : ");
			while (!flag) {
				try {
					fee = in.nextFloat();
					if (paiddue[1] < fee || fee < 0)
						throw new ArithmeticException("You entered high amount");
					flag = true;
				} catch (InputMismatchException e) {
					System.err.print(e.getMessage() + "\nRe-enter amount : ");
				} catch (ArithmeticException e) {
					System.out.println(e.getMessage() + "\nRe-enter amount : ");
				}
			}

			// run update database due fee from here
			if (updateDueFee(r, paiddue[0] + fee, paiddue[1] - fee)) {
				System.out.print("\nDue fee updated succesfully of roll no. :" + r);
			} else {
				System.err.print("\nError updating due fee ");
			}
		}
		// menu open here for the accountant
		menu();
	}

	// logout accountant
	private void logout() {
		FeeReport.accountant = null;
		System.out.println("Accountant logged off......");
		FeeReport.freereport.menu();
	}

	void menu() {
		char ch = ' ';
		System.out.println("\n1. Add Student");
		System.out.println("2. View Student");
		System.out.println("3. Edit Student");
		System.out.println("4. Due Fee");
		System.out.println("5. Logout\n");

		while (!Character.isDigit(ch)) {
			try {
				System.out.print("Select : ");
				ch = (char) ((in.nextLine()).trim().charAt(0));
			} catch (Exception e) {
				ch = ' ';
				System.out.println();
			}
		}
		switch (ch) {
		case '1':
			addStudent();
			break;
		case '2':
			viewStudent();
			break;
		case '3':
			editStudent();
			break;
		case '4':
			dueFee();
			break;
		case '5':
			logout();
			break;
		default:
			System.out.println("Select correct option ....");
			menu();
		}
	}

}

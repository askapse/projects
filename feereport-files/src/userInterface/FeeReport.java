package userInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class FeeReport {
	Scanner in;
	static FeeReport freereport;
	static Admin admin;
	static Accountant accountant;
	static public File datadir;

	public FeeReport() {
		in = new Scanner(System.in);
		freereport = this;

		// check connection established or not
		System.out.println("Checking filesystem.....");
		if (!checkDB()) {
			System.out.print("Enter ADMIN username : ");
			String user = in.nextLine();
			System.out.print("Enter ADMIN password : ");
			String pass = in.nextLine();
			System.out.println("Creating filesystem ...");
			if (createDB(user, pass)) {
				System.out.println("Filesystem created successfully ....");
				System.out.println("Creating admin ...");

			} else
				System.err.println("Error creating database please check manually, "
						+ "\nif database created please drop it and restart the application.");
		}
		this.menu();
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
						for(File f : fp) {
							if(f.getName().equals("student")) s = true;
							if(f.getName().equals("accountant")) a = true;
						}
					return a&&s;						
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
	private boolean createDB(String user, String pass) {

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
				usrps[0] = user.toCharArray();
				usrps[1] = pass.toCharArray();

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
				finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.out.println("Select correct option ");
			menu();
		}
	}

	// createDB function overload for the file management

	@Override
	protected void finalize() throws Throwable {
		in.close();
		System.out.println("Exitted from application.....");
	}

	// application startup
	public static void main(String[] args) {
		new FeeReport();
	}
}

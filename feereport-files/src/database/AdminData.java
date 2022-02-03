package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import entities.Accountant;
import userInterface.FeeReport;
import userInterface.FileMissingWhileRuntime;

public class AdminData {
	@SuppressWarnings("unchecked")
	private List<Accountant> getAccountantData() {
		List<Accountant> acdata = null;
		try {
			File f = new File(FeeReport.datadir.getAbsolutePath() + "\\accountant");
			if (!f.exists())
				throw new FileMissingWhileRuntime();
			FileInputStream fin = new FileInputStream(f);
			if (fin.available() > 0) {
				ObjectInputStream oin = new ObjectInputStream(fin);
				try {
					acdata = (List<Accountant>) oin.readObject();
				} catch (IOException | ClassNotFoundException e) {
					System.err.println(e.getMessage());
				}
				oin.close();
			}
//			fin.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return acdata;
	}

	private boolean saveAccountantData(List<Accountant> acdata) {
		try {
			File f = new File(FeeReport.datadir.getAbsolutePath() + "\\accountant");
			if (!f.exists())
				throw new FileMissingWhileRuntime();
			FileOutputStream fo = new FileOutputStream(f);
			ObjectOutputStream out = new ObjectOutputStream(fo);
			out.writeObject(acdata);
			fo.close();
			out.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	protected boolean checkUser(String user) {
		try {
			File f = new File(FeeReport.datadir.getAbsolutePath() + "\\admin");
			if (!f.exists()) {
				throw new FileMissingWhileRuntime();
			}
			FileInputStream fin = new FileInputStream(f);
			ObjectInputStream oin = new ObjectInputStream(fin);
			char[][] chr = (char[][]) oin.readObject();
			fin.close();
			oin.close();
			String p = new String();
			for (char c : chr[0]) {
				p = new String(p + "" + c);
			}

			if (p.equals(user))
				return true;

		} catch (FileMissingWhileRuntime e) {
			System.err.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	protected boolean checkPass(String user, String pass) {
		try {
			File f = new File(FeeReport.datadir.getAbsolutePath() + "\\admin");
			if (!f.exists()) {
				throw new FileMissingWhileRuntime();
			}
			FileInputStream fin = new FileInputStream(f);
			ObjectInputStream oin = new ObjectInputStream(fin);
			char[][] chr = (char[][]) oin.readObject();
			fin.close();
			oin.close();

			String u = new String();
			for (char c : chr[0]) {
				u = new String(u + "" + c);
			}

			String p = new String();
			for (char c : chr[1]) {
				p = new String(p + "" + c);
			}

			if (u.equals(user) && p.equals(pass))
				return true;
		} catch (FileMissingWhileRuntime e) {
			System.err.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	protected boolean checkACName(String name) {
		List<Accountant> acdata = getAccountantData();
		if (acdata != null)
			for (Accountant a : acdata) {
				if (a.getName().equals(name.trim()))
					return true;
			}
		return false;
	}

	protected boolean saveAccountant(Accountant ac) {
		try {
			List<Accountant> acdata = getAccountantData();
			if (acdata == null)
				acdata = new ArrayList<Accountant>();
			acdata.add(ac);
			saveAccountantData(acdata);
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	protected Accountant[] getAccountantList() {
		try {
			Accountant[] ac = new Accountant[0];
			List<Accountant> acdata = getAccountantData();
			for (Accountant a : acdata) {
				ac = Arrays.copyOf(ac, ac.length + 1);
				ac[ac.length - 1] = a;
			}
			return ac;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return new Accountant[0];
		}
	}
}

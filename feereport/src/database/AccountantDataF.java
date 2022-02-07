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
import entities.Student;
import userInterface.FeeReport;

public class AccountantDataF implements AccountantDataMap{
	@SuppressWarnings("unchecked")
	private List<Student> getStudentData() {
		List<Student> stdata = null;
		try {
			File f = new File(FeeReport.datadir.getAbsolutePath() + "\\student");
			if (!f.exists())
				throw new IOException("File missing");
			FileInputStream fin = new FileInputStream(f);
			if (fin.available() > 0) {
				ObjectInputStream oin = new ObjectInputStream(fin);
				try {
					stdata = (List<Student>) oin.readObject();
				} catch (IOException | ClassNotFoundException e) {					
					System.err.println(e.getMessage());
				}
				oin.close();
				fin.close();
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return stdata;
	}

	private boolean saveStudentData(List<Student> stdata) {
		try {
			File f = new File(FeeReport.datadir.getAbsolutePath() + "\\student");
			if (!f.exists())
				throw new IOException("File missing");
			FileOutputStream fo = new FileOutputStream(f);
			ObjectOutputStream out = new ObjectOutputStream(fo);
			out.writeObject(stdata);
			fo.close();
			out.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	// check username avilability
	public boolean checkUser(String user) {
		Accountant[] acd = new AdminDataF().getAccountantList();
		for (Accountant a : acd) {
			if (a.getName().equals(user.trim()))
				return true;
		}
		return false;
	}

	// checking username and password fromthe user is matched or not
	public boolean checkPass(String user, String pass) {
		Accountant[] acd = new AdminDataF().getAccountantList();
		for (Accountant a : acd) {
			if (a.getName().equals(user.trim()) && a.getPassword().equals(pass.trim()))
				return true;
		}
		return false;
	}

	// add new student to the database
	public boolean saveStudent(Student s) {
		List<Student> stdata = getStudentData();
		if (stdata == null)
			stdata = new ArrayList<Student>();
		stdata.add(s);
		return saveStudentData(stdata);
	}

	// get student from the database and return a array of the student object
	public Student[] getStudent() {
		List<Student> stdata = getStudentData();
		Student s[] = new Student[0];
		if (stdata != null)
			for (Student st : stdata) {
				s = Arrays.copyOf(s, s.length + 1);
				s[s.length - 1] = st;
			}
		return s;
	}

	public Student getStudent(int roll) {
		List<Student> stdata = getStudentData();
		if (stdata != null)
			for (Student st : stdata)
				if (st.getRoll() == roll)
					return st;

		return null;
	}

	// check student by the roll no.
	public boolean checkStudent(int roll) {
		List<Student> stdata = getStudentData();
		if (stdata != null)
			for (Student st : stdata)
				if (st.getRoll() == roll)
					return true;

		return false;
	}

	public float[] duePaidFee(int roll) {
		List<Student> stdata = getStudentData();
		if (stdata != null)
			for (Student st : stdata)
				if (st.getRoll() == roll) {
					float f[] = new float[2];
					f[0] = st.getPaid(); // it is paid fee
					f[1] = st.getDue(); // it is due fee
					return f;
				}
		return new float[0];
	}

	public boolean updateDueFee(int roll, float paid, float due) {
		List<Student> stdata = getStudentData();
		if (stdata != null)
			for (Student st : stdata)
				if (st.getRoll() == roll) {
					st.setPaid(paid);
					st.setDue(due);
					return saveStudentData(stdata);
				}
		return false;
	}

	public boolean updateStudent(int roll, String field, String value) {
		List<Student> stdata = getStudentData();
		if (stdata != null)
			for (Student st : stdata)
				if (st.getRoll() == roll) {
					switch (field) {
					case "name":
						st.setName(value);
						break;
					case "email":
						st.setEmail(value);
						break;
					case "contact":
						st.setContact(value);
						break;
					case "course":
						st.setCourse(value);
						break;
					case "address":
						st.setAddress(value);
						break;
					case "city":
						st.setCity(value);
						break;
					case "state":
						st.setState(value);
						break;
					case "contry":
						st.setContry(value);
						break;					
					}
					return saveStudentData(stdata);
				}
		return false;
	}
}

package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import entities.Student;
import userInterface.FeeReport;

public class AccountantData implements AccountantDataMap{
	// check username avilability
	public boolean checkUser(String user) {
		try {
			Statement st = FeeReport.con.createStatement();
			st.execute("use freereport");
			ResultSet rs = st.executeQuery("select name from accountant where name='" + user + "'");

			if (rs.next()) {
				st.close();
				return true;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return false;
	}

	// checking username and password fromthe user is matched or not
	public boolean checkPass(String user, String pass) {
		try {
			Statement st = FeeReport.con.createStatement();
			ResultSet rs = st.executeQuery("select name,password from accountant where " + "name='" + user + "'");
			while (rs.next()) {
				if (rs.getString(2).equals(pass))
					return true;
				else
					return false;
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return false;
	}

	// add new student to the database
	public boolean saveStudent(Student s) {
		try {
			PreparedStatement pst = FeeReport.con
					.prepareStatement("insert into student values(?,?,?,?,?,?,?,?,?,?,?,?)");

			pst.setInt(1, s.getRoll());
			pst.setString(2, s.getName());
			pst.setString(3, s.getEmail());
			pst.setString(4, s.getCourse());
			pst.setString(5, s.getContact());
			pst.setString(6, s.getAddress());
			pst.setString(7, s.getCity());
			pst.setString(8, s.getState());
			pst.setString(9, s.getContry());
			pst.setFloat(10, s.getTfee());
			pst.setFloat(11, s.getPaid());
			pst.setFloat(12, s.getDue());

			pst.execute();
			pst.close();
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	// get student from the database and return a array of the student object
	public Student[] getStudent() {
		try {
			Statement st = FeeReport.con.createStatement();
			ResultSet rs = st.executeQuery("select *from student");
			Student s[] = new Student[0];
			while (rs.next()) {
				s = Arrays.copyOf(s, s.length + 1);
				s[s.length - 1] = new Student();
				s[s.length - 1].setRoll(rs.getInt(1));
				s[s.length - 1].setName(rs.getString(2));
				s[s.length - 1].setEmail(rs.getString(3));
				s[s.length - 1].setCourse(rs.getString(4));
				s[s.length - 1].setContact(rs.getString(5));
				s[s.length - 1].setAddress(rs.getString(6));
				s[s.length - 1].setCity(rs.getString(7));
				s[s.length - 1].setState(rs.getString(8));
				s[s.length - 1].setContry(rs.getString(9));
				s[s.length - 1].setTfee(rs.getFloat(10));
				s[s.length - 1].setPaid(rs.getFloat(11));
				s[s.length - 1].setDue(rs.getFloat(12));
			}
			st.close();
			return s;

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return new Student[0];
		}
	}

	public Student getStudent(int roll) {
		try {
			Statement st = FeeReport.con.createStatement();
			ResultSet rs = st.executeQuery("select * from student where roll="+roll);
			if(rs.next()) {
				Student s = new Student();				
				s.setRoll(rs.getInt(1));
				s.setName(rs.getString(2));
				s.setEmail(rs.getString(3));
				s.setCourse(rs.getString(4));
				s.setContact(rs.getString(5));
				s.setAddress(rs.getString(6));
				s.setCity(rs.getString(7));
				s.setState(rs.getString(8));
				s.setContry(rs.getString(9));
				s.setTfee(rs.getFloat(10));
				s.setPaid(rs.getFloat(11));
				s.setDue(rs.getFloat(12));
				return s;
			}
			return null;
		}catch(SQLException e) {
			System.err.print(e.getMessage()+'\n');
			return null;
		}
	}
	// check student by the roll no.
	public boolean checkStudent(int roll) {
		try {
			Statement st = FeeReport.con.createStatement();
			ResultSet rs = st.executeQuery("select roll from student where roll=" + roll);
			if (rs.next())
				return true;
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public float[] duePaidFee(int roll) {
		try {
			Statement st = FeeReport.con.createStatement();
			ResultSet rs = st.executeQuery("select paidfee,duefee from student where roll=" + roll);
			if (rs.next()) {
				float f[] = new float[2];
				f[0] = rs.getFloat(1); // it is paid fee
				f[1] = rs.getFloat(2); // it is due fee
				return f;
			}
			return new float[0];
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return new float[0];
		}
	}

	public boolean updateDueFee(int roll, float paid, float due) {
		try {
			PreparedStatement pst = FeeReport.con.prepareStatement("update student set paidfee=? , duefee=? where roll=?");
			pst.setFloat(1, paid);
			pst.setFloat(2, due);
			pst.setInt(3, roll);
			pst.execute();
			return true;
		}catch(SQLException e) {
			System.err.print("\n"+e.getMessage());
			return false;
		}
	}
	
	public boolean updateStudent(int roll,String field,String value) {
		try {
			PreparedStatement pst = FeeReport.con.prepareStatement("update student set "+field+"=? where roll=?");
			pst.setString(1, value);
			pst.setInt(2, roll);
			if(pst.executeUpdate()>0) return true;
			return false;
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}		
	}

	
}

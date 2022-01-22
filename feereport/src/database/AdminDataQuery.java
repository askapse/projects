package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import entities.Accountant;
import userInterface.FeeReport;

public class AdminDataQuery {
	protected boolean checkUser(String user) {
		try {
		Statement st = FeeReport.con.createStatement();
		st.execute("use freereport");
		ResultSet rs = st.executeQuery("select user from admin where user='"+user+"'");
		
		if(rs.next()) {
			st.close();
			return true;
		}
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return false;
	}
	
	protected boolean checkPass(String user,String pass) {
		try {
			Statement st = FeeReport.con.createStatement();
			ResultSet rs = st.executeQuery("select user,password from admin where "
					+ "user='"+user+"'");
			while(rs.next()) {
				if(rs.getString(2).equals(pass)) return true;
				else return false;
			}
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return false;
	}
	
	protected boolean checkACName(String name){
		try {
			Statement st = FeeReport.con.createStatement();
			ResultSet rs = st.executeQuery("select name from accountant where name='"+name+"'");
			if(rs.next()) return true;
			return false;
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	protected boolean saveAccountant(Accountant ac){
		try {
			PreparedStatement pst = FeeReport.con.prepareStatement("insert into accountant values(?,?,?,?)");
			pst.setString(1, ac.getName());
			pst.setString(2,ac.getEmail());
			pst.setString(3, ac.getContact());
			pst.setString(4, ac.getPassword());
			
			pst.execute();
			return true;
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}	
	}
	
	protected Accountant[] getAccountantList() {
		try {
			Statement st = FeeReport.con.createStatement();
			ResultSet rs = st.executeQuery("select *from accountant");
			Accountant [] ac = new Accountant[0];			
			while(rs.next()) {							
				ac=Arrays.copyOf(ac, ac.length+1);
				ac[ac.length-1]=new Accountant();
				ac[ac.length-1].setName(rs.getString(1));
				ac[ac.length-1].setEmail(rs.getString(2));
				ac[ac.length-1].setContact(rs.getString(3));
				ac[ac.length-1].setPassword(rs.getString(4));
			}
			return ac;
		}catch (SQLException e) {
			System.err.println(e.getMessage());
			return new Accountant[0];
		}
	}
	
	
//	public static void main(String[] args) throws SQLException{
//		Connection con = ConnectionGrabber.getConnection();
//		Statement st = con.createStatement();
//		st.execute("use freereport");
//		st.close();
//		Statement st1 = con.createStatement();
//		ResultSet rs = st1.executeQuery("show tables");
//		while(rs.next()) {
//			System.out.println(rs.getString(1));
//		}
//		st1.close();
//		con.close();
//	}
}

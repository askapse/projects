package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Message {
	Connection con;

	public Message() {
		try {
			DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/messaging", "abhi", "kali");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	boolean createTable(String tname) {
		try {
			PreparedStatement st = con.prepareStatement("create table " + tname
					+ " (id bigint not null auto_increment primary key,f long,t long,msg text,date datetime)");
			st.executeUpdate();
			st.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	boolean dropTable(String tname) {
		try {
			PreparedStatement st = con.prepareStatement("drop table " + tname);
			st.executeUpdate();
			st.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean checkTable(String tname) {
		try {
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("show tables");
			while (rs.next()) {
				if (tname.equalsIgnoreCase(rs.getString(1)))
					return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(new Message().checkTable("abhi"));
	}

	@Override
	protected void finalize() throws Throwable {
		con.close();
		super.finalize();
	}
}

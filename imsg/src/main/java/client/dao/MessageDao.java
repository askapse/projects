package client.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;

import client.entities.Message;

public class MessageDao {
	public static MessageDao msgdao;
	Connection con;

	private MessageDao() {
		try {
			DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/messaging", "abhi", "kali");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static {
		msgdao = new MessageDao();
	}

	public boolean createTable(String tname) {
		try {
			PreparedStatement st = con.prepareStatement("create table " + tname
					+ " (id int not null auto_increment,senderid long,type char,msg text,date datetime,primary key(id))");
			st.executeUpdate();
			st.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean dropTable(String tname) {
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

	public int sendMessage(Message msg, long classid) {
		try {
			PreparedStatement pr = con
					.prepareStatement("insert into message" + classid + "(senderid,type,msg,date) values(?,?,?,?)");
			pr.setInt(1, (int) msg.getSenderId());
			pr.setString(2, msg.getType() + "");
			pr.setString(3, msg.getText());
			pr.setString(4, msg.getTime().toString());
			if (pr.executeUpdate() == 1) {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select max(id) from message" + classid);
				rs.next();
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}

	public List<Message> getMessagesAfter(long classid, long lmsg) {
		List<Message> msgs = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs;
			if (lmsg == 0) {
				rs = st.executeQuery("select *from message" + classid + " where id <= (select max(id) from message"
						+ classid + ") and id >(select max(id) from message" + classid + ")-25");
			} else {
				rs = st.executeQuery(" select *from message" + classid + " where id >" + lmsg);
			}

			while (rs.next()) {
				Message msg = new Message();
				msg.setId(rs.getLong(1));
				msg.setSenderId(rs.getLong(2));
				msg.setType(rs.getString(3).charAt(0));
				msg.setText(rs.getString(4));
				msg.setTime(LocalDateTime.parse(rs.getString(5).split(" ")[0]+"T"+rs.getString(5).split(" ")[1]));
				msgs.add(msg);
			}

			return msgs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Message> getMessagesBefore(long classid, long fmsg) {
		List<Message> msgs = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs;
			if (fmsg == 0 || fmsg == 1) {
				return msgs;
			} else {
				rs = st.executeQuery(" select *from message" + classid + " where id <" + fmsg +" and id >= "+(fmsg-25));
			}

			while (rs.next()) {
				Message msg = new Message();
				msg.setId(rs.getLong(1));
				msg.setSenderId(rs.getLong(2));
				msg.setType(rs.getString(3).charAt(0));
				msg.setText(rs.getString(4));
				msg.setTime(LocalDateTime.parse(rs.getString(5).split(" ")[0]+"T"+rs.getString(5).split(" ")[1]));
				msgs.add(msg);
			}
			Collections.reverse(msgs);
			return msgs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

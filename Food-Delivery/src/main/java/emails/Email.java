package emails;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	String to;
	String subject;
	String msg;

	//set email address to send the email.........
	public void setTo(String to) {
		this.to = to;
	}

	//set email body for the email ............
	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	//set subject of the email........
	public void setSubject(String subject) {
		this.subject = subject;
	}

	
	//function to send the email to the provided email address ........
	public boolean send() {
		String from = "akapse99@gmail.com";
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "affkofanmclpcbaj");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(from + "," + to));
			message.setSubject(subject);
			message.setText(msg);

			Transport.send(message);

			return true;

		} catch (Exception e) {			
			return false;
		}
	}

}

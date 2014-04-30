package controller;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EMailController {
	
	String email;
	String betreff;
	String text;
	
	
	
	
	
	
	Properties props = System.getProperties();
	props.setProperty("mail.smtp.host", "localhost");
	props.setProperty("mail.transport.protocol", "smtp");

	Session session = Session.getDefaultInstance(props, null);
	MimeMessage message = new MimeMessage(session);

	try {
		message.setFrom(new InternetAddress(request.getParameter("E-Mail")));
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress("kontakt@saatgutonline.de"));
		message.setSubject(request.getParameter("Betreff"));
		message.setSentDate(new Date());
		message.setContent("<h1>Kontaktanfrage</h1><br/>" + "Nachricht von" + " " 
						+ request.getParameter("Anrede") + " " 
						+ request.getParameter("Vorname") + " " 
						+ request.getParameter("Nachname")
						+ "<br><b>E-Mail: </b>" + request.getParameter("E-Mail") 
						+ "<br><b>Betreff: </b>" + request.getParameter("Betreff") 
						+ "<br><b>Nachricht: </b>" + request.getParameter("Nachricht"), "text/html");
		Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
	}
	}
}
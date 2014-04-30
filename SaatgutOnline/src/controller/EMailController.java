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
	
	String emailadresseAbsender;
	String betreff;
	String emailadresseEmpfaenger;
	String nachricht;
	
	String anrede;
	String vorname;
	String nachname;
	
	 public EMailController() {
		 
	 }
	
	public void sendeEmail (String emailadresseAbsender, String betreff, 
			String emailadresseEmpfaenger, String nachricht) {
	
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "localhost");
		props.setProperty("mail.transport.protocol", "smtp");
	
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);
	
		try {
			message.setFrom(new InternetAddress(emailadresseAbsender));
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(emailadresseEmpfaenger));
			message.setSubject(betreff);
			message.setSentDate(new Date());
			message.setContent(nachricht, "text/html");
			Transport.send(message);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
		}
	}
	
	public void sendeEmail (String emailadresseAbsender, String betreff, 
			String emailadresseEmpfaenger, String nachricht, String anrede, String vorname, String nachname) {
		
		nachricht = 	"<h1>Kontaktanfrage</h1><br/>" + "Nachricht von" + " " 
									+ anrede + " " 
									+ vorname + " " 
									+ nachname
									+ "<br><b>E-Mail: </b>" + emailadresseAbsender
									+ "<br><b>Betreff: </b>" + betreff
									+ "<br><b>Nachricht: </b>" + nachricht;
		
		sendeEmail (emailadresseAbsender, betreff, emailadresseEmpfaenger, nachricht);
	}
	
	
		
}
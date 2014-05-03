package controller;

import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Diese Klasse verwaltet die E-Mail Funktionalitäten.
 * 
 * @author Anja
 *
 */
public class EmailController {
	
	/**
	 * RegEx für E-Mail Validierung
	 */
	private static String EmailRegel = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
			+ "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	/**
	 * Pattern und Matcher für E-Mail Validierung
	 */
	private Pattern pattern;
	private Matcher matcher;
	
	/**
	 * Konstruktor für den EmailController.
	 */
	public EmailController() {

	}
	
	/**
	 * Diese Methode überprüft E-Mail-Adressen auf ihre Gültigkeit.
	 * 
	 * @param String e-mail
	 * @return boolean
	 * 
	 * @author Anja
	 * 
	 */
	public boolean validiereEmail(String email) {
		pattern = Pattern.compile(EmailRegel);
		matcher = pattern.matcher(email);
		return matcher.matches();			
	}	
	
	/**
	 * Diese Methode versendet E-Mails.
	 * 
	 * @param emailadresseAbsender
	 * @param betreff
	 * @param emailadresseEmpfaenger
	 * @param nachricht
	 * 
	 * @throws AdressException
	 * @throws MessagingException
	 * 
	 * @author Anja
	 * 
	 */
	public void sendeEmail(String emailadresseAbsender, String betreff,
			String emailadresseEmpfaenger, String nachricht) {

		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "localhost");
		props.setProperty("mail.transport.protocol", "smtp");

		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(emailadresseAbsender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailadresseEmpfaenger));
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

	
	/**
	 * Diese Methode versendet E-Mails und formatiert die Ausgabe.
	 * 
	 * @param emailadresseAbsender
	 * @param betreff
	 * @param emailadresseEmpfaenger
	 * @param nachricht
	 * @param anrede
	 * @param vorname
	 * @param nachname
	 * 
	 * @author Anja
	 * 
	 */
	public void sendeEmail(String emailadresseAbsender, String betreff,
			String emailadresseEmpfaenger, String nachricht, String anrede, String vorname,
			String nachname) {

		nachricht = "<h1>Kontaktanfrage</h1>" + "Nachricht von" + " " + anrede + " " + vorname + " "
				+ nachname + "<br>" + "<table><tr><td><b>E-Mail: </b></td>" + "<td>" + emailadresseAbsender
				+ "</td></tr>" + "<br><tr><td><b>Betreff: </b></td>" + "<td>" + betreff + "</td></tr>"
				+ "<br><tr><td><b>Nachricht: </b></td>" + "<td>" + nachricht + "</td></tr><table>";

		sendeEmail(emailadresseAbsender, betreff, emailadresseEmpfaenger, nachricht);
	}
	
}
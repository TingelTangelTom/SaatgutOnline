package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/KontaktFormular")
public class KontaktFormular extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public KontaktFormular() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

//		Properties props = System.getProperties();
//		props.setProperty("mail.smtp.host", "localhost");
//		props.setProperty("mail.transport.protocol", "smtp");
//
//		Session session = Session.getDefaultInstance(props, null);
//		MimeMessage message = new MimeMessage(session);
//
//		try {
//			message.setFrom(new InternetAddress(request.getParameter("E-Mail")));
//			message.addRecipient(Message.RecipientType.TO,
//					new InternetAddress("kontakt@saatgutonline.de"));
//			message.setSubject(request.getParameter("Betreff"));
//			message.setSentDate(new Date());
//			message.setContent("<h1>Kontaktanfrage</h1><br/>" + "Nachricht von" + " " 
//					+ request.getParameter("Anrede") + " " 
//					+ request.getParameter("Vorname") + " " 
//					+ request.getParameter("Nachname")
//					+ "<br><b>E-Mail: </b>" + request.getParameter("E-Mail") 
//					+ "<br><b>Betreff: </b>" + request.getParameter("Betreff") 
//					+ "<br><b>Nachricht: </b>" + request.getParameter("Nachricht"), "text/html");
//			Transport.send(message);
//		} catch (AddressException e) {
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
		out.println("Vielen Dank für ihre Nachricht.");
		out.println("<a href=\"/SaatgutOnline/IndexPlatzhalter\">Hauptseite</a>"); 
								// TODO : Link aktualisieren
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}

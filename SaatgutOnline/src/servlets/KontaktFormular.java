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

import controller.EMailController;

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
		
		EMailController eMailController = new EMailController();
		eMailController.sendeEmail(request.getParameter("E-Mail"), 
				request.getParameter("Betreff"), "kontakt@saatgutonline.de", 
				request.getParameter("Nachricht"), request.getParameter("Anrede"), 
				request.getParameter("Vorname"), request.getParameter("Nachname"));


		out.println("Vielen Dank für ihre Nachricht.");
		out.println("<a href=\"/SaatgutOnline/IndexPlatzhalter\">Hauptseite</a>"); 
								// TODO : Link aktualisieren
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}

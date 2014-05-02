package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.KontaktFormularVerarbeitungView;
import controller.EmailController;

public class KontaktFormularVerarbeitungController {

	public KontaktFormularVerarbeitungController(HttpServletRequest request,
			HttpServletResponse response) {

		// Daten aus Kontakformular als E-Mail senden
		EmailController emailController = new EmailController();
		emailController.sendeEmail(request.getParameter("E-Mail"), request.getParameter("Betreff"),
				"kontakt@saatgutonline.de", request.getParameter("Nachricht"),
				request.getParameter("Anrede"), request.getParameter("Vorname"),
				request.getParameter("Nachname"));

		KontaktFormularVerarbeitungView kontaktFormularVerarbeitungView = new KontaktFormularVerarbeitungView(
				request, response);
		if (emailController.validiereEmail(request.getParameter("E-Mail"))) {
			kontaktFormularVerarbeitungView.outKontaktVerarbeitungView();
			System.out.println("Klappt :)"); //TODO : remove
		} 
		else {
			kontaktFormularVerarbeitungView.outKontaktVerarbeitungViewUngueltig();
			System.out.println("Klappt nicht :("); //TODO : remove
		}
		
	}

}

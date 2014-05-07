package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.KontaktFormularVerarbeitungView;
import controller.EmailController;

/**
 * <p>
 * Die Klasse <code>KontaktFormularVerarbeitungController</code> liest das Kontaktformular aus</br> und validiert
 * die E-Mail-Adresse.
 * </p>
 * 
 * @author Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class KontaktFormularVerarbeitungController
{
	/**
	 * <p>
	 * Der Konstruktor der Klasse <code>KontaktFormularVerabeitungController</code> versendet die vom</br>
	 * <code>KontaktFormularController</code> ausgelesenen Daten als E-Mail und ruft den</br>
	 * <code>KontaktFormularView</code> auf.
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see view.KontaktFormularVerarbeitungView
	 * @see controller.EmailController
	 */
	public KontaktFormularVerarbeitungController(HttpServletRequest request, HttpServletResponse response)
	{
		EmailController emailController = new EmailController();
		emailController.sendeEmail(request.getParameter("E-Mail"), request.getParameter("Betreff"),
				"kontakt@saatgutonline.de", request.getParameter("Nachricht"), request.getParameter("Anrede"),
				request.getParameter("Vorname"), request.getParameter("Nachname"));
		KontaktFormularVerarbeitungView kontaktFormularVerarbeitungView = new KontaktFormularVerarbeitungView(
				request, response);
		if (emailController.validiereEmail(request.getParameter("E-Mail")))
		{
			kontaktFormularVerarbeitungView.outKontaktVerarbeitungView();
		}
		else
		{
			kontaktFormularVerarbeitungView.outKontaktVerarbeitungViewUngueltig();
		}
	}
}
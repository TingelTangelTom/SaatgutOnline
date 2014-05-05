package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UrlController;

/**
 * Diese Klasse ist für die Ausgabe der Versand & Zahlungs Informationen
 * zuständig.
 * 
 * @author Anja Dietrich
 * 
 */
public class VersandInfoView {

	/**
	 * Konstruktor für den VersandInfoView.
	 * 
	 * @param request
	 * @param response
	 * @param agbText
	 * 
	 */
	public VersandInfoView(HttpServletRequest request, HttpServletResponse response,
			String versandInfoText) {

		// Für den Zurück-Link
		UrlController urlController = new UrlController(request);

		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println(versandInfoText);
		out.println("<br><br><a href=\"" + urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}
}
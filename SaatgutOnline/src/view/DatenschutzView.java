package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UrlController;

/**
 * Diese Klasse ist für die Ausgabe der Datenschutzerklärung zuständig.
 * 
 * @author Anja Dietrich
 * 
 */
public class DatenschutzView {

	/**
	 * Konstruktor für den DatenschutzView.
	 * 
	 * @param request
	 * @param response
	 * @param datenschutzText
	 * 
	 */
	public DatenschutzView(HttpServletRequest request, HttpServletResponse response,
			String datenschutzText) {

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
		out.println(datenschutzText);
		out.println("<br><br><a href=\"" + urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}
}
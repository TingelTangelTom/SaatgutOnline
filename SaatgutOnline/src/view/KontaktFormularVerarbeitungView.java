package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UrlController;

/**
 * Diese Klasse ist für die Ausgabe und Formatierung des Kontaktformulares 
 * nach dem absenden zuständig.
 * 
 * @author anju
 *
 */
public class KontaktFormularVerarbeitungView {

	UrlController urlController;

	private PrintWriter out;

	public KontaktFormularVerarbeitungView(HttpServletRequest request, HttpServletResponse response) {

		this.urlController = new UrlController(request);

		response.setContentType("text/html");
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.out.println();
	}

	/**
	 * Diese Methode ist für das erfolgreiche versenden.
	 * 
	 * @author anjad
	 */
	public void outKontaktVerarbeitungView() {
		// Ausgabe wenn absenden erfolgreich
		out.println("Vielen Dank für ihre Nachricht.");
		out.println("<a href=\"" + this.urlController.urlAusSessionHolen("Produktseite")
				+ "\">weiter einkaufen</a>");
		out.println("<br><br><a href=\"" + this.urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}

	/**
	 * Diese Methode ist für das fehlgeschlagene versenden.
	 * 
	 * @author anjad
	 */
	public void outKontaktVerarbeitungViewUngueltig() {
		// Ausgabe wenn absenden fehlgeschlagen
		out.println("Ihre E-Mail-Adresse ist ungültig.<br>Geben sie eine gültige E-Mail-Adresse ein.");
		out.println("<br><br><a href=\"" + this.urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}

}

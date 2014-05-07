package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UrlController;

/**
 * <p>
 * Die Klasse <code>KontaktFormularVerarbeitungView</code> ist für die Ausgabe und Formatierung</br> des
 * Kontaktformulares nach dem absenden zuständig.
 * </p>
 * 
 * @author Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class KontaktFormularVerarbeitungView
{
	// Für den Zurück-Link
	UrlController urlController;
	private PrintWriter out;

	/**
	 * <p>
	 * Konstruktor der Klasse<code>KontaktFormularVerarbeitungView</code>.
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see controller.UrlController
	 * @see java.io.PrintWriter
	 */
	public KontaktFormularVerarbeitungView(HttpServletRequest request, HttpServletResponse response)
	{
		// Für den Zurück-Link
		this.urlController = new UrlController(request);
		response.setContentType("text/html");
		try
		{
			out = response.getWriter();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		this.out.println();
	}

	/**
	 * <p>
	 * Die Methode <code>outKontaktVerarbeitungView</code> erzeugt die Ausgabe, wenn die E-Mail Validierung</br>
	 * erfolgreich war.
	 * </p>
	 */
	public void outKontaktVerarbeitungView()
	{
		// Ausgabe wenn absenden erfolgreich
		out.println("Vielen Dank für ihre Nachricht.");
		out.println("<a href=\"" + this.urlController.urlAusSessionHolen("Produktseite")
				+ "\">weiter einkaufen</a>");
		out.println("<br><br><a href=\"" + this.urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}

	/**
	 * <p>
	 * Die Methode <code>outKontaktVerarbeitungView</code> erzeugt die Ausgabe, wenn die E-Mail Validierung</br>
	 * fehlgeschlagen ist.
	 * </p>
	 */
	public void outKontaktVerarbeitungViewUngueltig()
	{
		// Ausgabe wenn absenden fehlgeschlagen
		out.println("Ihre E-Mail-Adresse ist ungültig.<br>Geben sie eine gültige E-Mail-Adresse ein.");
		out.println("<br><br><a href=\"" + this.urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}
}
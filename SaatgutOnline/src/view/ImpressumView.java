package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UrlController;

/**
 * <p>
 * Die Klasse <code>ImpressumView</code> ist für die Ausgabe und Formatierung des Impressums zuständig.
 * </p>
 * 
 * @author Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class ImpressumView
{
	/**
	 * <p>
	 * Konstruktor der Klasse<code>ImpressumView</code>.
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @param unternehmen_adresse
	 * @param unternehmen_telefon
	 * @param unternehmen_fax
	 * @param unternehmen_email
	 * @param unternehmen_geschaeftsfuehrung
	 * @param registergericht
	 * @param register_nr
	 * @param umsatzsteuer_id
	 * @param wirtschafts_id
	 * @param impressum_copyright
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see controller.UrlController
	 * @see java.io.PrintWriter
	 */
	public ImpressumView(HttpServletRequest request, HttpServletResponse response, String unternehmen_adresse,
			String unternehmen_telefon, String unternehmen_fax, String unternehmen_email,
			String unternehmen_geschaeftsfuehrung, String registergericht, String register_nr,
			String umsatzsteuer_id, String wirtschafts_id, String impressum_copyright)
	{
		// Für den Zurück-Link
		UrlController urlController = new UrlController(request);
		response.setContentType("text/html");
		PrintWriter out;
		try
		{
			out = response.getWriter();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		out.println("<h1>Impressum</h1>" + unternehmen_adresse + "<br><br>Telefon: " + unternehmen_telefon
				+ "<br>Fax: " + unternehmen_fax + "<br>E-Mail: " + unternehmen_email + "<br><br>Geschäftsführer: "
				+ unternehmen_geschaeftsfuehrung + "<br><br>Registergericht: " + registergericht + ", "
				+ register_nr + "<br>Umsatzsteuer-IdNr.: " + umsatzsteuer_id + "<br>Wirtschafts-IdNr.: "
				+ wirtschafts_id + "<br><br>" + impressum_copyright);
		out.println("<br><br><a href=\"" + urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}
}
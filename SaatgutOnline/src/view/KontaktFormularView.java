package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.UrlController;

/**
 * <p>
 * Die Klasse <code>KontaktFormularView</code> ist für die Ausgabe und Formatierung</br> des Kontaktformulars
 * zustaendig.
 * 
 * @author Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class KontaktFormularView
{
	// Für den Zurück-Link
	private UrlController urlController;
	private ResourceBundle resourceBundle;

	/**
	 * <p>
	 * Konstruktor der Klasse<code>KontaktFormularView</code>.
	 * </p>
	 * <p>
	 * Liest die aktuelle eingestellte Sprache aus der <code>HttpSession</code>,</br> und gibt die entsprechenden
	 * properties aus.
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see javax.servlet.http.HttpSession
	 */
	public KontaktFormularView(HttpServletRequest request, HttpServletResponse response)
	{
		// Internationalisierung
		HttpSession session = request.getSession();
		Locale locale = (Locale) session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "."
				+ getClass().getSimpleName(), locale);
	}

	/**
	 * <p>
	 * Die Methode <code>outKontaktFormular</code> gibt das Kontaktformular aus.
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see controller.UrlController
	 * @see java.io.PrintWriter
	 */
	public void outKontaktFormular(HttpServletRequest request, HttpServletResponse response)
	{
		// Für den Zurück-Link
		this.urlController = new UrlController(request);
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
		out.println("<h1>Kontakt</h1>\n");
		out.println("<p><label>SaatgutOnline GmbH<br>\n" + "Am Waldrand 325<br>\n" + "12325 Palmenhausen<br>\n"
				+ "E-Mail kontakt@saatgutonline.de<br>\n" + "Tel 049-098-764512-0<br>\n"
				+ "Fax 049-098-764512-99 </label></p>\n");
		out.println("<form action=/SaatgutOnline/KontaktFormularVerarbeitung>\n"
				+ "<table width=\"374\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">\n");
		out.println("<tr><td width=\"108\">");
		out.println(this.resourceBundle.getString("ANREDE") + " ");
		out.println("</td><td width=\"259\"><select name=\"Anrede\" id=\"Anrede\" title=\"Anrede\">\n");
		out.println("<option>");
		out.println(this.resourceBundle.getString("ANREDEFRAU"));
		out.println("</option>");
		out.println("<option>");
		out.println(this.resourceBundle.getString("ANREDEHERR"));
		out.println("</option></select></td></tr>\n");
		out.println("<tr><td><label for=\"Vorname\">");
		out.println(this.resourceBundle.getString("VORNAME") + " ");
		out.println("</label></td>\n");
		out.println("<td><input name=\"Vorname\" type=\"text\" id=\"Vorname\""
				+ "size=\"35\"maxlength=\"60\"></td></tr>\n");
		out.println("<tr><td>");
		out.println(this.resourceBundle.getString("NACHNAME") + " ");
		out.println("</td>\n");
		out.println("<td><input name=\"Nachname\" type=\"text\" id=\"Nachname\""
				+ "size=\"35\" maxlength=\"60\"></td></tr>\n");
		out.println("<tr><td><label for=\"E-Mail\">");
		out.println(this.resourceBundle.getString("EMAIL") + " ");
		out.println("</label></td>\n");
		out.println("<td><input name=\"E-Mail\" type=\"text\" id=\"E-Mail\""
				+ "size=\"35\" maxlength=\"60\"></td></tr>\n");
		out.println("<tr><td>");
		out.println(this.resourceBundle.getString("BETREFF") + " ");
		out.println("</td>\n<td><input name=\"Betreff\" type=\"text\" id=\"Betreff\""
				+ "size=\"35\" maxlength=\"60\"></td></tr>\n");
		out.println("<tr><td valign=\"top\">");
		out.println(this.resourceBundle.getString("NACHRICHT") + " ");
		out.println("</td>\n");
		out.println("<td><textarea name=\"Nachricht\" cols=\"30\" rows=\"10\""
				+ "maxlength=\"900\" id=\"Nachricht\"></textarea></td></tr>\n");
		out.println("<tr><td valign=\"top\">&nbsp;</td><td><div align=\"left\">\n");
		out.println("<input name=\"submit\" type=\"submit\" id=\"submit\""
				+ "formmethod=\"POST\" value=\"Senden\"></div></td></tr></table></form>");
		out.println("<br><br><a href=\"" + this.urlController.urlAusSessionHolen("Produktseite")
				+ "\">&#11013 Zurück</a>");
	}
}
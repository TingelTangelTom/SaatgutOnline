package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UrlController;

/**
 * Diese Klasse ist für die Ausgabe und Formatierung des Kontaktformulares zuständig.
 * 
 * @author Anja
 *
 */
public class KontaktFormularView {

	//Für den Zurück-Link
	UrlController urlController;

	public KontaktFormularView(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println();
	}

	/**
	 * Diese Methode gibt das Kontaktformular aus.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @author anjad
	 */
	public void outKontaktFormular(HttpServletRequest request, HttpServletResponse response) {

		// Für den Zurück-Link
		this.urlController = new UrlController(request);

		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println("<h1>Kontakt</h1>\n<p><label>SaatgutOnline GmbH<br>\nAm Waldrand 325<br>\n"
				+ "12325 Palmenhausen<br>\nE-Mail kontakt@saatgutonline.de<br>\nTel 049-098-764512-0<br>\n"
				+ "Fax 049-098-764512-99 </label></p>\n"
				+ "<form action=/SaatgutOnline/KontaktFormularVerarbeitung>\n"
				+ "<table width=\"200\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">\n"
				+ "<tr><td>Anrede:</td><td><select name=\"Anrede\" id=\"Anrede\" title=\"Anrede\">\n"
				+ "<option>Frau</option><option>Herr</option></select></td></tr>\n"
				+ "<tr><td><label for=\"Vorname\">Vorname:</label></td>\n"
				+ "<td><input name=\"Vorname\" type=\"text\" id=\"Vorname\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td>Nachname:</td>\n"
				+ "<td><input name=\"Nachname\" type=\"text\" id=\"Nachname\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"E-Mail\">E-Mail:</label></td>\n"
				+ "<td><input name=\"E-Mail\" type=\"text\" id=\"E-Mail\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td>Betreff:</td>\n<td><input name=\"Betreff\" type=\"text\" id=\"Betreff\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td valign=\"top\">Nachricht:</td>\n"
				+ "<td><textarea name=\"Nachricht\" cols=\"30\" rows=\"10\" maxlength=\"900\" id=\"Nachricht\"></textarea></td></tr>\n"
				+ "<tr><td valign=\"top\">&nbsp;</td><td><div align=\"left\">\n"
				+ "<input name=\"submit\" type=\"submit\" id=\"submit\" formmethod=\"POST\" value=\"Senden\"></div></td></tr></table></form>");
		out.println("<br><br><a href=\"" + this.urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}
	//TODO: Internationalisierung

}
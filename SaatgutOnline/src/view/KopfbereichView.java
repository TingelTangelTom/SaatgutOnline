package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * Die Klasse <code>KopfbereichView</code> stellt Html-Ausgabe-Bloecke zur
 * Darstellung des Kopfbereichs zur Verfuegung.
 * </p>
 * 
 * @author Tom Weigelt
 * @version 1.0
 * @since 1.7.0_51
 */
public class KopfbereichView {
	
	private PrintWriter out;
	private ResourceBundle resourceBundle;
	private HttpSession session;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>KopfbereichView</code>.
	 * </p>
	 * <p>
	 * Erzeugt einen <code>PrintWriter</code>.
	 * </p>
	 * <p>
	 * Ereugt ein <code>ResourceBundle</code>.
	 * </p>
	 * 
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @param response - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see java.io.PrintWriter
	 * @see java.util.ResourceBundle
	 */
	public KopfbereichView(HttpServletRequest request, HttpServletResponse response) {
		this.session = request.getSession();
		response.setContentType("text/html");
		try {
			this.out = response.getWriter();
		} catch (IOException e) {
		}
		// die zwei nachfolgenden Zeilen enthalten Code von Simon Ankele
		Locale locale = (Locale) session.getAttribute("sprache");		
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "."	+ getClass().getSimpleName(), locale);
	}

	
	/**
	 * <p>
	 * Stellt die Html-Ausgabe fuer den Anfang des Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outKopfbereichAnfang() {
		this.out.println("<!doctype html>");
		this.out.println("<html>\n<head>");
		this.out.println("<meta charset=\"ISO-8859-15\">");
		this.out.println("<link type=\"text/css\" href=\"resources/css/seitenlayout.css\" rel=\"stylesheet\" />");
		this.out.println("<title></title>");
		this.out.println("</head>\n<body>");
		this.out.println("<table class=\"kopfbereich\">");
		this.out.println("<tr>\n<td colspan=\"2\">");
	}

	
	/**
	 * <p>
	 * Stellt die Html-Ausgabe fuer das Ende des Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outKopfbereichEnde() {
		this.out.println("</td>\n</tr>");
	}

	
	/**
	 * <p>
	 * Stellt die Html-Ausgabe fuer den Anfang des Inhaltsrahmens des
	 * Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outInhaltsRahmenAnfang() {
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
	}

	
	/**
	 * <p>
	 * Stellt die Html-Ausgabe fuer eine neue Spalte im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outInhaltsRahmenNeueSpalte() {
		this.out.println("</td>\n<td>");
	}

	
	/**
	 * <p>
	 * Stellt die Html-Ausgabe fuer das Ende des Inhaltsrahmens des
	 * Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outInhaltsRahmenEnde() {
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	
	/**
	 * <p>
	 * Stellt die Html-Ausgabe fuer das Firmen-Logo im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outLogo() {
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<img src=\"resources/bilder/logo.png\" alt=\"Logo\">");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	
	/**
	 * <p>
	 * Stellt die Html-Ausgabe fuer den Firmen-Schriftzug im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outSchriftzug() {
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("Saatgut");
		this.out.println("</td>\n</tr>\n<tr>\n<td>");
		this.out.println("Online");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	
	/**
	 * <p>
	 * Stellt die internationalisierte Html-Ausgabe fuer den Login-Bereich im
	 * Inhaltsrahmen des Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outLoginBereich() {
		this.out.println("<form action=\"\" method=\"POST\">");
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println(this.resourceBundle.getString("BENUTZERNAME"));
		this.out.println("</td>\n<td colspan=\"2\">");
		this.out.println("<input type=\"text\" name=\"benutzername\" size=\"25\">");
		this.out.println("</td>\n</tr>\n<tr>\n<td>");
		this.out.println(this.resourceBundle.getString("PASSWORT"));
		this.out.println("</td>\n<td>");
		this.out.println("<input type=\"password\" name=\"passwort\" size=\"15\">");
		this.out.println("</td>\n<td>");
		this.out.println("<input type=\"submit\" name=\"anmelden\" value=\"" + this.resourceBundle.getString("ANMELDEN") + "\">");
		this.out.println("</td>\n</tr>\n<tr>\n<td>\n</td>\n<td colspan=\"2\">");
		this.out.println("<a href=\"/SaatgutOnline/Registrierung\">\n" + this.resourceBundle.getString("REGISTRIEREN") + "\n</a>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
		this.out.println("</form>");
	}

	
	/**
	 * <p>
	 * Stellt die internationalisierte Html-Ausgabe fuer den Logout-Bereich im
	 * Inhaltsrahmen des Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outLogoutBereich() {
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println(this.resourceBundle.getString("ANGEMELDET_ALS"));
		this.out.println("</td>\n</tr>\n<tr>\n<td>");
		this.out.println(this.session.getAttribute("benutzername"));
		this.out.println("</td>\n</tr>\n<tr>\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/Abmeldung\">\n" + this.resourceBundle.getString("ABMELDEN") + "\n</a>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	
	/**
	 * <p>
	 * Stellt die internationalisierte Html-Ausgabe fuer den Link zum Warenkorb
	 * im Inhaltsrahmen des Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outWarenkorbLink() {
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/Warenkorb\">\n" + this.resourceBundle.getString("ZUM_WARENKORB") + "\n</a>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	
	/**
	 * <p>
	 * Stellt die internationalisierte Html-Ausgabe fuer den Suchfeld-Bereich im
	 * Inhaltsrahmen des Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outSuchfeld() {
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<form action=\"/SaatgutOnline/Produktliste\" method=\"GET\">");
		this.out.println("<input type=\"hidden\" name=\"erweitertesuche\" value=\"false\">");
		this.out.println("<input type=\"text\" name=\"suchbegriff\" size=\"20\">");
		this.out.println("</td>\n<td>");
		this.out.println("<input type=\"submit\" name=\"suche\" value=\"" + this.resourceBundle.getString("SUCHEN") + "\">");
		this.out.println("</form>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	
	/**
	 * <p>
	 * Stellt die Html-Ausgabe fuer den Sprachwahl-Bereich im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung.
	 * </p>
	 */
	public void outSprachwahl() {
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<form action=\"\" method=\"POST\">");
		this.out.println("<input type=\"image\" src=\"resources/bilder/flags_iso/24/de.png\" alt=\"de\">");
		this.out.println("<input type=\"hidden\" name=\"sprache\" value=\"de\"");
		this.out.println("</form>");
		this.out.println("</td>\n<td>");
		this.out.println("<form action=\"\" method=\"POST\">");
		this.out.println("<input type=\"image\" src=\"resources/bilder/flags_iso/24/us.png\" alt=\"en\">");
		this.out.println("<input type=\"hidden\" name=\"sprache\" value=\"en\"");
		this.out.println("</form>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}
}
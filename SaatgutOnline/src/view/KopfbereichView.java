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
 * Die Klasse KopfbereichView stellt Html-Ausgabe-Bloecke zur Darstellung des Kopfbereichs zur
 * Verfuegung
 * @author Tom Weigelt
 * 
 */
public class KopfbereichView
{
	/**
	 * Objekt der Klasse <code>PrintWriter</code>
	 * 
	 * @see java.io.PrintWriter
	 */
	private PrintWriter out;
	/**
	 * Objekt der Klasse <code>ResourceBundle</code>
	 * 
	 * @see java.util.ResourceBundle
	 */
	private ResourceBundle resourceBundle;
	
	/**
	 * Objekt der Klasse <code>HttpSession</code>
	 * @see HttpSession
	 */
	private HttpSession session;

	/**
	 * Konstruktor der Klasse <code>KopfbereichView</code>
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public KopfbereichView(HttpServletRequest request, HttpServletResponse response)
	{
		this.session = request.getSession();
		
		response.setContentType("text/html");
		try
		{
			this.out = response.getWriter();
		} catch (IOException e)
		{
			System.out.println("PrintWriter nicht erstellt!");
			e.printStackTrace();
		}
		
		Locale locale = (Locale) session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "."
				+ getClass().getSimpleName(), locale);
	}

	/**
	 * Stellt die Html-Ausgabe fuer den Anfang des Kopfbereiches zur Verfuegung
	 */
	public void outKopfbereichAnfang()
	{
		this.out.println("<!doctype html>\n" + "<html>\n<head>\n" + "<meta charset=\"ISO-8859-15\">\n"
				+ "<link type=\"text/css\" href=\"resources/css/seitenlayout.css\" rel=\"stylesheet\" />\n"
				+ "<title></title>\n" + "</head>\n<body>");
		this.out.println("<table class=\"kopfbereich\">");
		this.out.println("<tr>\n<td colspan=\"2\">");
	}

	/**
	 * Stellt die Html-Ausgabe fuer das Ende des Kopfbereiches zur Verfuegung
	 */
	public void outKopfbereichEnde()
	{
		this.out.println("</td>\n</tr>"); // schliesst Kopfbereich
	}

	/**
	 * Stellt die Html-Ausgabe fuer den Anfang des Inhaltsrahmens des
	 * Kopfbereiches zur Verfuegung
	 */
	public void outInhaltsRahmenAnfang()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
	}

	/**
	 * Stellt die Html-Ausgabe fuer eine neue Spalte im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung
	 */
	public void outInhaltsRahmenNeueSpalte()
	{
		this.out.println("</td>\n<td>");
	}

	/**
	 * Stellt die Html-Ausgabe fuer das Ende des Inhaltsrahmens des
	 * Kopfbereiches zur Verfuegung
	 */
	public void outInhaltsRahmenEnde()
	{
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	/**
	 * Stellt die Html-Ausgabe fuer den Login-Bereich im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung
	 */
	public void outLoginBereich()
	{
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
		this.out.println("<input type=\"submit\" name=\"anmelden\" value=\""
				+ this.resourceBundle.getString("ANMELDEN") + "\">");
		this.out.println("</td>\n</tr>\n<tr>\n<td>\n</td>\n<td colspan=\"2\">");
		// FIXME mit Mailfunktion verbinden!
		this.out.println("<a href=\"/SaatgutOnline/NoOperation\">\n"
				+ this.resourceBundle.getString("PASSWORT_VERGESSEN") + "\n</a>");
		// TODO remove
		this.out.println(" (noOp)");
		this.out.println("</td>\n</tr>\n<tr>\n<td>\n</td>\n<td colspan=\"2\">");
		this.out.println("<a href=\"/SaatgutOnline/Registrierung\">\n" + this.resourceBundle.getString("REGISTRIEREN")
				+ "\n</a>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
		this.out.println("</form>");
	}

	/**
	 * Stellt die Html-Ausgabe fuer den Logout-Bereich im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung
	 */
	public void outLogoutBereich()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println(this.resourceBundle.getString("ANGEMELDET_ALS"));
		this.out.println("</td>\n</tr>\n<tr>\n<td>");
		this.out.println(this.session.getAttribute("benutzername"));		
		this.out.println("</td>\n</tr>\n<tr><td>\n</tr><tr>\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/Abmeldung\">\n" + this.resourceBundle.getString("ABMELDEN")+"\n</a>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	/**
	 * Stellt die Html-Ausgabe fuer das Firmen-Logo im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung
	 */
	public void outLogo()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<img src=\"resources/bilder/logo.png\" alt=\"Logo\">");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	/**
	 * Stellt die Html-Ausgabe fuer den Firmen-Schriftzug im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung
	 */
	public void outSchriftzug()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("Saatgut");
		this.out.println("</td>\n</tr>\n<tr>\n<td>");
		this.out.println("Online");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	/**
	 * Stellt die Html-Ausgabe fuer den Sprachwahl-Bereich im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung
	 */
	public void outSprachwahl()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>\n</td>\n<td>");
		this.out.println("<form action=\"\" method=\"POST\">");
		this.out.println("<input type=\"image\" src=\"resources/bilder/flags_iso/24/de.png\" alt=\"de\">");
		this.out.println("<input type=\"hidden\" name=\"sprache\" value=\"de\"");
		this.out.println("</form>");
		this.out.println("</td>\n<td>");
		this.out.println("<form action=\"\" method=\"POST\">");
		this.out.println("<input type=\"image\" src=\"resources/bilder/flags_iso/24/us.png\" alt=\"en\">");
		this.out.println("<input type=\"hidden\" name=\"sprache\" value=\"en\"");
		this.out.println("</form>");
		this.out.println("</td>\n</tr>\n<td colspan=\"3\">\n</td>\n</tr>");
		this.out.println("</table>");
	}

	/**
	 * Stellt die Html-Ausgabe fuer den Link zum Warenkorb im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung
	 */
	public void outWarenkorbLink()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/Warenkorb\">\n" + this.resourceBundle.getString("ZUM_WARENKORB")
				+ "\n</a>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	/**
	 * Stellt die Html-Ausgabe fuer den Suchfeld-Bereich im Inhaltsrahmen des
	 * Kopfbereiches zur Verfuegung
	 */
	public void outSuchfeld()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<form action=\"/SaatgutOnline/Suchergebnisse\" method=\"GET\">");
		this.out.println("<input type=\"text\" name=\"suchbegriff\" size=\"20\">");
		this.out.println("</td>\n<td>");
		this.out.println("<input type=\"submit\" name=\"suche\" value=\"" + this.resourceBundle.getString("SUCHEN")
				+ "\">");
		this.out.println("</form>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}
}

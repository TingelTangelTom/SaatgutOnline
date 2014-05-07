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
 * Die Klasse <code>FussbereichView</code> stellt Html-Ausgabe-Bloecke zur Darstellung des Warenkorbs zur
 * Verfuegung.
 * </p>
 * 
 * @author Tom Weigelt & Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class FussbereichView
{
	// der folgende Code wurde von Tom Weigelt entwickelt
	private HttpServletResponse response;
	private PrintWriter out;
	private ResourceBundle resourceBundle;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>FussbereichView</code>.
	 * </p>
	 * <p>
	 * Erzeugt einen <code>PrintWriter</code>.
	 * </p>
	 * <p>
	 * Ereugt ein <code>ResourceBundle</code>.
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public FussbereichView(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		this.response = response;
		this.response.setContentType("text/html");
		try
		{
			this.out = response.getWriter();
		}
		catch (IOException e)
		{
			System.out.println("PrintWriter nicht erstellt!");
			e.printStackTrace();
		}
		Locale locale = (Locale) session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "."
				+ getClass().getSimpleName(), locale);
	}

	/**
	 * <p>
	 * Stellt die Html-Ausgabe fuer den Anfang des Fussbereichs zur Verfuegung.
	 * </p>
	 */
	public void outFussbereichAnfang()
	{
		this.out.println("</td>\n</tr>");
		this.out.println("<tr>\n<td  colspan=\"2\">");
	}

	/**
	 * <p>
	 * Stellt die Html-Ausgabe fuer das Ende des Fussbereichs zur Verfuegung.
	 * </p>
	 */
	public void outFussbereichEnde()
	{
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
		this.out.println("</body>\n</html>");
	}

	// der folgende Code wurde von Anja Dietrich entwickelt
	/**
	 * <p>
	 * Stellt die internationalisierte Html-Ausgabe fuer den Inhalt des Fussbereichs zur Verfuegung.
	 * </p>
	 */
	public void outFussbereichInhalt()
	{
		this.out.println("<footer class=\"fussbereich\">\n<a href=\"/SaatgutOnline/Impressum\">");
		this.out.println(this.resourceBundle.getString("IMPRESSUM") + "</a> | \n");
		this.out.println("<a href=\"/SaatgutOnline/AGB\">");
		this.out.println(this.resourceBundle.getString("AGB") + "</a> | \n");
		this.out.println("<a href=\"/SaatgutOnline/VersandInfo\">");
		this.out.println(this.resourceBundle.getString("VERSANDINFO") + "</a> | \n");
		this.out.println("<a href=\"/SaatgutOnline/Datenschutz\">");
		this.out.println(this.resourceBundle.getString("DATENSCHUTZ") + "</a> | \n");
		this.out.println("<a href=\"/SaatgutOnline/Kontakt\">");
		this.out.println(this.resourceBundle.getString("KONTAKT") + "</a> | \n");
		this.out.println("<br>&copy Copyright 2014 by SaatgutOnline GmbH</footer>");
	}
}
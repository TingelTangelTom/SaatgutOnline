package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.KategorieModel;

/**
 * <p>Die Klasse <code>NavigationsbereichView</code> stellt Html-Ausgabe-Bloecke zur Darstellung des Navigationsbereichs zur Verfuegung.</p>
 * @author Tom Weigelt
 * @version 1.0
 * @since 1.7.0_51 
 */
public class NavigationsbereichView
{
	private PrintWriter out;	
	private ResourceBundle resourceBundle;

	/**
	 * <p>Konstruktor der Klasse <code>NavigationsbereichView</code>.</p>
	 * <p>Erzeugt einen <code>PrintWriter</code>.</p>
	 * @param response - die aktuelle <code>HttpServletResponse</code>
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see java.io.PrintWriter
	 */
	public NavigationsbereichView(HttpServletRequest request, HttpServletResponse response)
	{	
		HttpSession session = request.getSession();
		
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
	 * <p>Stellt die Html-Ausgabe fuer den Anfang des Navigationsbereiches zur Verfuegung.</p>
	 */
	public void outNavigationsbereichAnfang()
	{
		this.out.println("<tr>\n<td class=\"navigation\">");		
	}
	
	/**
	 * <p>Stellt die Html-Ausgabe fuer das Ende des Navigationsbereiches zur Verfuegung</p>
	 */
	public void outNavigationsbereichEnde()
	{
		this.out.println("</td>\n<td class=\"inhalt\">");
	}
	
	/**
	 * <p>Stellt die internationalisierte Html-Ausgabe fuer den Angebote-Link zur Verfuegung</p>
	 */
	public void outAngebote()
	{
		this.out.println("<table>");
		this.out.println("<tr rowspan=\"2\">\n<td>");
		this.out.println("&nbsp;");
		this.out.println("</td>\n</tr>");
		this.out.println("<tr>\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/Produktliste?angebote=true\">"+ this.resourceBundle.getString("ANGEBOTE")+"</a>");
		this.out.println("</td>\n</tr>\n<tr rowspan=\"2\">\n<td>");
		this.out.println("&nbsp;");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}
	
	/**
	 * <p>Stellt die internationalisierte Html-Ausgabe fuer den akteull gewaehlten Angebote-Link zur Verfuegung</p>
	 */
	public void outAngeboteAktuell()
	{
		this.out.println("<table>");
		this.out.println("<tr rowspan=\"2\">\n<td>");
		this.out.println("&nbsp;");
		this.out.println("</td>\n</tr>");
		this.out.println("<tr class=\"navigation_aktuell\">\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/Produktliste?angebote=true\">"+ this.resourceBundle.getString("ANGEBOTE")+"</a>");
		this.out.println("</td>\n</tr>\n<tr rowspan=\"2\">\n<td>");
		this.out.println("&nbsp;");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}
	
	/**
	 * <p>Stellt die Html-Ausgabe fuer den Anfang der Kategorienliste im Navigationsbereich zur Verfuegung.</p>
	 */
	public void outKategorienListeAnfang()
	{
		this.out.println("<table>");
	}
	
	/**
	 * <p>Stellt die Html-Ausgabe fuer das Ende der Kategorienliste im Navigationsbereich zur Verfuegung</p>
	 */
	public void outKategorienListeEnde()
	{
		this.out.println("</table>");
	}
	
	/**
	 * <p>Stellt die Html-Ausgabe fuer eine Hauptkategorie in der Kategorienliste des Navigationsbereiches zur Verfuegung.</p>
	 * @param kategorieModel - das Objekt der anzuzeigenden Kategorie als <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	public void outHauptKategorieAnzeigen(KategorieModel kategorieModel)
	{		
		this.out.println("<tr>\n<td colspan=\"2\">");
		this.out.println("<a href=\"/SaatgutOnline/Produktliste?kategorie="
				+ kategorieModel.getKategorieId()				
				+ "\">\n"
				+ kategorieModel.getKategorieName()
				+ "\n</a>");
		this.out.println("</td>\n</tr>");
	}
		
	/**
	 * <p>Stellt die Html-Ausgabe fuer die aktuell gewaehlte Hauptkategorie in der Kategorienliste des Navigationsbereiches zur Verfuegung.</p>	 
	 * @param kategorieModel - das Objekt der anzuzeigenden Kategorie als <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	public void outHauptKategorieAktuellAnzeigen(KategorieModel kategorieModel)
	{		
		this.out.println("<tr class=\"navigation_aktuell\">\n<td colspan=\"2\">");		
		this.out.println("<a href=\"/SaatgutOnline/Produktliste?kategorie="
				+ kategorieModel.getKategorieId()				
				+ "\">\n"
				+ kategorieModel.getKategorieName()
				+ "\n</a>");
		this.out.println("</td>\n</tr>");		
	}
	
	/**
	 * <p>Stellt die Html-Ausgabe fuer eine Unterkategorie in der Kategorienliste des Navigationsbereiches zur Verfuegung.</p>
	 * @param kategorieModel - das Objekt der anzuzeigenden Kategorie als <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	public void outUnterKategorieAnzeigen(KategorieModel kategorieModel)
	{			
		this.out.println("<tr>\n<td>");
		this.out.println("&nbsp;");
		this.out.println("</td>\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/Produktliste?kategorie="
				+ kategorieModel.getKategorieId()				
				+ "\">\n"
				+ kategorieModel.getKategorieName()
				+ "\n</a>");
		this.out.println("</td>\n</tr>");
	}
	
	/**
	 * <p>Stellt die Html-Ausgabe fuer die aktuell gewaehlte Unterkategorie in der Kategorienliste des Navigationsbereiches zur Verfuegung.</p>
	 * @param kategorieModel - das Objekt der anzuzeigenden Kategorie als <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	public void outUnterKategorieAktuellAnzeigen(KategorieModel kategorieModel)
	{			
		this.out.println("<tr class=\"navigation_aktuell\">\n<td>");
		this.out.println("&nbsp;");		
		this.out.println("</td>\n<td>");				
		this.out.println("<a href=\"/SaatgutOnline/Produktliste?kategorie="
				+ kategorieModel.getKategorieId()				
				+ "\">\n"
				+ kategorieModel.getKategorieName()
				+ "\n</a>");
		this.out.println("</td>\n</tr>");
	}
}

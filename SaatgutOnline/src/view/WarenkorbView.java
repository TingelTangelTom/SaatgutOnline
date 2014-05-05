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
import model.ProduktModel;

/**
 * Die Klasse WarenkorbView stellt Html-Ausgabe-Bloecke zur Darstellung des Warenkorbs zur Verfuegung
 * @author Tom Weigelt
 *
 */
public class WarenkorbView
{
	/**
	 * Objekt der Klasse <code>PrintWriter</code>
	 * @see java.io.PrintWriter
	 */
	private PrintWriter out;
	/**
	 * Objekt der Klasse <code>ResourceBundle</code>
	 * @see java.util.ResourceBundle
	 */
	private ResourceBundle resourceBundle;
	/**
	 * Objekt der Klasse <code>HttpSession</code>
	 * @see javax.servlet.http.HttpSession
	 */
	/**
	 * 
	 */
	private HttpSession session;
	/**
	 * Objekt der Klasse <code>UrlController<code>
	 * @see controller.UrlController
	 */
	private UrlController urlController;

	/**
	 * Konstruktor der Klasse <code>WarenkorbView</code>
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @param response - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public WarenkorbView(HttpServletRequest request, HttpServletResponse response)
	{
		this.session = request.getSession();
		this.urlController = new UrlController(request);

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
	 * Stellt die Html-Ausgabe fuer den Anfang des Warenkorbs zur Verfuegung
	 */
	public void outWarenkorbAnfang()
	{					
		this.out.println("<table>");		
		this.out.println("<tr>\n<td colspan=\"8\">");
		this.out.println(this.resourceBundle.getString("WARENKORB"));
		this.out.println("</td>\n</tr>");
		this.out.println("<tr>\n<td colspan=\"8\">\n</tr>");
		this.out.println("<tr>\n<td>");
		this.out.println(this.resourceBundle.getString("NAME"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("PRODUKTNUMMER"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("VERPACKUNGSEINHEIT"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("EINZELPREIS_POSITION"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("MENGE"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("BESTAND"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("GESAMTPREIS_POSITION"));
		this.out.println("</td>\n<td>");
		this.out.println("<form action=\"Warenkorb\" method=\"GET\">");
		this.out.println(this.resourceBundle.getString("POSITION_ENTFERNEN"));
		this.out.println("</td>\n</tr>");
		this.out.println("<tr>\n<td colspan=\"7\">\n</td>\n</tr>");
	}
	
	/**
	 * Stellt die Html-Ausgabe fuer eine Position des Warenkorbs zur Verfuegung
	 * @param produktModel - Produkt der Position als <code>ProduktModel</code>
	 * @param menge - Bestellmenge als <code>int</code>
	 * @param einzelpreisFormatiert - Einzelpreis des Produkts, formatiert auf zwei Stellen nach dem Komma als <code>String</code>
	 * @param gesamtpreisPositionFormatiert - Gesamtpreis der Position, formatiert auf zwei Stellen nach dem Komma als <code>String</code>
	 * @see model.ProduktModel
	 */
	public void outWarenkorbInhalt(ProduktModel produktModel, int menge, String einzelpreisFormatiert, String gesamtpreisPositionFormatiert)
	{
		this.out.println("<tr>\n<td>");		
		this.out.println("<a href=\"/SaatgutOnline/Produktinfo?produkt="
				+ produktModel.getId()				
				+ "\">\n"
				+ produktModel.getName()
				+ "\n</a>");
		this.out.println("</td>\n<td>");
		this.out.println(produktModel.getProduktnummer());
		this.out.println("</td>\n<td>");
		this.out.println(produktModel.getVpe());
		this.out.println("</td>\n<td>");
		this.out.println(einzelpreisFormatiert);
		this.out.println("</td>\n<td>");				
		this.out.println("<input type=\"text\" name=\"menge_" + produktModel.getId() + "\" value=\""+ menge +"\" size=2");
		this.out.println("</td>\n<td>");
		this.out.println(produktModel.getBestand());
		this.out.println("</td>\n<td>");
		this.out.println(gesamtpreisPositionFormatiert);
		this.out.println("</td>\n<td>");
		this.out.println("<input type=\"checkbox\" name=\"entfernen_"+ produktModel.getId() + "\" value=\"true\">");
		this.out.println("</td>\n</tr>");		
	}

	/**
	 * Stellt die Html-Ausgabe fuer eine Ausgabezeile des Warenkorbs zur Verfuegung, wenn die Bestellmenge groesser ist, als der Lagerbestand 
	 */
	public void outMengeNichtImBestand()
	{		
		this.out.println("<tr rowspan=\"2\">\n<td colspan=\"8\">\n</td>\n</tr>\n<tr>\n<td colspan=\"7\">");
		this.out.println(this.resourceBundle.getString("MENGE_NICHT_IM_BESTAND"));
		this.out.println("</td>\n</tr>");		
	}
	
	/**
	 * Stellt die Html-Ausgabe fuer den leeren Warenkorb zur Verfuegung 
	 */
	public void outLeererWarenkorb()
	{
		this.out.println("<tr>\n<td colspan=\"8\">");
		this.out.println(this.resourceBundle.getString("WARENKORB_LEER"));
		this.out.println("</td>\n</tr>");
	}	

	/**
	 * Stellt die Html-Ausgabe fuer das Ende des Warenkorbs zur Verfuegung
	 * @param zwischensummeFormatiert - die Zwischensumme der Bestellung, , formatiert auf zwei Stellen nach dem Komma als <code>String</code>
	 */
	public void outWarenkorbEnde(String zwischensummeFormatiert)
	{
		this.out.println("<tr>\n<td colspan=\"8\">\n</td>\n</tr>");
		this.out.println("<tr>\n<td colspan=\"4\">");
		this.out.println("</td>\n<td colspan=\"2\">");
		this.out.println(this.resourceBundle.getString("ZWISCHENSUMME_BESTELLUNG"));
		this.out.println("</td>\n<td>");
		this.out.println(zwischensummeFormatiert);
		this.out.println("</td>\n<td>");		
		this.out.println("</td>\n</tr>\n<tr>\n<td colspan=\"7\">");
		this.out.println("</td>\n</tr>\n<tr>\n<td>");
		this.out.println("<input type=\"submit\" name=\"aktualisieren\" value=\"" + this.resourceBundle.getString("AKTUALISIEREN") + "\">");
		this.out.println("</form>");
		this.out.println("</td>\n<td colspan=\"2\">");
		this.out.println("<a href=\"/SaatgutOnline/Warenkorb?leeren=true\">\n" + this.resourceBundle.getString("WARENKORB_LEEREN") + "\n</a>");		
		this.out.println("</td>\n<td colspan=\"2\">");
		this.out.println("<a href=\""+ this.urlController.urlAusSessionHolen("Produktseite") + "\">\n" + this.resourceBundle.getString("WEITER") + "\n</a>");
		this.out.println("</td>\n<td colspan=\"2\">");		
		this.out.println("<a href=\"/SaatgutOnline/Kasse\">\n" + this.resourceBundle.getString("KASSE") + "\n</a>");		
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}
	


}

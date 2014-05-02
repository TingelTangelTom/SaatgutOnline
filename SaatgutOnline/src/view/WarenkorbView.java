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

public class WarenkorbView
{
	private PrintWriter out;
	private ResourceBundle resourceBundle;
	private HttpSession session;
	private UrlController urlController;

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

	public void outWarenkorbAnfang()
	{					//TODO remove
		this.out.println("<table border='1'>");		
		this.out.println("<tr>\n<td colspan=\"7\">");
		this.out.println(this.resourceBundle.getString("WARENKORB"));
		this.out.println("</td>\n</tr>");
		this.out.println("<tr>\n<td colspan=\"7\">\n</tr>");
		this.out.println("<tr>\n<td>");
		this.out.println(this.resourceBundle.getString("NAME"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("BESTELLNUMMER"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("VERPACKUNGSEINHEIT"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("EINZELPREIS_POSITION"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("MENGE"));
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("GESAMTPREIS_POSITION"));
		this.out.println("</td>\n<td>");
		this.out.println("<form action=\"Warenkorb\" method=\"GET\">");
		this.out.println(this.resourceBundle.getString("POSITION_ENTFERNEN"));
		this.out.println("</td>\n</tr>");
		this.out.println("<tr>\n<td colspan=\"7\">\n</td>\n</tr>");
	}

	public void outWarenkorbEnde(String zwischensummeFormatiert)
	{
		this.out.println("<tr>\n<td colspan=\"7\">\n</td>\n</tr>");
		this.out.println("<tr>\n<td colspan=\"3\">");
		this.out.println("</td>\n<td colspan=\"2\">");		
		this.out.println("</td>\n<td>");
		this.out.println(zwischensummeFormatiert);
		this.out.println("</td>\n<td>");
		this.out.println(this.resourceBundle.getString("ZWISCHENSUMME_BESTELLUNG"));
		this.out.println("</td>\n</tr>\n<tr>\n<td colspan=\"7\">");
		this.out.println("</td>\n</tr>\n<tr>\n<td>");
		this.out.println("<input type=\"submit\" name=\"aktualisieren\" value=\"" + this.resourceBundle.getString("AKTUALISIEREN") + "\">");
		this.out.println("</form>");
		this.out.println("</td>\n<td colspan=\"2\">");
		this.out.println("<a href=\"/SaatgutOnline/Warenkorb?leeren=true\">\n" + this.resourceBundle.getString("WARENKORB_LEEREN") + "\n</a>");		
		this.out.println("</td>\n<td colspan=\"2\">");
		this.out.println("<a href=\""+ this.urlController.urlAusSessionHolen("Produktseite") + "\">\n" + this.resourceBundle.getString("WEITER") + "\n</a>");
		this.out.println("</td>\n<td colspan=\"2\">");		
		
		// FIXME link anpassen und NoOp entfernen!
		this.out.println("<a href=\"/SaatgutOnline/NoOperation\">\n" + this.resourceBundle.getString("KASSE") + "\nNoOP</a>");
		
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}
	
	public void outWarenkorbInhalt(ProduktModel produktModel, int menge, String einzelpreisFormatiert, String gesamtpreisPositionFormatiert)
	{
		this.out.println("<tr>\n<td>");		
		this.out.println("<a href=\"/SaatgutOnline/Produktinfo?produkt="
				+ produktModel.getId()				
				+ "\">\n"
				+ produktModel.getName()
				+ "\n</a>");
		this.out.println("</td>\n<td>");
		this.out.println(produktModel.getBestellnummer());
		this.out.println("</td>\n<td>");
		this.out.println(produktModel.getVpe());
		this.out.println("</td>\n<td>");
		this.out.println(einzelpreisFormatiert);
		this.out.println("</td>\n<td>");				
		this.out.println("<input type=\"text\" name=\"menge_" + produktModel.getId() + "\" value=\""+ menge +"\" size=2");
		this.out.println("</td>\n<td>");
		this.out.println(gesamtpreisPositionFormatiert);
		this.out.println("</td>\n<td>");
		this.out.println("<input type=\"checkbox\" name=\"entfernen_"+ produktModel.getId() + "\" value=\"true\">");
		this.out.println("</td>\n</tr>");		
	}
		
	public void outLeererWarenkorb()
	{
		this.out.println("<tr>\n<td colspan=\"7\">");
		this.out.println(this.resourceBundle.getString("WARENKORB_LEER"));
		this.out.println("</td>\n</tr>");
	}

}

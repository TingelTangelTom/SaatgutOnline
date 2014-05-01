package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProduktModel;

public class WarenkorbView
{
	private PrintWriter out;
	private ResourceBundle resourceBundle;
	private HttpSession session;

	public WarenkorbView(HttpServletRequest request, HttpServletResponse response)
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

	public void outWarenkorbAnfang()
	{
		
		
		// TODO remove border='1'		
		out.println("<table border='1'>");
		
		out.println("<tr>\n<td colspan=\"7\">");
		out.println(this.resourceBundle.getString("WARENKORB"));
		out.println("</td>\n</tr>");
		out.println("<tr>\n<td colspan=\"7\">\n</tr>");
		out.println("<tr>\n<td>");
		out.println(this.resourceBundle.getString("NAME"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("BESTELLNUMMER"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("GEWICHT"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("EINZELPREIS_POSITION"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("MENGE"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("GESAMTPREIS_POSITION"));
		out.println("</td>\n<td>");
		out.println("<form action=\"Warenkorb\" method=\"GET\">");
		out.println(this.resourceBundle.getString("POSITION_ENTFERNEN"));
		out.println("</td>\n</tr>");
		out.println("<tr>\n<td colspan=\"7\">\n</td>\n</tr>");
	}

	public void outWarenkorbEnde(double gesamtgewicht, double zwischensumme)
	{
		out.println("<tr>\n<td colspan=\"7\">\n</td>\n</tr>");
		out.println("<tr>\n<td colspan=\"2\">");
		out.println("</td>\n<td>");
		out.println(gesamtgewicht);
		out.println("</td>\n<td colspan=\"2\">");		
		out.println("</td>\n<td>");
		out.println(zwischensumme);
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("ZWISCHENSUMME_BESTELLUNG"));
		out.println("</td>\n</tr>\n<tr>\n<td colspan=\"7\">");
		out.println("</td>\n</tr>\n<tr>\n<td>");
		out.println("<input type=\"submit\" name=\"aktualisieren\" value=\"" + this.resourceBundle.getString("AKTUALISIEREN") + "\">");
		out.println("</form>");
		out.println("</td>\n<td colspan=\"2\">");
		out.println("<form action=\"Warenkorb\" method=\"GET\">");
		out.println("<input type=\"submit\" name=\"leeren\" value=\"" + this.resourceBundle.getString("WARENKORB_LEEREN") + "\">");
		out.println("</form>");
		out.println("</td>\n<td colspan=\"2\">");
		
		// FIXME action anpassen und NoOp entfernen!
		out.println("<form action=\"NoOperation\" method=\"GET\">");
		out.println("<input type=\"submit\" name=\"weiter\" value=\"" + this.resourceBundle.getString("WEITER") + " NoOP\">");
		out.println("</form>");

		out.println("</td>\n<td colspan=\"2\">");		
		
		// FIXME action anpassen und NoOp entfernen!
		out.println("<form action=\"NoOperation\" method=\"GET\">");
		out.println("<input type=\"submit\" name=\"kasse\" value=\"" + this.resourceBundle.getString("KASSE") + " NoOP\">");
		out.println("</form>");
		
		out.println("</td>\n</tr>");
		out.println("</table>");
	}
	
	public void outWarenkorbInhalt(ProduktModel produktModel, int menge, double zwischensumme)
	{
		out.println("<tr>\n<td>");
		
		//TODO Link zur ProduktInfo realisieren!
//		out.println("<a href\"SaatgutOnline/ProduktInfo?produkt_id=" + produktModel.getId() +"\">");
		out.println(produktModel.getName());
		
		out.println("</td>\n<td>");
		out.println(produktModel.getBestellnummer());
		out.println("</td>\n<td>");
		out.println(produktModel.getGewicht());
		out.println("</td>\n<td>");
		out.println(produktModel.getPreisBrutto());
		out.println("</td>\n<td>");				
		out.println("<input type=\"text\" name=\"menge_" + produktModel.getId() + "\" value=\""+ menge +"\" size=2");
		out.println("</td>\n<td>");
		out.println(zwischensumme);
		out.println("</td>\n<td>");
		out.println("<input type=\"checkbox\" name=\"entfernen\" value=\"" + produktModel.getId() + "\">");
		out.println("</td>\n</tr>");		
	}
	
	public void outLeererWarenkorb()
	{
		out.println("<tr>\n<td colspan=\"7\">");
		out.println("Keine Produkte im Warenkorb");
		out.println("</td>\n</tr>");
	}

}

package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FussbereichView
{

	private HttpServletResponse response;
	private PrintWriter out;
	private ResourceBundle resourceBundle;

	public FussbereichView(HttpServletRequest request, HttpServletResponse response) {
		
		// Internationalisierung
		HttpSession session = request.getSession();
		Locale locale = (Locale) session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "."
				+ getClass().getSimpleName(), locale);
		
		this.response = response;
		this.response.setContentType("text/html");

		try
		{
			this.out = response.getWriter();
		} catch (IOException e)
		{
			System.out.println("PrintWriter nicht erstellt!");
			e.printStackTrace();
		}
	}
	
	public void outFussbereichAnfang()
	{
		this.out.println("</td>\n</tr>"); // schliesst Hauptbereich	
		this.out.println("<tr>\n<td  colspan=\"2\">"); // oeffnet Fussbereich
	}
	
	public void outFussbereichEnde()
	{
		this.out.println("</td>\n</tr>"); // schliesst Fussbereich
		this.out.println("</table>"); // schliesst Tabelle
		this.out.println("</body>\n</html>");
	}
	
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
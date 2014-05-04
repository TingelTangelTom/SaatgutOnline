package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class FussbereichView
{

	private HttpServletResponse response;
	private PrintWriter out;

	/**
	 * Konstruktor der Klasse FussbereichView.</br> Erzeugt einen PrintWriter
	 * aus response.
	 * 
	 * @param response
	 *            response aus dem KopfbereichController als
	 *            <i>HttpServletResponse</i>.
	 * 
	 */
	public FussbereichView(HttpServletResponse response)
	{
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
		out.println("</td>\n</tr>"); // schliesst Hauptbereich
		
		out.println("<tr>\n<td  colspan=\"2\">"); // oeffnet Fussbereich
	}
	
	public void outFussbereichEnde()
	{
		out.println("</td>\n</tr>"); // schliesst Fussbereich
		out.println("</table>"); // schliesst Tabelle
		out.println("</body>\n</html>");
	}
	
	public void outFussbereichInhalt()
	{
		out.println("<footer class=\"fussbereich\">\n<a href=\"/SaatgutOnline/Impressum\">Impressum</a> | \n"
				      + "<a href=\"/SaatgutOnline/AGB\">AGB</a> | \n"
				      + "<a href=\"/SaatgutOnline/VersandInfo\">Versand & Zahlung</a> | \n"
				      + "<a href=\"/SaatgutOnline/Datenschutz\">Datenschutz</a> | \n"
				      + "<a href=\"/SaatgutOnline/Kontakt\">Kontakt</a> | \n"
				      + "<a href=\"/SaatgutOnline/Sitemap\">Sitemap</a> | \n"
				      + "<br>&copy Copyright 2014 by SaatgutOnline GmbH</footer>");
	}
}
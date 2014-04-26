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
		
		out.println("<tr>\n<td class=\"fussbereich\" colspan='2'>"); // oeffnet Fussbereich
	}
	
	public void outFussbereichEnde()
	{
		out.println("</td>\n</tr>"); // schliesst Fussbereich
		out.println("</table>"); // schliesst Tabelle
		out.println("</body>\n</html>");
	}
	
	public void outFussbereichInhalt()
	{
		out.println("<footer><a href=\"/SaatgutOnline/src/servlets/Impressum.java\">"
					+ "Impressum</a> | <a href=\"/SaatgutOnline/src/servlets/AGB.java\">"
					+ "AGB</a> | <a href=\"/SaatgutOnline/src/servlets/VersandZahlung.java\">"
					+ "Versand & Zahlung</a> | <a href=\"/SaatgutOnline/src/servlets/Datenschutz.java\">"
					+ "Datenschutz</a> | <a href=\"/SaatgutOnline/src/servlets/Kontakt.java\">"
					+ "Kontakt</a> | <a href=\"/SaatgutOnline/src/servlets/Sitemap.java\">"
					+ "Sitemap</a> | <a href=\"/SaatgutOnline/src/servlets/Copyright.java\">"
					+ "Copyright</a></footer>");
	}
}

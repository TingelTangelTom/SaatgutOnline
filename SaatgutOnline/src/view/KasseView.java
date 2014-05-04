package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KasseView
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
	 * Konstruktor der Klasse <code>KopfbereichView</code>
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public KasseView(HttpServletRequest request, HttpServletResponse response)
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
	
	public void outKasseKomplett()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println(this.resourceBundle.getString("KASSE"));
		this.out.println("</td>\n</tr>");
		this.out.println("<tr>\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/Warenkorb\">zurück</a>");	
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");		
	}
}

package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AnmeldungFehlerView {

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
	
	
	public AnmeldungFehlerView (HttpServletRequest request, HttpServletResponse response) {
		
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
	
	public void outAnmeldungFehler(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		try {
			this.out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.out.println("<h3>" + this.resourceBundle.getString("UEBERSCHRIFT") + "</h1><br />");
		this.out.println("<p>"  + this.resourceBundle.getString("TEXT") + "</p><br />");
		
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
}
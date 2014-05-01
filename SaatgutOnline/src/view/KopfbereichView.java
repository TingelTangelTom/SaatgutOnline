package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KopfbereichView
{	
	private PrintWriter out;
	private ResourceBundle resourceBundle;

	public KopfbereichView(HttpServletRequest request, HttpServletResponse response)
	{
		response.setContentType("text/html");
		try
		{
			this.out = response.getWriter();
		} catch (IOException e)
		{
			System.out.println("PrintWriter nicht erstellt!");
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		
		Locale locale = (Locale) session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "."
				+ getClass().getSimpleName(), locale);	
	}

	public void outKopfbereichAnfang()
	{
		this.out.println("<!doctype html>\n"
				+ "<html>\n<head>\n"
				+ "<meta charset=\"ISO-8859-15\">\n"
				+ "<link type=\"text/css\" href=\"resources/css/seitenlayout.css\" rel=\"stylesheet\" />\n"
				+ "<title></title>\n" + "</head>\n<body>");
		this.out.println("<table class=\"kopfbereich\">");
		this.out.println("<tr>\n<td colspan=\"2\">");
	}

	public void outKopfbereichEnde()
	{
		this.out.println("</td>\n</tr>"); // schliesst Kopfbereich
	}

	public void outInhaltsRahmenAnfang()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
	}

	public void outInhaltsRahmenNeueSpalte()
	{
		this.out.println("</td>\n<td>");
	}

	public void outInhaltsRahmenEnde()
	{
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	public void outLoginBereich()
	{	
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
		this.out.println("<input type=\"submit\" name=\"anmelden\" value=\""+ this.resourceBundle.getString("ANMELDEN") +"\">");				
		this.out.println("</td>\n</tr>\n<tr>\n<td>\n</td>\n<td colspan=\"2\">");
		//FIXME mit Mailfunktion verbinden! 
		this.out.println("<a href=\"/SaatgutOnline/NoOperation\">\n" + this.resourceBundle.getString("PASSWORT_VERGESSEN") + "?\n</a>");
		//TODO remove
		this.out.println(" (noOp)");
		this.out.println("</td>\n</tr>\n<tr>\n<td>\n</td>\n<td colspan=\"2\">");
		//FIXME mit Registrierung verbinden! 
		this.out.println("<a href=\"/SaatgutOnline/NoOperation\">\n" + this.resourceBundle.getString("REGISTRIEREN") + "\n</a>");
		//TODO remove
		this.out.println(" (noOp)");
		this.out.println("</td>\n</tr>");		
		this.out.println("</table>");
		this.out.println("</form>");
	}
	
	public void outLogoutBereich()
	{
		
	}

	public void outLogo()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<img src=\"resources/bilder/logo.png\" alt=\"Logo\">");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	public void outSchriftzug()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("Saatgut");
		this.out.println("</td>\n</tr>\n<tr>\n<td>");
		this.out.println("Online");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	public void outSprachwahl()
	{		
		this.out.println("<table>");
		this.out.println("<tr>\n<td>\n</td>\n<td>");
		this.out.println("<form action=\"\" method=\"POST\">");
		this.out.println("<input type=\"image\" src=\"resources/bilder/flags_iso/24/de.png\" alt=\"de\">");
		this.out.println("<input type=\"hidden\" name=\"sprache\" value=\"de\"");
		this.out.println("</form>");
		this.out.println("</td>\n<td>");
		this.out.println("<form action=\"\" method=\"POST\">");
		this.out.println("<input type=\"image\" src=\"resources/bilder/flags_iso/24/us.png\" alt=\"en\">");
		this.out.println("<input type=\"hidden\" name=\"sprache\" value=\"en\"");
		this.out.println("</form>");
		this.out.println("</td>\n</tr>\n<td colspan=\"3\">\n</td>\n</tr>");
		this.out.println("</table>");
	}

	public void outWarenkorbLink()
	{		
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/Warenkorb\">\n" + this.resourceBundle.getString("ZUM_WARENKORB") + "\n</a>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}
	
	public void outSuchfeld()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<form action=\"\" method=\"GET\">");
		this.out.println("<input type=\"text\" name=\"suchbegriff\" size=\"20\">");
		this.out.println("</td>\n<td>");
		this.out.println("<input type=\"submit\" name=\"suche\" value=\"" + this.resourceBundle.getString("SUCHEN") + "\">");
		this.out.println("</form>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}	
}

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
	private String servletPath;

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
		
		this.servletPath = request.getServletPath();
	}

	public void outKopfbereichAnfang()
	{
		this.out.println("<!doctype html>\n"
				+ "<html>\n<head>\n"
				+ "<meta charset='UTF-8'>\n"
				+ "<link type=\"text/css\" href=\"resources/css/seitenlayout.css\" rel=\"stylesheet\" />\n"
				+ "<title></title>\n" + "</head>\n<body>");
		this.out.println("<table class=\"kopfbereich\">");
		this.out.println("<tr>\n<td colspan=\"2\">");
	}

	public void outKopfbereichEnde()
	{
		this.out.println("</td>\n</tr>"); // schliesst Kopfbereich
	}

	public void outInhaltsframeAnfang()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
	}

	public void outInhaltsframeNeueSpalte()
	{
		this.out.println("</td>\n<td>");
	}

	public void outInhaltsframeEnde()
	{
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

	public void outLoginBereich()
	{		
		this.out.println("<table>");
		this.out.println("<form action=\"\" method=\"POST\">");
		this.out.println("<tr>\n<td>");
		this.out.println(this.resourceBundle.getString("NUTZERNAME"));
		this.out.println("</td>\n<td colspan=\"2\">");
		this.out.println("<input type=\"text\" name=\"nutzername\" size=\"25\">");
		this.out.println("</td>\n</tr>\n<tr>\n<td>");
		this.out.println(this.resourceBundle.getString("PASSWORT"));
		this.out.println("</td>\n<td>");
		this.out.println("<input type=\"password\" name=\"passwort\" size=\"15\">");
		this.out.println("</td>\n<td>");
		this.out.println("<input type=\"image\" name=\"absenden\" value=\"senden\" alt=\""+ this.resourceBundle.getString("ANMELDEN") +"\">");
		this.out.println("</td>\n</tr><tr>\n<td></td>\n<td colspan=\"2\">");
		//TODO mit Mailfunktion verbinden! 
		this.out.println("<a href=\"/SaatgutOnline/NoFunctionServlet\">" + this.resourceBundle.getString("PASSWORT_VERGESSEN") + "</a>");
		//TODO remove
		this.out.println(" (nf)");
		
		this.out.println("</td>\n</tr>");
		this.out.println("</form>");
		this.out.println("</table>");
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

	public void outWarenkorbPreview()
	{		
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("Warenkorb-Preview");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}
	
	public void outSuchfeld()
	{
		this.out.println("<table>");
		this.out.println("<tr>\n<td>");
		this.out.println("<form action=\"\" method=\"POST\">");
		this.out.println("<input type=\"text\" name=\"suchbegriff\" size=\"20\">");
		this.out.println("</td>\n<td>");
		this.out.println("<input type=\"submit\" name=\"suche\" value=\"" + this.resourceBundle.getString("SUCHEN") + "\">");
		this.out.println("</form>");
		this.out.println("</td>\n</tr>");
		this.out.println("</table>");
	}

}

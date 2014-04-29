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
		out.println("<!doctype html>\n"
				+ "<html>\n<head>\n"
				+ "<meta charset='UTF-8'>\n"
				+ "<link type=\"text/css\" href=\"resources/css/seitenlayout.css\" rel=\"stylesheet\" />\n"
				+ "<title></title>\n" + "</head>\n<body>");
		out.println("<table class=\"kopfbereich\">");
		out.println("<tr>\n<td colspan=\"2\">");
	}

	public void outKopfbereichEnde()
	{
		out.println("</td>\n</tr>"); // schliesst Kopfbereich
	}

	public void outInhaltsframeAnfang()
	{
		out.println("<table>");
		out.println("<tr>\n<td>");
	}

	public void outInhaltsframeNeueSpalte()
	{
		out.println("</td>\n<td>");
	}

	public void outInhaltsframeEnde()
	{
		out.println("</td>\n</tr>");
		out.println("</table>");
	}

	public void outLoginBereich()
	{		
		out.println("<table>");
		out.println("<form action=\"\" method=\"POST\">");
		out.println("<tr>\n<td>");
		out.println(this.resourceBundle.getString("NUTZERNAME"));
		out.println("</td>\n<td colspan=\"2\">");
		out.println("<input type=\"text\" name=\"nutzername\" size=\"25\">");
		out.println("</td>\n</tr>\n<tr>\n<td>");
		out.println(this.resourceBundle.getString("PASSWORT"));
		out.println("</td>\n<td>");
		out.println("<input type=\"password\" name=\"passwort\" size=\"15\">");
		out.println("</td>\n<td>");
		out.println("<input type=\"submit\" name=\"login\" value=\"" + this.resourceBundle.getString("ANMELDEN") + "\">");
		out.println("</td>\n</tr><tr>\n<td></td>\n<td colspan=\"2\">");
		//TODO mit Mailfunktion verbinden! 
		out.println("<a href=\"http://localhost:8080/SaatgutOnline/NoFunctionServlet\">" + this.resourceBundle.getString("PASSWORT_VERGESSEN") + "</a>");
		//TODO remove
		out.println(" (nf)");
		
		out.println("</td>\n</tr>");
		out.println("</form>");
		out.println("</table>");
	}

	public void outLogo()
	{
		out.println("<table>");
		out.println("<tr>\n<td>");
		out.println("<img src=\"resources/bilder/logo.png\" alt=\"Logo\">");
		out.println("</td>\n</tr>");
		out.println("</table>");
	}

	public void outSchriftzug()
	{
		out.println("<table>");
		out.println("<tr>\n<td>");
		out.println("Saatgut");
		out.println("</td>\n</tr>\n<tr>\n<td>");
		out.println("Online");
		out.println("</td>\n</tr>");
		out.println("</table>");
	}

	public void outSprachwahl()
	{		
		out.println("<table>");
		out.println("<tr>\n<td>\n</td>\n<td>");
		out.println("<form action=\"\" method=\"POST\">");
		out.println("<input type=\"image\" src=\"resources/bilder/flags_iso/24/de.png\" alt=\"de\">");
		out.println("<input type=\"hidden\" name=\"sprache\" value=\"de\"");
		out.println("</form>");
		out.println("</td>\n<td>");
		out.println("<form action=\"\" method=\"POST\">");
		out.println("<input type=\"image\" src=\"resources/bilder/flags_iso/24/us.png\" alt=\"en\">");
		out.println("<input type=\"hidden\" name=\"sprache\" value=\"en\"");
		out.println("</form>");
		out.println("</td>\n</tr>\n<td colspan=\"3\">\n</td>\n</tr>");
		out.println("</table>");
	}

	public void outWarenkorbPreview()
	{		
		out.println("<table>");
		out.println("<tr>\n<td>");
		out.println("Warenkorb-Preview");
		out.println("</td>\n</tr>");
		out.println("</table>");
	}
	
	public void outSuchfeld()
	{
		out.println("<table>");
		out.println("<tr>\n<td>");
		out.println("<form action=\"\" method=\"POST\">");
		out.println("<input type=\"text\" name=\"suchbegriff\" size=\"20\">");
		out.println("</td>\n<td>");
		out.println("<input type=\"submit\" name=\"suche\" value=\"" + this.resourceBundle.getString("SUCHEN") + "\">");
		out.println("</form>");
		out.println("</td>\n</tr>");
		out.println("</table>");
	}

}

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
		out.println("<!doctype html>\n" + "<html>\n<head>\n" + "<meta charset='UTF-8'>\n"
				+ "<link type=\"text/css\" href=\"resources/css/seitenlayout.css\" rel=\"stylesheet\" />\n"
				+ "<title></title>\n" + "</head>\n<body>\n");

		out.println("<table class=\"kopfbereich\">\n");

		out.println("<tr>\n<td colspan=\"2\">\n");
	}

	public void outKopfbereichEnde()
	{
		out.println("</td>\n</tr>\n"); // schliesst Kopfbereich
	}

	public void outInhaltsframeAnfang()
	{
		out.println("<table class=\"kopfinhalt\">\n<tr>\n<td>");
	}

	public void outInhaltsframeNeueSpalte()
	{
		out.println("</td>\n<td>");
	}

	public void outInhaltsframeEnde()
	{
		out.println("</td>\n</tr>\n</table>");
	}

	public void outLoginBereich()
	{
		out.println("<table>");
		out.println("<form action=\"\" method=\"POST\">");
		out.println("<tr>\n<td>" + this.resourceBundle.getString("NUTZERNAME")
				+ "</td><td colspan=\"2\"><input name=\"nutzername\" type=\"text\" size=\"25\">\n</td>\n</tr>");
		out.println("<tr>\n<td>\n" + this.resourceBundle.getString("PASSWORT")
				+ "</td><td><input name=\"passwort\" type=\"password\" size=\"15\"></td>");
		out.println("<td>\n<input name=\"login\" value=\"" + this.resourceBundle.getString("ANMELDEN")
				+ "\" type=\"submit\">\n</td>\n</tr>");
		out.println("<tr>\n<td></td><td colspan=\"2\">\n<a href=\"http://localhost:8080/SaatgutOnline/NoFunctionServlet\">"
				+ this.resourceBundle.getString("PASSWORT_VERGESSEN") + "</a> (nf)\n</td>\n</tr>");
		out.println("</form>");
		out.println("</table>");
	}

	public void outLogo()
	{
		out.println("<table>\n<tr>\n<td>");
		out.println("<img src=\"resources/bilder/logo.png\" alt=\"Logo\">");
		out.println("</td>\n</tr>\n</table>");
	}

	public void outSchriftzug()
	{
		out.println("<table>\n<tr>\n<td>");
		out.println("Saatgut");
		out.println("</td>\n</tr>\n<tr>\n<td>");
		out.println("Online");
		out.println("</td>\n</tr>\n</table>");
	}

	public void outSprachwahl()
	{
		out.println("<table>\n<tr><td></td>\n<td>");
		out.println("<form action=\"\" method=\"POST\">");
		out.println("<input type=\"image\" src=\"resources/bilder/flags_iso/24/de.png\" alt=\"de\">");
		out.println("<input type=\"hidden\" name=\"sprache\" value=\"de\"");
		out.println("</form>");
		out.println("</td>\n<td>");
		out.println("<form action=\"\" method=\"POST\">");
		out.println("<input type=\"image\" src=\"resources/bilder/flags_iso/24/us.png\" alt=\"en\">");
		out.println("<input type=\"hidden\" name=\"sprache\" value=\"en\"");
		out.println("</form>");
		out.println("</td>\n</tr>\n<td colspan=\"3\"");
		out.println("</td></tr></table>");
	}

	public void outWarenkorbPreview()
	{
		out.println("<table>\n<tr>\n<td>");
		out.println("Warenkorb-Preview");
		out.println("</td>\n</tr>\n</table>");
	}

}

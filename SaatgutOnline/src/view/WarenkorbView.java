package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		//TODO remove border='1'
		out.println("<table border='1'>");
		out.println("<tr>\n<td colspan=\"7\">");
		out.println(this.resourceBundle.getString("WARENKORB"));		
		out.println("</td>\n</tr>");
		out.println("<tr>\n<td colspan=\"7\">\n</tr>");
		out.println("<tr>\n<td>");
		out.println(this.resourceBundle.getString("NAME"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("MERKMALE"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("GEWICHT"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("EINZELPREIS"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("MENGE"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("GESAMTPREIS"));
		out.println("</td>\n<td>");
		out.println(this.resourceBundle.getString("POSITION_ENTFERNEN"));
		out.println("</td>\n<tr>");

		out.println("<tr>\n<td colspan=\"7\">\n</tr>");		
	}
	
	public void outWarenkorbEnde()
	{
		out.println("</table>");
	}
	
	
	
}

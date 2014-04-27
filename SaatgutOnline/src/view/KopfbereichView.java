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
	private ResourceBundle textbundle;
	
	
	public KopfbereichView(HttpServletRequest request, HttpServletResponse response)
	{
		//PrintWriter erzeugen
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
		Locale locale = (Locale)session.getAttribute("sprache");		
		this.textbundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
		
	}

	/*
	 * SCHRITT 4:
	 * 
	 * Ausgabemodule bauen.
	 * 
	 * --> weiter in KopfbereichController
	 */
	
	/**
	 * Oeffnet eine Tabelle.<br /> Oeffnet eine Tabellenzeile.<br /> Oeffnet eine
	 * Tabellenspalte.
	 */
	public void outKopfbereichAnfang()
	{
		out.println("<!doctype html>\n"
				+ "<html>\n<head>\n"
				+ "<meta charset='UTF-8'>\n"
				+ "<link type=\"text/css\" href=\"resources/css/seitenlayout.css\" rel=\"stylesheet\" />\n"
				+ "<title></title>\n"
				+ "</head>\n<body>\n");
		
		out.println("<table class=\"kopfbereich\">\n");
		
		out.println("<tr>\n<td colspan='2'>\n");
	}

	/**
	 * Schließt eine Tabellenspalte.<br /> Schließt eine Tabellenzeile.<br />
	 */
	public void outKopfbereichEnde()
	{
		out.println("</td>\n</tr>\n"); // schliesst Kopfbereich
	}
	
	/**
	 * Inhalte des Kopfbereichs
	 */
	public void outKopfbereichInhalt()
	{
		this.outKopfbereichAnfang();
		
		out.println("<table border='1' width=100% cellspacing='0' cellpadding='0'>\n<tr>\n<td>");		
		this.outLogo();			
		out.println(HtmlAusgabe.NEUE_SPALTE);		
		this.outSchriftzug();		
		out.println(HtmlAusgabe.NEUE_SPALTE);		
		this.outLoginBereich();		
		out.println(HtmlAusgabe.TABELLE_SCHLIESSEN);
		
		this.outKopfbereichEnde();
	}
	
	public void outLoginBereich()
	{
		out.println("<form action='' method='POST'>");
		out.println("<table border='1' cellspacing='0' cellpadding='0'>");
		out.println("<tr>\n<td colspan='2'>\n<input name='nutzername' value='Nutzername' type='text' size='25'>\n</td>\n</tr>");
		out.println("<tr>\n<td>\n<input name='passwort' value='Passwort' type='password' size='15'></td>");
		out.println("<td>\n<input name='login' value='Login' type='submit'>\n</td>\n</tr>");
		out.println("<tr>\n<td colspan='2'>\n<a href='http://localhost:8080/SaatgutOnline/NoFunctionServlet'>" + this.textbundle.getString("PASSWORT_VERGESSEN")+ "</a> (nf)\n</td>\n</tr>");
		out.println("</table>");
		out.println("</form>");		
	}
	
	public void outLogo()
	{		
		out.println("<table border='1' cellspacing='0' cellpadding='0'>\n<tr>\n<td>");
		out.println("<img src='resources/bilder/logo.jpg' width=100px height= alt='Logo'>");
		out.println("</td>\n</tr>\n</table>");		
	}
	
	public void outSchriftzug()
	{
		out.println("<table border='1' cellspacing='0' cellpadding='0'>\n<tr>\n<td>");
		out.println("<h2>Saatgut</h2>");
		out.println("</td>\n</tr>\n<tr>\n<td>");		
		out.println("<h3>Online</h3>");			
		out.println("</td>\n</tr>\n</table>");		
	}
	

	
	
}

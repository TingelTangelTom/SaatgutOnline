package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class KopfbereichView
{

	private HttpServletResponse response;
	private PrintWriter out;
	
	
	public KopfbereichView(HttpServletResponse response)
	{
		/*
		 * SCHRITT 3:
		 * 
		 * response entgegen nehmen und entschatten.
		 * Aus der response den PrintWriter erzeugen.
		 * 
		 * --> weiter: unten
		 */
		this.response = response;

		//PrintWriter erzeugen
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
		out.println("<tr>\n<td colspan='2'>\n<a href='http://localhost:8080/SaatgutOnline/NoFunctionServlet'>Passwort vergessen?</a> (nf)\n</td>\n</tr>");
		out.println("</table>");
		out.println("</form>");		
	}
	
	public void outLogo()
	{		
		out.println("<table border='1' cellspacing='0' cellpadding='0'>\n<tr>\n<td>");
		out.println("<img src='resources/bilder/logo.jpg' width=50% height=50% alt='Logo'>");
		out.println("</td>\n</tr>\n</table>");		
	}
	
	public void outSchriftzug()
	{
		out.println("<table border='1' cellspacing='0' cellpadding='0' width=15%>\n<tr>\n<td>");
		out.println("<h2>Saatgut</h2>");
		out.println("</td>\n</tr>\n<tr>\n<td>");		
		out.println("<h3>Online</h3>");			
		out.println("</td>\n</tr>\n</table>");		
	}
	

	
	
}

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
		out.println("<table border='0' width=100%  height=100% cellspacing='0' cellpadding='0'>"); // oeffnet Tabelle
		
		out.println("<tr style=\"height:15%; \">\n<td colspan='2' bgcolor='#EEEEEE'>"); // oeffnet Kopfbereich
	}

	/**
	 * Schließt eine Tabellenspalte.<br /> Schließt eine Tabellenzeile.<br />
	 * Oeffnet eine Tabellenzeile.<br /> Oeffnet eine Tabellenspalte.
	 */
	public void outKopfbereichEnde()
	{
		out.println("</td>\n</tr>"); // schliesst Kopfbereich
	}
	
	/**
	 * Inhalte des Kopfbereichs
	 */
	public void outKopfbereichInhalt()
	{
		out.println("Kopfbereich");
	}
	
	public void outLoginBereich()
	{
		
	}
	
	
}

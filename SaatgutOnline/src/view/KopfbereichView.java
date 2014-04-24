package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class KopfbereichView
{

	private HttpServletResponse response;
	private PrintWriter out;

	/**
	 * Konstruktor der Klasse KopfbereichView.</br> Erzeugt einen PrintWriter
	 * aus response.
	 * 
	 * @param response
	 *            response aus dem KopfbereichController als
	 *            <i>HttpServletResponse</i>.
	 * 
	 */
	public KopfbereichView(HttpServletResponse response)
	{
		this.response = response;

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

	/**
	 * Oeffnet eine Tabelle.</br> Oeffnet eine Tabellenzeile.</br> Oeffnet eine
	 * Tabellenspalte.
	 */
	public void outKopfbereichAnfang()
	{
		out.println("<table border='1'>\n<tr>\n<td>");
	}

	/**
	 * Schließt eine Tabellenspalte.</br> Schließt eine Tabellenzeile.</br>
	 * Oeffnet eine Tabellenzeile.</br> Oeffnet eine Tabellenspalte.
	 */
	public void outKopfbereichMitte()
	{
		out.println("</td>\n</tr>\n<tr>\n<td>");
	}
	
	
	/**
	 * Schliesst eine Tabellenzeile.</br>
	 * Oeffnet eine tabellenzeile.
	 */
	public void outKopfbereichEnde()
	{
		out.println("</td>\n<td>");
	}

	/**
	 * Inhalte des Kopfbereichs
	 */
	public void outKopfbereichInhalt()
	{
		out.println("Kopfbereich");
	}

}

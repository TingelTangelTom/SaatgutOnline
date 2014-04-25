package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class NavigationsbereichView
{

	
	private HttpServletResponse response;
	private PrintWriter out;
	
	/**
	 * Konstruktor der Klasse KopfbereichView.</br>
	 * Erzeugt einen PrintWriter aus response.
	 * 
	 * @param response
	 * response aus dem KopfbereichController als <i>HttpServletResponse</i>.
	 * 
	 */
	public NavigationsbereichView(HttpServletResponse response)
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

	public void outNavigationsabereichAnfang()
	{
		out.println("<tr style=\"vertical-align: top;\">\n<td width=15% bgcolor='#EEEEEE'>"); // oeffnet Navigationsbereich
		/*
		 * Ich habe den vertical-align eingesetzt, damit die Navigation und die Main jeweils oben links beginnen!
		 */
	}
	
	public void outNavigationsbereichInhalt()
	{
		out.println("Navigationsbereich");
	}
	
	
	public void outNavigationsbereichEnde()
	{
		out.println("</td>"); // schliesst Navigationsbereich
		out.println("<td>"); // oeffnet Hauptbereich
	}
}

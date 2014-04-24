package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class FussbereichView
{

	private HttpServletResponse response;
	private PrintWriter out;

	/**
	 * Konstruktor der Klasse FussbereichView.</br> Erzeugt einen PrintWriter
	 * aus response.
	 * 
	 * @param response
	 *            response aus dem KopfbereichController als
	 *            <i>HttpServletResponse</i>.
	 * 
	 */
	public FussbereichView(HttpServletResponse response)
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
	
	public void outFussbereichAnfang()
	{
		out.println("</td>\n</tr>\n<tr>\n<<td>");
	}
	
	public void outFussbereichEnde()
	{
		out.println("</td>\n</tr>\n</table>");
	}
	
	public void outFussbereichInhalt()
	{
		out.println("Fussbereich");
	}
}

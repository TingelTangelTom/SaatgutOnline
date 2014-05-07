package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UrlController;

/**
 * <p>
 * Die Klasse <code>DatenschutzView</code> ist für die Ausgabe der Datenschutzerklaerung zuständig.
 * </p>
 * 
 * @author Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class DatenschutzView
{
	/**
	 * <p>
	 * Konstruktor der Klasse<code>DatenschutzView</code>.
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @param datenschutzText
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see controller.UrlController
	 * @see java.io.PrintWriter
	 */
	public DatenschutzView(HttpServletRequest request, HttpServletResponse response, String datenschutzText)
	{
		// Für den Zurück-Link
		UrlController urlController = new UrlController(request);
		response.setContentType("text/html");
		PrintWriter out;
		try
		{
			out = response.getWriter();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		out.println(datenschutzText);
		out.println("<br><br><a href=\"" + urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}
}
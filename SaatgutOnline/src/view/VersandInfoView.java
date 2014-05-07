package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UrlController;

/**
 * <p>
 * Die Klasse <code>VersandInfoView</code> ist für die Ausgabe der Versand & Zahlungs Informationen zustaendig.
 * </p>
 * 
 * @author Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class VersandInfoView
{
	/**
	 * <p>
	 * Konstruktor der Klasse<code>VersandInfoView</code>.
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @param agbText
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public VersandInfoView(HttpServletRequest request, HttpServletResponse response, String versandInfoText)
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
		out.println(versandInfoText);
		out.println("<br><br><a href=\"" + urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}
}
package controller;

/**
 * <p>
 * Die Klasse <code>AbmeldungController</code> meldet den Kunden vom System ab und erzeugt als Ausgabe einen
 * passenden <code>AbmeldungView</code>
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see view.AbmeldungView
 */
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.AbmeldungView;
import view.KopfbereichView;

public class AbmeldungController
{
	private AbmeldungView abmeldungView;
	private HttpSession session;

	/**
	 * Konstruktor der Klasse <code>AbmeldungController</code> Session-Attribut "angemeldet" wird auf false gesetzt
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public AbmeldungController(HttpServletRequest request, HttpServletResponse response)
	{
		this.session = request.getSession();
		if ((boolean) this.session.getAttribute("angemeldet") == false)
		{
			this.abmeldungView = new AbmeldungView(request, response);
			this.abmeldungView.outAbmeldungView(request, response);
		}
		else
		{
			this.session.setAttribute("angemeldet", false);
			try
			{
				response.sendRedirect("Abmeldung"); // url korrekt!?
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

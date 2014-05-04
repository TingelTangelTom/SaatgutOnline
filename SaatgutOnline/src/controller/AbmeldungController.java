package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.AbmeldungView;
import view.KopfbereichView;

public class AbmeldungController {
	/**
	 * Objekt der Klasse <code>KopfbereichView</code>
	 * @see view.KopfbereichView
	 */
	private AbmeldungView abmeldungView;
	/**
	 * Objekt der Klasse <code>HttpSession</code>
	 * @see HttpSession
	 */
	private HttpSession session;
		
	/**
	 * Konstruktor der Klasse <code>KopfbereichController</code>
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @param response - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public AbmeldungController(HttpServletRequest request, HttpServletResponse response)
	{	
		this.session = request.getSession();
		if((boolean)this.session.getAttribute("angemeldet") == false) {
			this.abmeldungView = new AbmeldungView(request, response);
			this.abmeldungView.outAbmeldungView(request, response);
		} else {
			this.session.setAttribute("angemeldet", false);
			try {
				response.sendRedirect("Abmeldung");	// url korrekt!?
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Formatiert die Darstellung fuer den Kopfbereich und gibt diese aus
	 */
}

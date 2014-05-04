package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.AnmeldungErfolgreichView;
import view.KopfbereichView;

public class AnmeldungErfolgreichController {
	/**
	 * Objekt der Klasse <code>KopfbereichView</code>
	 * @see view.KopfbereichView
	 */
	private AnmeldungErfolgreichView anmeldungErfolgreichView;
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
	public AnmeldungErfolgreichController(HttpServletRequest request, HttpServletResponse response)
	{	
		this.session = request.getSession();
		this.anmeldungErfolgreichView = new AnmeldungErfolgreichView(request, response);	
		this.anmeldungErfolgreichView.outAnmeldungErfolgreichView();
	}
}

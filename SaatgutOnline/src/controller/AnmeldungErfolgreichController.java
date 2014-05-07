package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.AnmeldungErfolgreichView;
/**
 * <p>
 * Die Klasse <code>AnmeldungErfolgreichController</code> erzeugt einen <code>anmeldungErfolgreichView</code>.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see AnmeldungErfolgreichView
 */
public class AnmeldungErfolgreichController
{
	private AnmeldungErfolgreichView anmeldungErfolgreichView;
	private HttpSession session;

	/**
	 * Konstruktor der Klasse <code>AnmeldungErfolgreichController</code>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
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

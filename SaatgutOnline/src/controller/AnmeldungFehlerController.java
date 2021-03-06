package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.AnmeldungFehlerView;

/**
 * <p>
 * Die Klasse <code>AnmeldungFehlerController</code> erzeugt einen <code>AnmeldungFehlerView</code> und ruft die
 * passende Ausgabemethode derselben auf.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see AnmeldungFehlerView
 */
public class AnmeldungFehlerController
{
	/**
	 * Erzeugt einen View fuer die Ausgabe und  ruft die Ausgabemethode auf
	 * @param request
	 * @param response
	 */
	public AnmeldungFehlerController(HttpServletRequest request, HttpServletResponse response)
	{
		AnmeldungFehlerView anmeldungFehlerView = new AnmeldungFehlerView(request, response);
		anmeldungFehlerView.outAnmeldungFehler(request, response);
	}
}

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.FussbereichView;

/**
 * <p>
 * Die Klasse <code>FussbereichController</code> stellt die Ausgabemethode zur Verfuegung.
 * </p>
 * 
 * @author Tom Weigelt
 * @version 1.0
 * @since 1.7.0_51
 */
public class FussbereichController
{
	private FussbereichView fussbereichView;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>WarenkorbController</code>.
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public FussbereichController(HttpServletRequest request, HttpServletResponse response)
	{
		this.fussbereichView = new FussbereichView(request, response);
	}

	/**
	 * <p>
	 * Gibt die Darstellung des Fussbereichs aus.
	 * </p>
	 */
	public void outFussbereichAnzeigen()
	{
		this.fussbereichView.outFussbereichAnfang();
		this.fussbereichView.outFussbereichInhalt();
		this.fussbereichView.outFussbereichEnde();
	}
}
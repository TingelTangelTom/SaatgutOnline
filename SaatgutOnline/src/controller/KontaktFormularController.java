package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.KontaktFormularView;

/**
 * <p>
 * Die Klasse <code>KontaktFormularController</code> ruft den KontaktFormularView auf.
 * </p>
 * 
 * @author Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class KontaktFormularController
{
	/**
	 * <p>
	 * Der Konstruktor der Klasse <code>KontaktFormularController</code> ruft den KontaktFormularView auf.
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see view.KontaktFormularView
	 */
	public KontaktFormularController(HttpServletRequest request, HttpServletResponse response)
	{
		KontaktFormularView kontaktFormularView = new KontaktFormularView(request, response);
		kontaktFormularView.outKontaktFormular(request, response);
	}
}

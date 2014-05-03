package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.KontaktFormularView;

/**
 * Diese Klasse ruft den KontaktFormularView auf.
 * 
 * @author Anja
 *
 */
public class KontaktFormularController {
	
	/**
	 * Konstruktor für den KontaktFormularController.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @author Anja
	 */
	public KontaktFormularController(HttpServletRequest request, HttpServletResponse response) {
		
		KontaktFormularView kontaktFormularView = new KontaktFormularView(request, response);
		kontaktFormularView.outKontaktFormular(request, response);
		
	}

}

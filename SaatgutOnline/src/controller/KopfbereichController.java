package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.KopfbereichView;

/**
 * Die Klasse KopfbereichController stellt Kontrollstrukturen fuer die Darstellung des Kopfbereichs zur Verfuegung 
 * @author Tom Weigelt
 *
 */
public class KopfbereichController
{
	/**
	 * Objekt der Klasse <code>KopfbereichView</code>
	 * @see view.KopfbereichView
	 */
	private KopfbereichView kopfbereichView;
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
	public KopfbereichController(HttpServletRequest request, HttpServletResponse response)
	{	
		this.session = request.getSession();
		this.kopfbereichView = new KopfbereichView(request, response);		
	}

	/**
	 * Formatiert die Darstellung fuer den Kopfbereich und gibt diese aus
	 */
	public void outKopfbereichAnzeigen()
	{					
		this.kopfbereichView.outKopfbereichAnfang();
		
		this.kopfbereichView.outInhaltsRahmenAnfang();
		
		this.kopfbereichView.outLogo();
		
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		
		this.kopfbereichView.outSchriftzug();
		
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		
		if((boolean) this.session.getAttribute("angemeldet"))
		{
			this.kopfbereichView.outLogoutBereich();
		}
		else
		{
			this.kopfbereichView.outLoginBereich();
		}
		
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		
		this.kopfbereichView.outWarenkorbLink();
						
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		
		this.kopfbereichView.outSuchfeld();
		
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		
		this.kopfbereichView.outSprachwahl();
		
		this.kopfbereichView.outInhaltsRahmenEnde();
		
		this.kopfbereichView.outKopfbereichEnde();	
	}
}

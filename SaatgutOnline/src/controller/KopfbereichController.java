package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.KopfbereichView;

/**
 * <p>
 * Die Klasse KopfbereichController stellt Kontrollstrukturen fuer die Darstellung des Kopfbereichs zur Verfuegung.
 * </p>
 * 
 * @author Tom Weigelt
 * @version 1.0
 * @since 1.7.0_51
 */
public class KopfbereichController
{
	private KopfbereichView kopfbereichView;
	private HttpSession session;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>KopfbereichController</code>.
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public KopfbereichController(HttpServletRequest request, HttpServletResponse response)
	{
		this.session = request.getSession();
		this.kopfbereichView = new KopfbereichView(request, response);
	}

	/**
	 * <p>
	 * Formatiert durch den situationsbedingten Aufruf von Methoden der Klasse <code>KopfbereichView</code> die
	 * Darstellung des Kopfbereichs und gibt diese aus.
	 * </p>
	 * 
	 * @see view.KopfbereichView
	 */
	public void outKopfbereichAnzeigen()
	{
		this.kopfbereichView.outKopfbereichAnfang();
		this.kopfbereichView.outInhaltsRahmenAnfang();
		this.kopfbereichView.outLogo();
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		this.kopfbereichView.outSchriftzug();
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		if ((boolean) this.session.getAttribute("angemeldet"))
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
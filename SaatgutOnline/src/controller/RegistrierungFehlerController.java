package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.RegistrierungFehlerView;

/**
 * <p>
 * Die Klasse <code>RegistrierungFehlerController</code> erzeugt einen <code>RegistrierungFehlerView</code> und
 * ruft die passende Ausgabemethode derselben auf.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see RegistrierungFehlerView
 */
public class RegistrierungFehlerController
{
	public RegistrierungFehlerController(HttpServletRequest request, HttpServletResponse response)
	{
		RegistrierungFehlerView registrierungFehlerView = new RegistrierungFehlerView(request, response);
		registrierungFehlerView.outRegistrierungFehler(request, response);
	}
}

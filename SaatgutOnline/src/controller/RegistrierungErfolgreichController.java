package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.RegistrierungErfolgreichView;
import view.RegistrierungView;

/**
 * <p>
 * Die Klasse <code>RegistrierungErfolgreichController</code> erzeugt einen
 * <code>RegistrierungErfolgreichView</code> und ruft die passende Ausgabemethode derselben auf.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see RegistrierungErfolgreichView
 */
public class RegistrierungErfolgreichController
{
	public RegistrierungErfolgreichController(HttpServletRequest request, HttpServletResponse response)
	{
		RegistrierungErfolgreichView registrierungErfoglreichView = new RegistrierungErfolgreichView(request,
				response);
		registrierungErfoglreichView.outRegistrierungErfolgreich(request, response);
	}
}

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.AnmeldungFehlerView;
import view.RegistrierungView;
/**
 * <p>Die Klasse <code>AnmeldungFehlerController</code>
 * erzeugt einen <code>RegistrierungView</code> und ruft
 * die passende Ausgabemethode derselben auf.
 * </p>
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see RegistrierungView
 */	
public class RegistrierungController {
	
	public RegistrierungController (HttpServletRequest request, HttpServletResponse response) {
		RegistrierungView registrierungView = new RegistrierungView(request, response);
		registrierungView.outResistrierungFormular(request, response);
	}
}

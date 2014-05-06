package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.RegistrierungVerarbeitungController;
/**
 * <p>Die Klasse <code>RegistrierungErfolgreichView</code>
 * erzeugt einen <code>PrintWriter</code> und gibt
 * gemaess mittels resourcebundle uebergebener Spracheinstellungen
 * HTML-Code aus.
 * </p>
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 */
public class RegistrierungErfolgreichView {
	private PrintWriter out;
	private ResourceBundle resourceBundle;
	private HttpSession session;
	private RegistrierungVerarbeitungController registrierungVerarbeitungController;
	
	/**
	 * Konstruktor der Klasse. Liest Session-Variable ein 
	 * stellt Lokalisierung bereit.
	 * @param request
	 * @param response
	 */
	public RegistrierungErfolgreichView (HttpServletRequest request, HttpServletResponse response) {
		
		this.session = request.getSession();
		this.registrierungVerarbeitungController = (RegistrierungVerarbeitungController) session.getAttribute("RegistrierungVerarbeitungController");
		
		response.setContentType("text/html");
		try
		{
			this.out = response.getWriter();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Locale locale = (Locale) session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "."
				+ getClass().getSimpleName(), locale);
	}
	
	/**
	 * Ausgabemethode	
	 * @param request
	 * @param response
	 */
	public void outRegistrierungErfolgreich(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println("<h1>" + (this.resourceBundle.getString("UEBERSCHRIFT")) + "</h1>");
		out.println("<p>" + (this.resourceBundle.getString("TEXT")) + "</p>");
	}		
}
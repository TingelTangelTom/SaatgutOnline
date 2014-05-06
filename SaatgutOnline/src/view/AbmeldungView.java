package view;
/**
 * <p>Die Klasse <code>AbmeldungView</code>
 * erzeugt einen <code>PrintWriter</code> und gibt
 * gemaess mittels resourcebundle uebergebener Spracheinstellungen
 * den passenden internationalisierten Text aus.
 * </p>
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see AnmeldungFehlerView
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AbmeldungView {
	/**
	 * Objekt der Klasse <code>PrintWriter</code>
	 * 
	 * @see java.io.PrintWriter
	 */
	private PrintWriter out;
	/**
	 * Objekt der Klasse <code>ResourceBundle</code>
	 * 
	 * @see java.util.ResourceBundle
	 */
	private ResourceBundle resourceBundle;

	/**
	 * Konstruktor der Klasse <code>AbmeldungView</code>
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public AbmeldungView(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
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
	 * Stellt die Html-Ausgabe fuer die Abmeldung zur Verfuegung
	 */
	public void outAbmeldungView(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println("<h1>" + this.resourceBundle.getString("UEBERSCHRIFT") + "</h1>");
	}	
}

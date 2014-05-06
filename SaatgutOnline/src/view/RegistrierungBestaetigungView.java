package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * <p>Die Klasse <code>RegistrierungBestaetigungView</code>
 * erzeugt einen <code>PrintWriter</code> und gibt
 * gemaess mittels resourcebundle uebergebener Spracheinstellungen
 * ein passendes internationalisiertes
 * Registrierungsformular aus.
 * </p>
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 */
public class RegistrierungBestaetigungView {
	private PrintWriter out;
	private ResourceBundle resourceBundle;

	/**
	 * Konstruktor der Klasse <code>RegistrierungBestaetigungView</code>
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public RegistrierungBestaetigungView(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		
		response.setContentType("text/html");
		try
		{
			this.out = response.getWriter();
		} catch (IOException e)
		{
			System.out.println("PrintWriter nicht erstellt!");
			e.printStackTrace();
		}
		
		Locale locale = (Locale) session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "."
				+ getClass().getSimpleName(), locale);
	}

	/**
	 * Stellt die internationalisierte Html-Ausgabe zur Verfuegung
	 */
	public void outRegistrierungBestaetigungView() {
		this.out.println("<h1>" + (this.resourceBundle.getString("UEBERSCHRIFT")) + "</h1>");
	}
}

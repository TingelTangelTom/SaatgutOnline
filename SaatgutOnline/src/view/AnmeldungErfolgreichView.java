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
 * <p>
 * Die Klasse <code>AnmeldungErfolgreichView</code> erzeugt einen <code>PrintWriter</code> und gibt gemaess mittels
 * resourcebundle uebergebener Spracheinstellungen den passenden internationalisierten Text aus.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see AnmeldungFehlerView
 */
public class AnmeldungErfolgreichView
{
	private PrintWriter out;
	private ResourceBundle resourceBundle;

	/**
	 * Konstruktor der Klasse <code>KopfbereichView</code>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public AnmeldungErfolgreichView(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		try
		{
			this.out = response.getWriter();
		}
		catch (IOException e)
		{
		}
		Locale locale = (Locale) session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "."
				+ getClass().getSimpleName(), locale);
	}

	/**
	 * Erzeugt HTML-Code
	 */
	public void outAnmeldungErfolgreichView()
	{
		this.out.println("<h1>" + (this.resourceBundle.getString("UEBERSCHRIFT")) + "</h1>");
	}
}
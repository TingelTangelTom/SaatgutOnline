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
 * Die Klasse <code>RegistrierungView</code> erzeugt einen <code>PrintWriter</code> und gibt gemaess mittels
 * resourcebundle uebergebener Spracheinstellungen ein passendes internationalisiertes Registrierungsformular aus.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 */
public class RegistrierungView
{
	private PrintWriter out;
	private ResourceBundle resourceBundle;

	/**
	 * Konstruktor. Holt die entsprechende Sprachdatei.
	 * 
	 * @param request
	 * @param response
	 */
	public RegistrierungView(HttpServletRequest request, HttpServletResponse response)
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
	 * Ausgabefunktion, die das Registrierunsformular als HTML-Code generiert
	 * 
	 * @param request
	 * @param response
	 */
	public void outResistrierungFormular(HttpServletRequest request, HttpServletResponse response)
	{
		response.setContentType("text/html");
		try
		{
			out = response.getWriter();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		this.out.println("<h1>"
				+ this.resourceBundle.getString("UEBERSCHRIFT")
				+ "</h1>"
				+ "<form action=/SaatgutOnline/RegistrierungVerarbeitung method=\"POST\">"
				+ "<table width=\"200\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">"
				+ "<tr><td><label for =\"Anrede\">"
				+ this.resourceBundle.getString("ANREDE")
				+ "</label></td>"
				+ "<td><select name=\"Anrede\" id=\"Anrede\" title=\"Anrede\">\n"
				+ "<option value=\"0\">"
				+ this.resourceBundle.getString("FRAU")
				+ "</option><option value=\"1\">"
				+ this.resourceBundle.getString("HERR")
				+ "</option></select></td></tr>\n"
				+ "<tr><td><label for=\"Vorname\">"
				+ this.resourceBundle.getString("VORNAME")
				+ "</label></td>\n"
				+ "<td><input name=\"Vorname\" type=\"text\" id=\"Vorname\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"Nachname\">"
				+ this.resourceBundle.getString("NACHNAME")
				+ "</label></td>\n"
				+ "<td><input name=\"Nachname\" type=\"text\" id=\"Nachname\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"Firma\">"
				+ this.resourceBundle.getString("FIRMA")
				+ "</label></td>\n"
				+ "<td><input name=\"Firma\" type=\"text\" id=\"Firma\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"Strasse\">"
				+ this.resourceBundle.getString("STRASSE")
				+ "</label></td>\n"
				+ "<td><input name=\"Strasse\" type=\"text\" id=\"Strasse\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"Hausnummer\">"
				+ this.resourceBundle.getString("HAUSNUMMER")
				+ "</label></td>\n"
				+ "<td><input name=\"Hausnummer\" type=\"text\" id=\"Hausnummer\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"Plz\">"
				+ this.resourceBundle.getString("PLZ")
				+ "</label></td>\n"
				+ "<td><input name=\"Plz\" type=\"text\" id=\"Plz\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"Ort\">"
				+ this.resourceBundle.getString("ORT")
				+ "</label></td>\n"
				+ "<td><input name=\"Ort\" type=\"text\" id=\"Ort\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"Emailadresse\">"
				+ this.resourceBundle.getString("EMAILADRESSE")
				+ "</label></td>\n"
				+ "<td><input name=\"Emailadresse\" type=\"text\" id=\"Emailadresse\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"Telefon\">"
				+ this.resourceBundle.getString("TELEFONNUMMER")
				+ "</label></td>\n"
				+ "<td><input name=\"Telefon\" type=\"text\" id=\"Telefon\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"Benutzername\">"
				+ this.resourceBundle.getString("BENUTZERNAME")
				+ "</label></td>\n"
				+ "<td><input name=\"Benutzername\" type=\"text\" id=\"Benutzername\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"Passwort\">"
				+ this.resourceBundle.getString("PASSWORT")
				+ "</label></td>\n"
				+ "<td><input type=\"password\" name=\"Passwort\" type=\"text\" id=\"Passwort\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td><label for=\"PasswortWiederholung<\">"
				+ this.resourceBundle.getString("PASSWORTWIEDERHOLUNG")
				+ "</label></td>\n"
				+ "<td><input type=\"password\"  name=\"PasswortWiederholung\" type=\"text\" id=\"PasswortWiederholung\" size=\"35\" maxlength=\"60\"></td></tr>\n"
				+ "<tr><td valign=\"top\">&nbsp;</td><td><div align=\"left\">"
				+ "<input name=\"submit\" type=\"submit\" id=\"submit\" value=\""
				+ this.resourceBundle.getString("SENDEN") + "\"></div></td></tr></table></form>");
	}
}
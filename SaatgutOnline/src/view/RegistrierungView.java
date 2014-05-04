package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrierungView {
	
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
	
	public RegistrierungView (HttpServletRequest request, HttpServletResponse response) {
		
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
	
	public void outResistrierungFormular(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.out.println("<h1>" + this.resourceBundle.getString("UEBERSCHRIFT") + "</h1>"
				+ "<p>" + this.resourceBundle.getString("UEBERSCHRIFT") + "</p>"
			    + "<form action=/SaatgutOnline/RegistrierungVerarbeitung method=\"POST\">"
			    + "<table width=\"200\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">"
			  
			    + "<tr><td>Anrede:</td><td><select name=\"" + this.resourceBundle.getString("ANREDE") + "\" id=\"Anrede\" title=\"Anrede\">\n"
				+ "<option>" + this.resourceBundle.getString("FRAU") + "</option><option>" + this.resourceBundle.getString("HERR") + "</option></select></td></tr>\n"
			    
			    + "<tr><td><label for=\"Vorname\">" + this.resourceBundle.getString("VORNAME") + "</label></td>\n"
				+ "<td><input name=\"Vorname\" type=\"text\" id=\"Vorname\" size=\"35\" maxlength=\"60\"></td></tr>\n"

 				+ "<tr><td><label for=\"Nachname\">" + this.resourceBundle.getString("NACHNAME") + "</label></td>\n"
 				+ "<td><input name=\"Nachname\" type=\"text\" id=\"Nachname\" size=\"35\" maxlength=\"60\"></td></tr>\n"

 				+ "<tr><td><label for=\"Firma\">" + this.resourceBundle.getString("FIRMA") + "</label></td>\n"
 				+ "<td><input name=\"Firma\" type=\"text\" id=\"Firma\" size=\"35\" maxlength=\"60\"></td></tr>\n"

 				+ "<tr><td><label for=\"Strasse\">" + this.resourceBundle.getString("STRASSE") + "</label></td>\n"
 				+ "<td><input name=\"Strasse\" type=\"text\" id=\"Strasse\" size=\"35\" maxlength=\"60\"></td></tr>\n"

			    
 				+ "<tr><td><label for=\"Hausnummer\">" + this.resourceBundle.getString("HAUSNUMMER") + "</label></td>\n"
 				+ "<td><input name=\"Hausnummer\" type=\"text\" id=\"Hausnummer\" size=\"35\" maxlength=\"60\"></td></tr>\n"
 				
 				+ "<tr><td><label for=\"plz\">" + this.resourceBundle.getString("PLZ") + "</label></td>\n"
 				+ "<td><input name=\"plz\" type=\"text\" id=\"plz\" size=\"35\" maxlength=\"60\"></td></tr>\n"
 				
 				+ "<tr><td><label for=\"Ort\">" + this.resourceBundle.getString("ORT") + "</label></td>\n"
 				+ "<td><input name=\"Ort\" type=\"text\" id=\"Ort\" size=\"35\" maxlength=\"60\"></td></tr>\n"

 				+ "<tr><td><label for=\"E-Mail-Adresse\">" + this.resourceBundle.getString("EMAILADRESSE") + "</label></td>\n"
 				+ "<td><input name=\"E-Mail-Adresse\" type=\"text\" id=\"E-Mail-Adresse\" size=\"35\" maxlength=\"60\"></td></tr>\n"
 				
 				+ "<tr><td><label for=\"Telefonnummer\">" + this.resourceBundle.getString("TELEFONNUMMER") + "</label></td>\n"
 				+ "<td><input name=\"Telefonnummer\" type=\"text\" id=\"Telefonnummer\" size=\"35\" maxlength=\"60\"></td></tr>\n"
 				
 				+ "<tr><td><label for=\"Benutzername\">" + this.resourceBundle.getString("BENUTZERNAME") + "</label></td>\n"
 				+ "<td><input name=\"Benutzername\" type=\"text\" id=\"Benutzername\" size=\"35\" maxlength=\"60\"></td></tr>\n"
 				
 				+ "<tr><td><label for=\"Passwort\">" + this.resourceBundle.getString("PASSWORT") + "</label></td>\n"
 				+ "<td><input name=\"Passwort\" type=\"text\" id=\"Passwort\" size=\"35\" maxlength=\"60\"></td></tr>\n"
 				
 				+ "<tr><td><label for=\"PasswortWiederholung<\">" + this.resourceBundle.getString("PASSWORTWIEDERHOLUNG") + "</label></td>\n"
 				+ "<td><input name=\"PasswortWiederholung\" type=\"text\" id=\"PasswortWiederholung\" size=\"35\" maxlength=\"60\"></td></tr>\n"
 				
			    + "<tr><td valign=\"top\">&nbsp;</td><td><div align=\"left\">"
			    + "<input name=\"submit\" type=\"submit\" id=\"submit\" value=\"" + this.resourceBundle.getString("SENDEN") + "\"></div></td></tr></table></form>");
	}
	
}
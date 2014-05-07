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
 * <p>Die Klasse <code>RegistrierungFehlerView</code>
 * erzeugt einen <code>PrintWriter</code> und gibt
 * gemaess mittels resourcebundle uebergebener Spracheinstellungen
 * HTML-Code aus.
 * </p>
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 */
public class RegistrierungFehlerView {
	private PrintWriter out;
	private ResourceBundle resourceBundle;
	private HttpSession session;
	private RegistrierungVerarbeitungController registrierungVerarbeitungController;
	
	/**
	 * Konstruktor des Views.
	 * @param request
	 * @param response
	 */
	public RegistrierungFehlerView (HttpServletRequest request, HttpServletResponse response) {
		
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
	 * Internationalisierte Ausgabemethode.
	 * Zuvor falsch oder nicht regelgemaess ausgefuellte 
	 * Formularfelder werden hervorgehoben und alle Formularfelder
	 * mit Ausnahme der Passwortfelder werden mit den zuvor eingegebenen
	 * Werten belegt.
	 * @param request
	 * @param response
	 */
	public void outRegistrierungFehler(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			return;
		}
		this.out.println("<h1>" + this.resourceBundle.getString("UEBERSCHRIFT") + "</h1>"
				+ "<p>" + this.resourceBundle.getString("TEXT") + "</p>"
			    + "<form action=/SaatgutOnline/RegistrierungVerarbeitung method=\"POST\">"
			    + "<table width=\"200\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">");
		
		if (registrierungVerarbeitungController.getKunde().getGeschlecht() == 1) {
			this.out.println("<tr><td><label for =\"Anrede\">" + this.resourceBundle.getString("ANREDE") + "</label></td>"
		    		+ "<td><select name=\"Anrede\" id=\"Anrede\" title=\"Anrede\" value=\"" + session.getAttribute("Anrede") + "\">\n"
					+ "<option value=\"0\">" + this.resourceBundle.getString("FRAU") + "</option><option selected value=\"1\">" + this.resourceBundle.getString("HERR") + "</option></select></td></tr>\n");
		} else {
			this.out.println("<tr><td><label for =\"Anrede\">" + this.resourceBundle.getString("ANREDE") + "</label></td>"
		    		+ "<td><select name=\"Anrede\" id=\"Anrede\" title=\"Anrede\" value=\"" + session.getAttribute("Anrede") + "\">\n"
					+ "<option selected value=\"0\">" + this.resourceBundle.getString("FRAU") + "</option><option value=\"1\">" + this.resourceBundle.getString("HERR") + "</option></select></td></tr>\n");
		}
			  
	 	if (registrierungVerarbeitungController.isVornameGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Vorname\">" + this.resourceBundle.getString("VORNAME") + "</label></td>\n"
					+ "<td><input value=\"" + registrierungVerarbeitungController.getKunde().getVorname() + "\" name=\"Vorname\" type=\"text\" id=\"Vorname\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Vorname\">" + this.resourceBundle.getString("VORNAME") + "</label></td>\n"
					+ "<td><input style =\"color:red;border-color:red\" value=\"" + registrierungVerarbeitungController.getKunde().getVorname() + "\" name=\"Vorname\" type=\"text\" id=\"Vorname\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}

	 	if (registrierungVerarbeitungController.isNachnameGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Nachname\">" + this.resourceBundle.getString("NACHNAME") + "</label></td>\n"
	 				+ "<td><input value=\"" + registrierungVerarbeitungController.getKunde().getNachname() + "\" name=\"Nachname\" type=\"text\" id=\"Nachname\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Nachname\">" + this.resourceBundle.getString("NACHNAME") + "</label></td>\n"
	 				+ "<td><input style =\"color:red;border-color:red\" value=\"" + registrierungVerarbeitungController.getKunde().getNachname() + "\" name=\"Nachname\" type=\"text\" id=\"Nachname\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}

	 	if (registrierungVerarbeitungController.isFirmaGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Firma\">" + this.resourceBundle.getString("FIRMA") + "</label></td>\n"
	 				+ "<td><input value=\"" + registrierungVerarbeitungController.getKunde().getFirma() + "\" name=\"Firma\" type=\"text\" id=\"Firma\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Nachname\">" + this.resourceBundle.getString("FIRMA") + "</label></td>\n"
	 				+ "<td><input style =\"color:red;border-color:red\" value=\"" + registrierungVerarbeitungController.getKunde().getFirma() + "\" name=\"Firma\" type=\"text\" id=\"Firma\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}

	 	if (registrierungVerarbeitungController.isStrasseGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Strasse\">" + this.resourceBundle.getString("STRASSE") + "</label></td>\n"
	 				+ "<td><input value=\"" + registrierungVerarbeitungController.getKunde().getStrasse() + "\" name=\"Strasse\" type=\"text\" id=\"Strasse\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Strasse\">" + this.resourceBundle.getString("STRASSE") + "</label></td>\n"
	 				+ "<td><input style =\"color:red;border-color:red\" value=\"" + registrierungVerarbeitungController.getKunde().getStrasse() + "\" name=\"Strasse\" type=\"text\" id=\"Strasse\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}
	 	
	 	if (registrierungVerarbeitungController.isHausnummerGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Hausnummer\">" + this.resourceBundle.getString("HAUSNUMMER") + "</label></td>\n"
	 				+ "<td><input value=\"" + registrierungVerarbeitungController.getKunde().getHausnummer() + "\" name=\"Hausnummer\" type=\"text\" id=\"Hausnummer\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Hausnummer\">" + this.resourceBundle.getString("HAUSNUMMER") + "</label></td>\n"
	 				+ "<td><input style =\"color:red;border-color:red\" value=\"" + registrierungVerarbeitungController.getKunde().getHausnummer() + "\" name=\"Hausnummer\" type=\"text\" id=\"Hausnummer\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}
	 	
	 	if (registrierungVerarbeitungController.isPostleitzahlGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Plz\">" + this.resourceBundle.getString("PLZ") + "</label></td>\n"
	 				+ "<td><input value=\"" + registrierungVerarbeitungController.getKunde().getPostleitzahl() + "\" name=\"Plz\" type=\"text\" id=\"Plz\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Plz\">" + this.resourceBundle.getString("PLZ") + "</label></td>\n"
	 				+ "<td><input style =\"color:red;border-color:red\" value=\"" + registrierungVerarbeitungController.getKunde().getPostleitzahl() + "\" name=\"Plz\" type=\"text\" id=\"Plz\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}
	 	
	 	if (registrierungVerarbeitungController.isOrtGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Ort\">" + this.resourceBundle.getString("ORT") + "</label></td>\n"
	 				+ "<td><input value=\"" + registrierungVerarbeitungController.getKunde().getOrt() + "\" name=\"Ort\" type=\"text\" id=\"Ort\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Ort\">" + this.resourceBundle.getString("ORT") + "</label></td>\n"
	 				+ "<td><input style =\"color:red;border-color:red\" value=\"" + registrierungVerarbeitungController.getKunde().getOrt() + "\" name=\"Ort\" type=\"text\" id=\"Ort\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}

	 	if (registrierungVerarbeitungController.isEmailadresseGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Emailadresse\">" + this.resourceBundle.getString("EMAILADRESSE") + "</label></td>\n"
	 				+ "<td><input value=\"" + registrierungVerarbeitungController.getKunde().getEmailadresse() + "\" name=\"Emailadresse\" type=\"text\" id=\"Emailadresse\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Emailadresse\">" + this.resourceBundle.getString("EMAILADRESSE") + "</label></td>\n"
	 				+ "<td><input style =\"color:red;border-color:red\" value=\"" + registrierungVerarbeitungController.getKunde().getEmailadresse() + "\" name=\"Emailadresse\" type=\"text\" id=\"Emailadresse\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}

	 	if (registrierungVerarbeitungController.isTelefonGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Telefon\">" + this.resourceBundle.getString("TELEFONNUMMER") + "</label></td>\n"
	 				+ "<td><input value=\"" + registrierungVerarbeitungController.getKunde().getTelefon() + "\" name=\"Telefon\" type=\"text\" id=\"Telefon\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Telefon\">" + this.resourceBundle.getString("TELEFONNUMMER") + "</label></td>\n"
	 				+ "<td><input style =\"color:red;border-color:red\" value=\"" + registrierungVerarbeitungController.getKunde().getTelefon() + "\" name=\"Telefon\" type=\"text\" id=\"Telefon\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}

	 	if (registrierungVerarbeitungController.isBenutzernameGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Benutzername\">" + this.resourceBundle.getString("BENUTZERNAME") + "</label></td>\n"
	 				+ "<td><input value=\"" + registrierungVerarbeitungController.getKunde().getBenutzername() + "\" name=\"Benutzername\" type=\"text\" id=\"Benutzername\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Benutzername\">" + this.resourceBundle.getString("BENUTZERNAME") + "</label></td>\n"
	 				+ "<td><input style =\"color:red;border-color:red\" value=\"" + registrierungVerarbeitungController.getKunde().getBenutzername() + "\" name=\"Benutzername\" type=\"text\" id=\"Benutzername\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}
	 	
	 	if (registrierungVerarbeitungController.isPasswortGeprueft()) {
	 		this.out.println("<tr><td><label for=\"Passwort\">" + this.resourceBundle.getString("PASSWORT") + "</label></td>\n"
	 				+ "<td><input type=\"password\" name=\"Passwort\" type=\"text\" id=\"Passwort\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"Passwort\">" + this.resourceBundle.getString("PASSWORT") + "</label></td>\n"
	 				+ "<td><input  type=\"password\" style =\"color:red;border-color:red\" name=\"Passwort\" type=\"text\" id=\"Passwort\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}
	 	
	 	if (registrierungVerarbeitungController.isPasswortWiederholungGeprueft()) {
	 		this.out.println("<tr><td><label for=\"PasswortWiederholung\">" + this.resourceBundle.getString("PASSWORTWIEDERHOLUNG") + "</label></td>\n"
	 				+ "<td><input type=\"password\" name=\"PasswortWiederholung\" type=\"text\" id=\"PasswortWiederholung\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	} else {
	 		this.out.println("<tr><td><label for=\"PasswortWiederholung\">" + this.resourceBundle.getString("PASSWORTWIEDERHOLUNG") + "</label></td>\n"
	 				+ "<td><input type=\"password\" style =\"color:red;border-color:red\" name=\"PasswortWiederholung\" type=\"text\" id=\"PasswortWiederholung\" size=\"35\" maxlength=\"60\"></td></tr>\n");
	 	}
	 	this.out.println("<tr><td valign=\"top\">&nbsp;</td><td><div align=\"left\">"
	    + "<input name=\"submit\" type=\"submit\" id=\"submit\" value=\"" + this.resourceBundle.getString("SENDEN") + "\"></div></td></tr></table></form>");
	}
}
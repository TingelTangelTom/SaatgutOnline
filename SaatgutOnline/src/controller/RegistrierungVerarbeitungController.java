package controller;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.KundeModel;
import model.PasswortHashModel;
import view.RegistrierungFehlerView;
	
/**
 * <p>Die Klasse <code>RegistrierungVerarbeitungController</code>
 * verarbeitet die Daten aus dem Registrierungsformular
 * <code>RegistrierungView</code> mit der Methode
 * <code>validiereKundendaten(request)</code>.
 * Je nach Ergebnis der Auswertung wird auf entsprechende 
 * Seiten weitergeleitet.
 * </p>
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see RegistrierungFehlerView
 */	
public class RegistrierungVerarbeitungController {
	
	private KundeModel kunde;
	private String passwort;
	
	private boolean geschlechtGeprueft;		
	private boolean vornameGeprueft;
	private boolean nachnameGeprueft;
	private boolean benutzernameGeprueft;
	private boolean firmaGeprueft;
	private boolean strasseGeprueft;
	private boolean hausnummerGeprueft;
	private boolean ortGeprueft;				
	private boolean postleitzahlGeprueft;	
	private boolean telefonGeprueft;			
	private boolean emailadresseGeprueft;	
	private boolean passwortGeprueft;
	private boolean passwortWiederholungGeprueft;
	
	/**
	 * Objekt der Klasse <code>HttpSession</code>
	 * @see HttpSession
	 */
	private HttpSession session;
	
	/**
	 * <p>Konstruktor der Klasse<code>RegistrierungVerarbeitungController</code>.
	 * Mit <code>validiereKundendaten(request)</code> werden die Formulareingaben
	 * ueberprueft.
	 * Die Pruefung prueft auf nicht null (Pflichtfelder) und weiter auf
	 * anderer Eigenschaften, je nach Formularfeld. 
	 * <code>RegistrierungView</code> mit der Methode
	 * <code>validiereKundendaten(request)</code>.
	 * Bei bestandener Pruefung wird ein Kundenobjekt in die Datenbank 
	 * gespeichert, ein Hash aus dem Passwort erstellt und dieses 
	 * ebenfalls in die Datenbank gespeichert.
	 * Weiters wird eine uuid erzeugt und per Email eine Registrierungsaufforderung an
	 * die E-Mail-Adresse des Kunden gesendet.
	 * </p>
	 * @author Christof Weigandt
	 * @version 1.0
	 * @since 1.7.0_51
	 * @see RegistrierungFehlerView
	 */	
	public RegistrierungVerarbeitungController (HttpServletRequest request, HttpServletResponse response) {
		
		kunde = new KundeModel();
		this.session = request.getSession();
		
		this.passwort = "hallo";
		System.out.println("this.passwort ist: " + this.passwort);
		System.out.println(PasswortHashController.erstellePasswortHash(this.passwort));
		
		if (validiereKundendaten(request)) {
			generiereUuid();
			kunde.speichereKundeInDb();
			System.out.println("this.passwort ist: " + this.passwort);
			System.out.println(PasswortHashController.erstellePasswortHash(this.passwort));
			PasswortController.speicherePasswortHashInDb(PasswortHashController.erstellePasswortHash(this.passwort), this.kunde.getId());
			sendeRegistrierungsEmail();
			
			try {
				response.sendRedirect("RegistrierungErfolgreich");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				// Fehlerhaft ausgefuellte Formularfelder als Objekt in session speichern
				this.session.setAttribute("RegistrierungVerarbeitungController", this);
				response.sendRedirect("RegistrierungFehler");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean validiereKundendaten(HttpServletRequest request) {
		
		if (request.getParameter("Anrede") != null) {
			this.kunde.setGeschlecht(Integer.parseInt(request.getParameter("Anrede")));
			System.out.println("kunde geschlecht ist jetzt: " + this.kunde.getGeschlecht());
			try {
				if ((Integer.parseInt(request.getParameter("Anrede")) == 0 ||
				(Integer.parseInt(request.getParameter("Anrede")) == 1))) {
					this.geschlechtGeprueft = true;
				}
			} catch(NumberFormatException e) {
		    }
		}
		if (request.getParameter("Vorname") != null) {
			this.kunde.setVorname(request.getParameter("Vorname"));
			if (request.getParameter("Vorname").length() > 2) {
				this.vornameGeprueft = true;
			} 
		}
		if (request.getParameter("Nachname") != null) {
			this.kunde.setNachname(request.getParameter("Nachname"));
			if (request.getParameter("Nachname").length() > 1) {
				this.nachnameGeprueft = true;
			}
		}
		if (request.getParameter("Strasse") != null) {
			this.kunde.setStrasse(request.getParameter("Strasse"));
			if (request.getParameter("Strasse").length() > 2) {
				this.strasseGeprueft = true;
			}
			else { System.out.println("FEHLER bei Strasse"); }
		}
		if (request.getParameter("Hausnummer") != null) {
			this.kunde.setHausnummer(request.getParameter("Hausnummer"));
			this.hausnummerGeprueft = true;
		}
		else { System.out.println("FEHLER bei Hausnummer"); }
		if (request.getParameter("Plz") != null) {
			this.kunde.setPostleitzahl(request.getParameter("Plz"));
			if (request.getParameter("Plz").length() > 4 && request.getParameter("Plz").length() < 8 ){
				this.postleitzahlGeprueft = true;
			}
		}
		if (request.getParameter("Ort") != null) {
			this.kunde.setOrt(request.getParameter("Ort"));
			if (request.getParameter("Ort").length() > 2 && request.getParameter("Ort").length() < 30 ){
				this.ortGeprueft = true;
			}
		}
		if (request.getParameter("Benutzername") != null) {
			String regelBenutzername = KonfigurationController.getRegelBenutzername();
			this.kunde.setBenutzername(request.getParameter("Benutzername"));
			if (Pattern.matches(regelBenutzername, request.getParameter("Benutzername"))) {
				this.benutzernameGeprueft = true;
			}
		}
		if (request.getParameter("Firma") != null) {
			this.kunde.setFirma(request.getParameter("Firma"));
			if (request.getParameter("Firma").length() > 2 && request.getParameter("Firma").length() < 40 ){
				this.firmaGeprueft = true;
			}
		}
		if (request.getParameter("Emailadresse") != null) {
			this.kunde.setEmailadresse(request.getParameter("Emailadresse"));
			if (request.getParameter("Emailadresse").length() > 4 && request.getParameter("Emailadresse").length() < 70 ){
				this.emailadresseGeprueft = true;
			}
		}
		if (request.getParameter("Telefon") != null) {
			this.kunde.setTelefon(request.getParameter("Telefon"));
			if (request.getParameter("Telefon").length() > 4 && request.getParameter("Telefon").length() < 30 ){
				this.telefonGeprueft = true;
			}
		}
		if (request.getParameter("Passwort") != null) {
			this.passwort = request.getParameter("Passwort");
			String regelPasswort = KonfigurationController.getRegelPasswort();
			if (Pattern.matches(regelPasswort, request.getParameter("Passwort"))) {
				this.passwortGeprueft = true;
			} else {
			}
		}
		if (request.getParameter("PasswortWiederholung") != null && request.getParameter("Passwort") != null) {
			if (request.getParameter("PasswortWiederholung").equals(request.getParameter("Passwort"))) {
				this.passwortWiederholungGeprueft = true;
			} else {
			}
		}
		
		System.out.println(geschlechtGeprueft);
		System.out.println(vornameGeprueft);
		System.out.println(nachnameGeprueft);
		System.out.println(benutzernameGeprueft);
		System.out.println(firmaGeprueft);
		System.out.println(strasseGeprueft);
		System.out.println(hausnummerGeprueft);
		System.out.println(ortGeprueft);
		System.out.println(postleitzahlGeprueft);
		System.out.println(geschlechtGeprueft);
		System.out.println(geschlechtGeprueft);
		
		if (geschlechtGeprueft
				&& vornameGeprueft
				&& nachnameGeprueft
				&& benutzernameGeprueft
				&& firmaGeprueft
				&& strasseGeprueft
				&& hausnummerGeprueft
				&& ortGeprueft
				&& postleitzahlGeprueft
				&& telefonGeprueft
				&& emailadresseGeprueft
				&& passwortGeprueft
				&& passwortWiederholungGeprueft) {
			return true;
		} else {
			return false;
		}
	}
		
	private void generiereUuid() {
		this.kunde.setUuid(UUID.randomUUID().toString());		
	}

	private void sendeRegistrierungsEmail() {
		EmailController emailController = new EmailController();
		
		String emailadresseEmpfaenger = this.kunde.getEmailadresse();
		String registrierungsnachricht = "Bitte klicken Sie auf den folgenden Link, "
				+ "um Ihre Registrierung bei saatgutonline.de abzuschliessen: <br />"
				+ "<a href=\"http://localhost:8080/SaatgutOnline/RegistrierungBestaetigung?" // URL flexibel anpassen
				+ "uuid=" + this.kunde.getUuid() + "\">Registrierung abschliessen</a>";	
				
		emailController.sendeEmail("kontakt@saatgutonline.de", "Ihre Registrierung",
				emailadresseEmpfaenger, registrierungsnachricht);	
	}

	public KundeModel getKunde() {
		return kunde;
	}

	public String getPasswort() {
		return passwort;
	}

	public boolean isGeschlechtGeprueft() {
		return geschlechtGeprueft;
	}

	public boolean isVornameGeprueft() {
		return vornameGeprueft;
	}

	public boolean isNachnameGeprueft() {
		return nachnameGeprueft;
	}

	public boolean isBenutzernameGeprueft() {
		return benutzernameGeprueft;
	}

	public boolean isFirmaGeprueft() {
		return firmaGeprueft;
	}

	public boolean isStrasseGeprueft() {
		return strasseGeprueft;
	}

	public boolean isHausnummerGeprueft() {
		return hausnummerGeprueft;
	}

	public boolean isOrtGeprueft() {
		return ortGeprueft;
	}

	public boolean isPostleitzahlGeprueft() {
		return postleitzahlGeprueft;
	}

	public boolean isTelefonGeprueft() {
		return telefonGeprueft;
	}

	public boolean isEmailadresseGeprueft() {
		return emailadresseGeprueft;
	}

	public boolean isPasswortGeprueft() {
		return passwortGeprueft;
	}

	public boolean isPasswortWiederholungGeprueft() {
		return passwortWiederholungGeprueft;
	}

	public HttpSession getSession() {
		return session;
	}
}
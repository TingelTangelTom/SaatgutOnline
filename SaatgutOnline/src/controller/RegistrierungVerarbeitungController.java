package controller;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.KundeModel;
import model.PasswortHashModel;
import view.RegistrierungVerarbeitungView;
	
public class RegistrierungVerarbeitungController {
	
	private KundeModel kunde;
	private String passwort;
	private PasswortHashModel passwortHash;
	
	private boolean geschlechtGeprueft;		// DB anpassen
	private boolean vornameGeprueft;
	private boolean nachnameGeprueft;
	private boolean benutzernameGeprueft;
	private boolean firmaGeprueft;
	private boolean strasseGeprueft;
	private boolean hausnummerGeprueft;
	private boolean ortGeprueft;				// TODO DB anpassen
	private boolean postleitzahlGeprueft;	
	private boolean landGeprueft;			
	private boolean telefonGeprueft;			
	private boolean emailadresseGeprueft;	
	private boolean bundeslandGeprueft;	
	private boolean newsletterGeprueft;		// int ggfs. in bool konvertieren
	private boolean passwortGeprueft;
	private boolean passwortWiederholungGeprueft;
	
	/**
	 * Objekt der Klasse <code>HttpSession</code>
	 * @see HttpSession
	 */
	private HttpSession session;
	
	public RegistrierungVerarbeitungController (HttpServletRequest request, HttpServletResponse response) {
		
		kunde = new KundeModel();
		this.session = request.getSession();
		
		// TODO Validierung
		
		// Daten aus Formular einlesen
//		System.out.println("geschlecht: " + request.getParameter("geschlecht"));
//		System.out.println("nachname: " + request.getParameter("nachname"));
//		System.out.println("Vorname: " + request.getParameter("Vorname"));
//		
//		this.kunde.setGeschlecht(Integer.parseInt(request.getParameter("geschlecht")));	// muss validiert sein
//		this.kunde.setVorname(request.getParameter("vorname"));
//		this.kunde.setNachname(request.getParameter("nachname"));
//		this.kunde.setStrasse(request.getParameter("strasse"));
//		this.kunde.setHausnummer(request.getParameter("hausnummer"));
//		this.kunde.setPostleitzahl(request.getParameter("plz"));
//		this.kunde.setOrt(request.getParameter("ort"));
//		this.kunde.setBenutzername(request.getParameter("benutzername"));
//		this.kunde.setNewsletter(Integer.parseInt(request.getParameter("newsletter")));		// muss validiert sein
//		this.kunde.setFirma(request.getParameter("firma"));
//		this.kunde.setEmailadresse(request.getParameter("emailadresse"));
//		this.kunde.setTelefon(request.getParameter("telefon"));
//		this.kunde.setNewsletter(Integer.parseInt(request.getParameter("newsletter")));				// muss validiert sein		
//		
//		this.passwort = request.getParameter("passwort");
		
		if (validiereKundendaten(request)) {
			generiereUuid();
			kunde.speichereKundeInDb();
			this.passwortHash.setPasswortHash(
					PasswortHashController.erstellePasswortHash(this.passwort));
			this.passwortHash.speicherePasswortHashInDb();
			sendeRegistrierungsEmail();
			
			try {
				response.sendRedirect("/RegistrierungErfolgreich");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				// hier ein forward mit request einbauen
				// Fehlerhafte Daten als Objekt in session speichern
				this.session.setAttribute("RegistrierungVerarbeitungController", this);
				response.sendRedirect("RegistrierungFehler");	// POST-Daten mitgeben!!!
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
//		RegistrierungVerarbeitungView registrierungView = new RegistrierungView(request, response);
//		registrierungVerarbeitungView.outResistrierungVerarbeitung(request, response);
	}

	private boolean validiereKundendaten(HttpServletRequest request) {
		
		if (request.getParameter("Anrede") != null) {
			this.kunde.setGeschlecht(Integer.parseInt(request.getParameter("Anrede")));
			System.out.println("kunde geschlecht ist jetzt: " + this.kunde.getGeschlecht());
			try {
				if ((Integer.parseInt(request.getParameter("Anrede")) == 0 ||
				(Integer.parseInt(request.getParameter("Anrede")) == 1))) 
				this.geschlechtGeprueft = true;
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
		}
		if (request.getParameter("Hausnummer") != null) {
			this.kunde.setHausnummer(request.getParameter("Hausnummer"));
			this.hausnummerGeprueft = true;
		}
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
		if (request.getParameter("Newsletter") != null) {
			try {
				this.kunde.setNewsletter(Integer.parseInt(request.getParameter("Newsletter")));
				if ((Integer.parseInt(request.getParameter("Newsletter")) == 0 ||
				(Integer.parseInt(request.getParameter("Newsletter")) == 1))) 
				this.newsletterGeprueft = true;
			} catch(NumberFormatException e) {
		    }		
		}
		if (request.getParameter("Passwort") != null) {
			this.passwort = request.getParameter("Passwort");
			String regelPasswort = KonfigurationController.getRegelPasswort();
			if (Pattern.matches(regelPasswort, request.getParameter("Passwort"))) {
				this.passwortGeprueft = true;
			}
		}
		if (request.getParameter("Passwortwiederholung") != null && request.getParameter("Passwort") != null) {
			if (request.getParameter("Passwortwiederholung").equals(request.getParameter("Passwort"))) {
				this.passwortWiederholungGeprueft = true;
			}
		}
		if (geschlechtGeprueft
				&& vornameGeprueft
				&& nachnameGeprueft
				&& benutzernameGeprueft
				&& firmaGeprueft
				&& strasseGeprueft
				&& hausnummerGeprueft
				&& ortGeprueft
				&& postleitzahlGeprueft
				&& landGeprueft
				&& telefonGeprueft
				&& emailadresseGeprueft
				&& bundeslandGeprueft
				&& newsletterGeprueft
				&& passwortGeprueft
				&& passwortWiederholungGeprueft) {
			return true; } else {
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

	public PasswortHashModel getPasswortHash() {
		return passwortHash;
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

	public boolean isLandGeprueft() {
		return landGeprueft;
	}

	public boolean isTelefonGeprueft() {
		return telefonGeprueft;
	}

	public boolean isEmailadresseGeprueft() {
		return emailadresseGeprueft;
	}

	public boolean isBundeslandGeprueft() {
		return bundeslandGeprueft;
	}

	public boolean isNewsletterGeprueft() {
		return newsletterGeprueft;
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

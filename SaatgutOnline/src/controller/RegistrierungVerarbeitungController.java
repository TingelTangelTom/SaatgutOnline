package controller;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	public RegistrierungVerarbeitungController (HttpServletRequest request, HttpServletResponse response) {
		
		kunde = new KundeModel();
		
		// TODO Validierung
		
		// Daten aus Formular einlesen
		System.out.println("geschlecht: " + request.getParameter("geschlecht"));
		System.out.println("nachname: " + request.getParameter("nachname"));
		System.out.println("Vorname: " + request.getParameter("Vorname"));
		
		this.kunde.setGeschlecht(Integer.parseInt(request.getParameter("geschlecht")));	// muss validiert sein
		this.kunde.setVorname(request.getParameter("vorname"));
		this.kunde.setNachname(request.getParameter("nachname"));
		this.kunde.setStrasse(request.getParameter("strasse"));
		this.kunde.setHausnummer(request.getParameter("hausnummer"));
		this.kunde.setPostleitzahl(request.getParameter("plz"));
		this.kunde.setOrt(request.getParameter("ort"));
		this.kunde.setBenutzername(request.getParameter("benutzername"));
		this.kunde.setNewsletter(Integer.parseInt(request.getParameter("newsletter")));		// muss validiert sein
		this.kunde.setFirma(request.getParameter("firma"));
		this.kunde.setEmailadresse(request.getParameter("emailadresse"));
		this.kunde.setTelefon(request.getParameter("telefon"));
		this.kunde.setNewsletter(Integer.parseInt(request.getParameter("newsletter")));				// muss validiert sein		
		
		this.passwort = request.getParameter("passwort");
		
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
				
				
				response.sendRedirect("/RegistrierungFehler");	// POST-Daten mitgeben!!!
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
//		RegistrierungVerarbeitungView registrierungView = new RegistrierungView(request, response);
//		registrierungVerarbeitungView.outResistrierungVerarbeitung(request, response);
	}

	private boolean validiereKundendaten(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		if (request.getParameter("Geschlecht") != null) {
			try {
				if ((Integer.parseInt(request.getParameter("Geschlecht")) == 0 ||
				(Integer.parseInt(request.getParameter("Geschlecht")) == 1))) 
				this.geschlechtGeprueft = true;
				this.kunde.setGeschlecht(Integer.parseInt(request.getParameter("Geschlecht")));
			} catch(NumberFormatException e) {
		    }		
		}
		if (request.getParameter("Vorname") != null) {
			if (request.getParameter("Vorname").length() > 2) {
				this.vornameGeprueft = true;
				this.kunde.setVorname(request.getParameter("Vorname"));
			}
		}
		if (request.getParameter("Nachname") != null) {
			if (request.getParameter("Nachname").length() > 1) {
				this.nachnameGeprueft = true;
				this.kunde.setNachname(request.getParameter("Nachname"));
			}
		}
		if (request.getParameter("Strasse") != null) {
			if (request.getParameter("Strasse").length() > 2) {
				this.strasseGeprueft = true;
				this.kunde.setStrasse(request.getParameter("Strasse"));
			}
		}
		if (request.getParameter("Hausnummer") != null) {
			this.hausnummerGeprueft = true;
			this.kunde.setHausnummer(request.getParameter("Hausnummer"));
		}
		if (request.getParameter("Plz") != null) {
			if (request.getParameter("Plz").length() > 4 && request.getParameter("Plz").length() < 8 ){
				this.postleitzahlGeprueft = true;
				this.kunde.setPostleitzahl(request.getParameter("Plz"));
			}
		}
		if (request.getParameter("Ort") != null) {
			if (request.getParameter("Ort").length() > 2 && request.getParameter("Ort").length() < 30 ){
				this.ortGeprueft = true;
				this.kunde.setOrt(request.getParameter("Ort"));
			}
		}
		if (request.getParameter("Benutzername") != null) {
			String regelBenutzername = KonfigurationController.getRegelBenutzername();
			if (Pattern.matches(regelBenutzername, request.getParameter("Benutzername"))) {
				this.benutzernameGeprueft = true;
				this.kunde.setOrt(request.getParameter("Benutzername"));
			}
		}
		if (request.getParameter("Firma") != null) {
			if (request.getParameter("Firma").length() > 2 && request.getParameter("Firma").length() < 40 ){
				this.firmaGeprueft = true;
				this.kunde.setFirma(request.getParameter("Firma"));
			}
		}
		if (request.getParameter("Emailadresse") != null) {
			if (request.getParameter("Emailadresse").length() > 4 && request.getParameter("Emailadresse").length() < 70 ){
				this.emailadresseGeprueft = true;
				this.kunde.setEmailadresse(request.getParameter("Emailadresse"));
			}
		}
		if (request.getParameter("Telefon") != null) {
			if (request.getParameter("Telefon").length() > 4 && request.getParameter("Telefon").length() < 30 ){
				this.telefonGeprueft = true;
				this.kunde.setTelefon(request.getParameter("Telefon"));
			}
		}
		if (request.getParameter("Newsletter") != null) {
			try {
				if ((Integer.parseInt(request.getParameter("Newsletter")) == 0 ||
				(Integer.parseInt(request.getParameter("Newsletter")) == 1))) 
				this.newsletterGeprueft = true;
				this.kunde.setNewsletter(Integer.parseInt(request.getParameter("Newsletter")));
			} catch(NumberFormatException e) {
		    }		
		}
		if (request.getParameter("Passwort") != null) {
			String regelPasswort = KonfigurationController.getRegelPasswort();
			if (Pattern.matches(regelPasswort, request.getParameter("Passwort"))) {
				this.passwortGeprueft = true;
				this.passwort = request.getParameter("Passwort");
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
}

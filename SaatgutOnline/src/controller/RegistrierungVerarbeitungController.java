package controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.KundeModel;
import model.PasswortHashModel;
import view.RegistrierungVerarbeitungView;
	
public class RegistrierungVerarbeitungController {
	
	private KundeModel kunde;
	private String passwort;
	private String passwortWiederholung;
	private PasswortHashModel passwortHash;
	
	public RegistrierungVerarbeitungController (HttpServletRequest request, HttpServletResponse response) {
		
		kunde = new KundeModel();
		
		// TODO Validierung
		
		// Daten aus Formular einlesen
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
		this.passwortWiederholung = request.getParameter("passwortwiederholung");
		
		if (validiereKundendaten()) {
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
				response.sendRedirect("/RegistrierungFehler");	// POST-Daten mitgeben!!!
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
//		RegistrierungVerarbeitungView registrierungView = new RegistrierungView(request, response);
//		registrierungVerarbeitungView.outResistrierungVerarbeitung(request, response);
	}

	private boolean validiereKundendaten() {
		// TODO Auto-generated method stub
		return false;
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

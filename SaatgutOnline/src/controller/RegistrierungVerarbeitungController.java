package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.KundeModel;
import view.RegistrierungVerarbeitungView;
	
public class RegistrierungVerarbeitungController {
	
	KundeModel kunde;
	private String passwort;
	private String passwortWiederholung;
	
	public RegistrierungVerarbeitungController (HttpServletRequest request, HttpServletResponse response) {
		
		kunde = new KundeModel();
		
		this.kunde.setNachname(request.getParameter("nachname"));
		this.kunde.setVorname(request.getParameter("vorname"));
		this.kunde.setStrasse(request.getParameter("strasse"));
		this.kunde.setHausnummer(request.getParameter("hausnummer"));
		this.kunde.setPostleitzahl(request.getParameter("plz"));
		this.kunde.setOrt(request.getParameter("ort"));
		this.kunde.setBenutzername(request.getParameter("benutzername"));
		this.kunde.setNewsletter(Integer.parseInt(request.getParameter("newsletter")));
		this.kunde.setNachname(request.getParameter("nachname"));
		this.kunde.setEmailAdresse(request.getParameter("emailadresse"));
	
		this.passwort = request.getParameter("passwort");
		this.passwortWiederholung = request.getParameter("passwortwiederholung");
	
//		RegistrierungVerarbeitungView registrierungView = new RegistrierungView(request, response);
//		registrierungVerarbeitungView.outResistrierungVerarbeitung(request, response);
	}
}

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.RegistrierungVerarbeitungView;
	
public class RegistrierungVerarbeitungController {
	
	private String nachname;
	private String vorname;
	private String strasse;
	private String hausnummer;
	private String plz;
	private String ort;
	private String email;
	private String benutzername;
	private String passwort;
	private String passwortWiederholung;
	
	public RegistrierungVerarbeitungController (HttpServletRequest request, HttpServletResponse response) {
		
	this.nachname = request.getParameter("nachname");
	this.vorname = request.getParameter("vorname");
	this.strasse = request.getParameter("strasse");
	this.hausnummer = request.getParameter("hausnummer");
	this.ort = request.getParameter("ort");
	this.email = request.getParameter("email");
	this.benutzername = request.getParameter("benutzername");
	this.passwort = request.getParameter("passwort");
	this.passwortWiederholung = request.getParameter("passwortWiederholung");
	
	
//		RegistrierungVerarbeitungView registrierungView = new RegistrierungView(request, response);
//		registrierungVerarbeitungView.outResistrierungVerarbeitung(request, response);
	}
}

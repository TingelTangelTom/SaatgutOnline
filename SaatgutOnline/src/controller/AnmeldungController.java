/**
 * 
 */
package controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import model.KundeModel;
import model.PasswortHashModel;

/**
 * @author Christof Weigandt
 *
 */

public class AnmeldungController {
	
	private String benutzername;
	private String passwort;
	private String regelBenutzername;
	private String regelPasswort;
	private Pattern pattern;
	private Matcher matcher;
	private ServletResponse response;
	private KundeModel kunde;

	public AnmeldungController(ServletRequest request,
			ServletResponse response) {
		this.response = response;
		this.benutzername = request.getParameter("benutzername");
		this.passwort = request.getParameter("passwort");
		
		
//		try {
//			((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL("NoOperationServlet"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		boolean status = pruefeAnmeldedaten();
		
		
	}

	private boolean pruefeAnmeldedaten() {
		// Teil 1: Anmeldedaten auf formelle Gueltigkeit pruefen
		
		this.regelBenutzername = KonfigurationController.getRegelBenutzername();
		this.regelPasswort = KonfigurationController.getRegelPasswort();
		
		if (!(Pattern.matches(regelBenutzername, benutzername)) || 
				!(Pattern.matches(regelPasswort, passwort)) ) {
			// es folgt die unfassbar umständliche Weiterleitung
			System.out.println("Benutzername oder Passwort formell falsch");
			try {
				((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL("AnmeldungFehler"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("user/pass formell korrekt");
			this.kunde = KundeModel.ladeKundeAusDb(this.benutzername);
			System.out.println("kunde geladen");
			System.out.println(this.kunde.getBenutzername());
			
			if (this.kunde.getBenutzername() != null) {
				System.out.println("Benutzername stimmt überein!");
				
				String passwortHash = PasswortHashModel.ladePasswortHashAusDb(this.kunde.getId());
				if (PasswortHashController.validierePasswort(this.passwort, passwortHash)) {
					System.out.println("passwort stimmt");
				} else {
					System.out.println("passwort falsch");
				}
				
				try {
					((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL("AnmeldungFehler"));
				} catch (IOException e) {
					e.printStackTrace();
				} 
			} else {
				System.out.println("Benutzername nicht in Datenbank!!");
			}
			
			
		}
		
		
		// Teil 2: Anmeldedaten mit der Datenbank abgleichen
		
		// Verbindung aufbauen
		// sql query-string erstellen: 
		
		return false;
	}

}
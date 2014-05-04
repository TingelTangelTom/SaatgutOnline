/**
 * 
 */
package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	private ServletRequest request;
	private ServletResponse response;
	private KundeModel kunde;

	public AnmeldungController(ServletRequest request,
			ServletResponse response) {
		this.request = request;
		this.response = response;
		this.benutzername = request.getParameter("benutzername");
		this.passwort = request.getParameter("passwort");
		
		switch (pruefeAnmeldedaten()) {
			case 0: try {
						((HttpServletResponse) response).sendRedirect(
								((HttpServletResponse) response).encodeRedirectURL
								("AnmeldungFehler"));
					} catch (IOException e) {
						e.printStackTrace(); }
					break;
			case 1: try {
						((HttpServletResponse) response).sendRedirect(
								((HttpServletResponse) response).encodeRedirectURL
								("AnmeldungRegistrierungFehler"));	// TODO erstellen!
					} catch (IOException e) {
						e.printStackTrace(); }
					break;
			case 2: HttpSession session = ((HttpServletRequest) request).getSession();
					System.out.println("Setzte angemeldet auf true");
					session.setAttribute( "angemeldet", true);	
					session.setAttribute("benutzername", this.kunde.getBenutzername());
					try {
						((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL("AnmeldungErfolgreich"));
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
		}
	}

	private int pruefeAnmeldedaten() {
		this.regelBenutzername = KonfigurationController.getRegelBenutzername();
		this.regelPasswort = KonfigurationController.getRegelPasswort();
		
		if (!(Pattern.matches(regelBenutzername, benutzername)) || 
				!(Pattern.matches(regelPasswort, passwort)) ) {
			System.out.println("Benutzername oder Passwort formell falsch");
			return 0; }
		// System.out.println("user/pass formell korrekt");
		this.kunde = KundeModel.ladeKundeAusDb(this.benutzername);
//			System.out.println("kunde geladen");
//			System.out.println(this.kunde.getBenutzername());
		
		if (this.kunde.getBenutzername() == null) {
			return 0; }

		String passwortHash = PasswortHashModel.ladePasswortHashAusDb(this.kunde.getId());
		if (!(PasswortHashController.validierePasswort(this.passwort, passwortHash))) {		// TODO umbenennen validiere...
			return 0; }
		if(this.kunde.getFreigeschaltet() != 1) {
			// noch nicht freigeschaltet!!!
			return 1;
		} 
		
		// alles korrekt -> einloggen!!!
		return 2; }
}
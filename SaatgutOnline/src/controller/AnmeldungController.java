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

/**
 * @author Christof Weigandt
 *
 */

public class AnmeldungController {
	
	String benutzername;
	String passwort;
	ServletContext context;
	
	private String benutzernameRegel;
	private String passwortRegel;
	private Pattern pattern;
	private Matcher matcher;
	private ServletResponse response;

	public AnmeldungController(ServletRequest request,
			ServletResponse response, ServletContext context) {
		this.response = response;
		this.benutzername = request.getParameter("benutzername");
		this.passwort = request.getParameter("passwort");
		this.context = context;
		
		boolean status = anmeldedatenPruefen();
	}

	private boolean anmeldedatenPruefen() {
		// Teil 1: Anmeldedaten auf formelle Gueltigkeit pruefen
		
		benutzernameRegel = (String)context.getAttribute("BenutzernameRegel");
		
		if (!(Pattern.matches(benutzernameRegel, benutzername)) || 
				!(Pattern.matches(passwortRegel, passwort)) ) {
			// es folgt die unfassbar umständliche Weiterleitung
			try {
				((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL("AnmeldungFehler"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Teil 2: Anmeldedaten mit der Datenbank abgleichen
		
		// Verbindung aufbauen
		// sql query-string erstellen: 
		
		return false;
	}
}
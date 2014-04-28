/**
 * 
 */
package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Christof Weigandt
 *
 */

public class AnmeldungController {
	
	String nutzername;
	String passwort;
	private Pattern pattern;
	private Matcher matcher;

	public AnmeldungController(String nutzername, String passwort) {
		this.nutzername = nutzername;
		this.passwort = passwort;
		
		boolean status = AnmeldedatenPruefen();
		
	}

	private boolean AnmeldedatenPruefen() {
		
		// Anmeldedaten auf formelle Gueltigkeit pruefen
		
		// Anmeldedaten mit der Datenbank abgleichen
		
		return false;
	}
	
}
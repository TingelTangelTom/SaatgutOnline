package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrierungView {
	
	public RegistrierungView (HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println();
	}
	
	public void outResistrierungFormular(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println("<h1>Registrierung</h1>"
				+ "<p>Bitte geben Sie Ihre Daten ein: (* Pflichtfelder)</p>"
			    + "<form action=/SaatgutOnline/RegistrierungVerarbeitung method=\"POST\">"
			    + "<table width=\"200\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">"
			    + "<tr><td>Anrede:</td><td><select name=\"Anrede\" id=\"Anrede\" title=\"Anrede\">"
			    + "<option>Frau</option><option>Herr</option></select></td></tr>"
			    
			    + "<tr><td>Nachname:</td>\n"
			    + "<td><input name=\"Nachname\" type=\"text\" id=\"nachname\" size=\"35\" maxlength=\"60\"></td></tr>"
			    
			    + "<tr><td>Vorname:</td>"
			    + "<td><input name=\"vorname\" type=\"text\" id=\"vorname\" size=\"35\" maxlength=\"60\"></td></tr>"
			    
				+ "<tr><td>Strasse:</td>"
			 	+ "<td><input name=\"strasse\" type=\"text\" id=\"strasse\" size=\"35\" maxlength=\"60\"></td>"
				 
				+ "<tr><td>Hausnummer:</td>"
				+ "<td><input name=\"hausnummer\" type=\"text\" id=\"hausnummer\" size=\"35\" maxlength=\"60\"></td></tr>"
				 
				+ "<tr><td>plz:</td>"
				+ "<td><input name=\"plz\" type=\"text\" id=\"plz\" size=\"35\" maxlength=\"60\"></td></tr>"
				 
				+ "<tr><td>Ort:</td>"
				+ "<td><input name=\"ort\" type=\"text\" id=\"ort\" size=\"35\" maxlength=\"60\"></td></tr>"
			    
				+ "<tr><td>E-Mail-Adresse:</td>"
			    + "<td><input name=\"email\" type=\"text\" id=\"email\" size=\"35\" maxlength=\"60\"></td></tr>"
			    
			  	+ "<tr><td>Ihr gew&uuml;nschter Benutzername:</td>"
			  	+ "<td><input name=\"benutzername\" type=\"text\" id=\"benutzername\" size=\"35\" maxlength=\"60\"></td></tr>"
  
			  	+ "<tr><td>Passwort</td>"
			  	+ "<td><input name=\"passwort\" type=\"text\" id=\"passwort\" size=\"35\" maxlength=\"60\"></td></tr>"
			  	
			  	+ "<tr><td>Passwort Wiederholung</td>"
			  	+ "<td><input name=\"passwortwiederholung\" type=\"text\" id=\"passwortwiederholung\" size=\"35\" maxlength=\"60\"></td></tr>"
			  	
			    + "<tr><td valign=\"top\">&nbsp;</td><td><div align=\"left\">"
			    + "<input name=\"submit\" type=\"submit\" id=\"submit\" formmethod=\"POST\" value=\"Senden\"></div></td></tr></table></form>");
	}
	
}
package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KontaktFormularVerarbeitungView {
	
	private PrintWriter out;
	
	public KontaktFormularVerarbeitungView(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/html");
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.out.println();
	}
		
	public void outKontaktVerarbeitungView() {
		//Ausgabe nach senden
		out.println("Vielen Dank für ihre Nachricht.");
		out.println("<a href=\"/SaatgutOnline/IndexPlatzhalter\">Startseite</a>"); 
	}
	
	public void outKontaktVerarbeitungViewUngueltig() {
		//Ausgabe nach senden
		out.println("Ihre E-Mail-Adresse ist ungültig.<br>Geben sie eine gültige E-Mail-Adresse ein.");
		out.println("<a href=\"/SaatgutOnline/Kontakt\">>&#11013 Zurück</a>"); 
	}
	
}

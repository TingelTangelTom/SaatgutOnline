package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImpressumView {

	public ImpressumView(HttpServletRequest request, HttpServletResponse response,
			String unternehmen_adresse, String unternehmen_telefon, 
			String unternehmen_fax, String unternehmen_email, String unternehmen_geschaeftsfuehrung, 
			String registergericht, String register_nr, String umsatzsteuer_id, 
			String wirtschafts_id, String impressum_copyright) {
			//TODO : E-Mail ändern in samen@saatgutonline.de (In DB)

		// Ausgabe und Formatierung mit Printwriter
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println("<h1>Impressum</h1>" + unternehmen_adresse + "<br><br>Telefon: " 
				+ unternehmen_telefon + "<br>Fax: " +  unternehmen_fax + "<br>E-Mail: "
				+ unternehmen_email + "<br><br>Geschäftsführer: " + unternehmen_geschaeftsfuehrung 
				+ "<br><br>Registergericht: " + registergericht + ", " + register_nr 
				+ "<br>Umsatzsteuer-IdNr.: " + umsatzsteuer_id + "<br>Wirtschafts-IdNr.: " 
				+ wirtschafts_id + "<br><br>" +  impressum_copyright);
		out.println("<br><br><a href=\"/SaatgutOnline/IndexPlatzhalter\">&#11013 Zurück</a>");
		//FIXME : Richtigen Link einsetzen
		//TODO : Ich hätte den Link gerne in schwarz :)
		//TODO : Pfeil : Auch &#8678 ausprobieren
	}
}

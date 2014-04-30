package view;

import java.util.ArrayList;
import java.util.Locale;
import java.util.PropertyResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ProduktModel;
import controller.HtmlOutput;
import controller.ProduktController;

public class ProduktlisteView {
	private ProduktController produktController;
	private ProduktModel produktModel;
	private HtmlOutput htmlOutput;
	private String output;
	private ArrayList<ProduktModel> produktliste;
	private String kategorie;
	
	public ProduktlisteView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.produktModel = new ProduktModel();
		this.htmlOutput = new HtmlOutput(request);
		this.produktliste = new ArrayList<>();
		this.kategorie = request.getParameter("kategorie");

		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute("sprache");
		PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
	}

	//texte.getString("WILLKOMMEN");
	public String anzeigenProduktliste() {	
		
		this.produktliste = this.produktController.getProduktliste(this.kategorie);
		this.output = "<table class=\"produktinfo\"><tr><td>";
		for (int i = 0; i < this.produktliste.size(); i++) {
			ProduktModel produktModel = this.produktliste.get(i);
			this.output += "<table width=\"100%\" border=\"0\">"
			+ "<tr>"
			+ "<td rowspan=\"4\"><img src=\"/resources/bilder/phoenix_canariensis.jpg\" alt=\"Phoenix Canariensis\"></td>"
	    	+ "<td colspan=\"2\">" + produktModel.getName() + "</td>"
	    	+ "<td rowspan=\"2\">" + produktModel.getPreisBrutto() + "</td>"
	    	+ "</tr>"
	    	+ "<tr>"
    		+ "<td colspan=\"2\">" + produktModel.getKategorie_id() + "</td>"
    		+ "</tr>"
    		+ "<tr>"
    		+ "<td colspan=\"3\">" + produktModel.getBeschreibung() + "</td>"
    		+ "</tr>"
    		+ "<tr>"
    		+ "<td>Keine Ahnung</td>"
    		+ "<td>Auf Lager</td>"
    		+ "<td>Warenkorb</td>"
    		+ "</tr>"
    		+ "</table>";
		}
		this.output += "</td></tr></table>";	
			/*this.output += "<table class=\"produktinfo\"><tr><td>"
				+ "<tr><td rowspan=\"5\">Cell 1</td><td colspan=\"3\">" + this.produktliste.size() + " " + produktModel.getId() + " "+ produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td colspan=\"3\">" + produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td colspan=\"3\">" + produktModel.getBeschreibung() + "</td></tr>"		
				+ "<tr><td>Menge</td><td>Auf Lager</td><td>Warenkorb</td></tr>" // Titel
				+ "<tr><td colspan=\"3\">Preise inklusive</td></tr>" // Produktbeschreibung
				+ "<tr><td colspan=\"2\">Warenkorb</td></tr>" // Button Warenkorb
				+ "<tr><td colspan=\"2\">" // Button Warenkorb"
				+ "<form action='' method='POST'>"
				+ "<input type=\"hidden\" name=\"menge\" value=\"3\">"
				+ "<input type=\"hidden\" name=\"produkt\" value=\"\">"
				+ "<input type=\"image\" name=\"absenden\" value=\"senden\" src=\"resources/bilder/flags_iso/24/us.png\">"
				+ "</td></tr></table>";	
			produktModel = null;
			*/
		return output;
	}

}
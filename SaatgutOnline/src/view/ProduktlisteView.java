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
	
	public ProduktlisteView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.produktModel = new ProduktModel();
		this.htmlOutput = new HtmlOutput(request);
		this.produktliste = new ArrayList<>();
		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute("sprache");
//		PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
	}

	//texte.getString("WILLKOMMEN");
	public String anzeigenProduktliste() {	
		this.output = "<table class=\"produktinfo\">";
		this.produktliste = this.produktController.getProduktliste(1);
		this.output = "<table class=\"produktinfo\"><tr><td>";
		for (int i = 0; i < this.produktliste.size(); i++) {
		this.produktModel = this.produktliste.get(i);	
		this.output += "<table class=\"produktinfo\"><tr><td>"
				+ "<tr><td rowspan=\"5\">Cell 1</td><td colspan=\"3\">" + this.produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td colspan=\"3\">" + this.produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td colspan=\"3\">" + this.produktModel.getBeschreibung() + "</td></tr>"		
				+ "<tr><td>Menge</td><td>Auf Lager</td><td>Warenkorb</td></tr>" // Titel
				+ "<tr><td colspan=\"3\">Preise inklusive</td></tr>" // Produktbeschreibung
				+ "<tr><td colspan=\"2\">Warenkorb</td></tr>" // Button Warenkorb
				+ "<tr><td colspan=\"2\">" // Button Warenkorb"
				+ "<form action='' method='POST'>"
				+ "<input type=\"hidden\" name=\"menge\" value=\"3\">"
				+ "<input type=\"hidden\" name=\"produkt\" value=\"\">"
				+ "<input type=\"image\" name=\"absenden\" value=\"senden\" src=\"resources/bilder/flags_iso/24/us.png\">"
				+ "</td></tr></table>";	
		}
		this.output += "</td></tr></table>";	

		return this.output;
	}

}
package view;

import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import controller.HtmlOutput;
import controller.ProduktinfoController;
import model.ProduktModel;

public class ProduktinfoView {
	private ResourceBundle resourceBundle;
	private ProduktinfoController produktController;
	private ProduktModel produktModel;
	private HtmlOutput htmlOutput;
	private String output;
	private HashMap<String, String> merkmale;
	
	public ProduktinfoView(HttpServletRequest request) {
		
		this.produktController = new ProduktinfoController(request);
		this.produktModel = new ProduktModel();
		this.htmlOutput = new HtmlOutput(request);
		
		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
	}

	//texte.getString("WILLKOMMEN");
	public String anzeigenProduktinfo() {	
		this.produktModel = this.produktController.getProdukt(1);
		Locale sprache = Locale.GERMANY;
		
		this.merkmale = this.produktModel.getMerkmale();
		
		output = "<table class=\"produktinfo\">"
				+ "<tr><td rowspan=\"3\">Cell 1</td><td>" + this.produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td>" + this.produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td><table>";
				for(String name : merkmale.keySet()) {
					output += "<tr><td>" + name + "</td><td>" + merkmale.get(name) +"</td></tr>"; // Eigenschaft 1
				}
		output += "</table></td></tr>"		
				+ "<tr><td colspan=\"2\">" + htmlOutput.outPreisformat(this.produktModel.getPreisBrutto()) + " " + htmlOutput.outPreisverordnung(this.produktModel.getSteuersatz()) + "</td></tr>" // Titel
				+ "<tr><td colspan=\"2\">" + this.produktModel.getBeschreibung() + "</td></tr>" // Produktbeschreibung
				+ "<tr><td colspan=\"2\">Warenkorb</td></tr>" // Button Warenkorb
				+ "<tr><td colspan=\"2\">" // Button Warenkorb"
				+ "<form action='' method='POST'>"
				+ "<input type=\"hidden\" name=\"menge\" value=\"3\">"
				+ "<input type=\"hidden\" name=\"produkt\" value=\"" + this.produktModel + "\">"
				+ "<input type=\"image\" name=\"absenden\" value=\"senden\" src=\"resources/bilder/flags_iso/24/us.png\">"
				+ "</td></tr></table>";	

		return output;
	}
	
	/*
	public String anzeigenListenProdukt() {	
		produktModel = this.produktController.getProdukt(1, 1);
		htmlOutput = "<table border='1' width=150  height=200 cellspacing='0' cellpadding='0'>"
						+ "<tr style=\"height:100;\">"
						+ "<td>"
						+ produktModel.getBeschreibung()
						+ "</td></tr><tr><td>"
						+ "Titel"
						+ "</td></tr><tr><td>"		
						+ "Preis mit Angaben"
						+ "</td></tr><tr><td>"
						+ "Button 1 Button 2"
						+ "</td></tr></table>";
		
		
		//htmlOutput = String.valueOf(produktModel.getKategorieId());
		return htmlOutput;
	}
	*/
}

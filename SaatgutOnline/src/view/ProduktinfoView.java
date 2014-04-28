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
		produktModel = this.produktController.getProdukt(1);
		Locale sprache = Locale.GERMANY;
		
		this.merkmale = this.produktModel.getMerkmale();
		
		output = "<table class=\"produktinfo\">"
				+ "<tr><td rowspan=\"6\">Cell 1</td><td colspan=\"2\">" + produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td colspan=\"2\">" + this.produktModel.getName() + "</td></tr>"; // Titel
				for(String name : merkmale.keySet()) {
					output += "<tr><td>" + name + "</td><td>" + merkmale.get(name) +"</td></tr>"; // Eigenschaft 1
				}
		output += "<tr><td colspan=\"2\">" + htmlOutput.outPreisformat(this.produktModel.getPreisBrutto()) + " " + htmlOutput.outPreisverordnung(this.produktModel.getSteuersatz()) + "</td></tr>" // Titel
				+ "<tr><td colspan=\"3\">" + this.produktModel.getBeschreibung() + "</td></tr>" // Produktbeschreibung
				+ "<tr><td colspan=\"3\">Warenkorb</td></tr>" // Button Warenkorb
				+ "</table>";
				
				/*
						+ "<tr>\n<td style=\"width: 340px; text-align: center;\">\n"
						+ "<img src=\"resources/bilder/phoenix_canariensis.jpg\" alt=\"Logo\">\n"
						+ "</td\n><td style=\"width: 430px;\">\n"
						+ produktModel.getName() + "<br />"
						+ "<produktinfo>" + produktModel.getPreis() + "<produktinfo><br />"
						+ produktModel.getGewicht()
						+ "</td></tr><tr><td colspan=\"2\">"
						+ produktModel.getBeschreibung()
						+ "</td></tr><tr><td colspan=\"2\">"
						+ "Button 1 Button 2"
						
						*/


		
		//produktModel = this.produktController.getProdukt(1);
		//htmlOutput = String.valueOf(produktModel.getKategorieId());
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

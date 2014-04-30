package view;

import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import controller.HtmlOutput;
import controller.ProduktController;
import model.ProduktModel;

public class ProduktinfoView {
	private ProduktController produktController;
	private ProduktModel produktModel;
	private HtmlOutput htmlOutput;
	private String output;
	private HashMap<String, String> merkmale;
	
	public ProduktinfoView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.produktModel = new ProduktModel();
		this.htmlOutput = new HtmlOutput(request);
		
		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute("sprache");
		PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
	}

	//texte.getString("WILLKOMMEN");
	public String anzeigenProduktinfo() {	
		this.produktModel = this.produktController.getProdukt(1);
		this.merkmale = this.produktModel.getMerkmale();
		
		this.output = "<table class=\"produktinfo\">"
				+ "<tr><td rowspan=\"3\">Cell 1</td><td>" + this.produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td>" + this.produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td><table class=\"produktinfo\">";
				for(String name : merkmale.keySet()) {
					output += "<tr><td>" + name + "</td><td>" + merkmale.get(name) +"</td></tr>"; // Eigenschaft 1
				}
		this.output += "</table></td></tr>"		
				+ "<tr><td colspan=\"2\">" + htmlOutput.outPreisformat(this.produktModel.getPreisBrutto()) + " " + htmlOutput.outPreisverordnung(this.produktModel.getSteuersatz()) + "</td></tr>" // Titel
				+ "<tr><td colspan=\"2\">" + this.produktModel.getBeschreibung() + "</td></tr>" // Produktbeschreibung
				+ "<tr><td colspan=\"2\">Warenkorb</td></tr>" // Button Warenkorb
				+ "<tr><td colspan=\"2\">" // Button Warenkorb"
				+ "<form action='' method='POST'>"
				+ "<input type=\"hidden\" name=\"menge\" value=\"3\">"
				+ "<input type=\"hidden\" name=\"produkt\" value=\"" + this.produktModel + "\">"
				+ "<input type=\"image\" name=\"absenden\" value=\"senden\"> absenden"
				+ "</td></tr></table>";	

		return this.output;
	}

}
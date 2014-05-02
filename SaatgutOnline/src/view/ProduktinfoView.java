package view;

import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import controller.ProduktController;
import model.ProduktModel;

public class ProduktinfoView {
	private ProduktController produktController;
	private ProduktModel produktModel;
	private HtmlAusgabe htmlOutput;
	private String output;
	private HashMap<String, String> merkmale;
	private static int warenkorbmenge;
	
	public ProduktinfoView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.produktModel = new ProduktModel();
		this.htmlOutput = new HtmlAusgabe(request);
		
		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute("sprache");
		PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
	}

	//texte.getString("WILLKOMMEN");
	public String anzeigenProduktinfo(int id) {	
		this.produktModel = this.produktController.getProdukt(id);
		this.merkmale = this.produktModel.getMerkmale();
		warenkorbmenge = 1;
		
		this.output = "<table class=\"produktinfo\">"
				+ "<tr><td rowspan=\"3\">Cell 1</td><td>" + this.produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td>" + this.produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td><table class=\"produktinfo\">";
				for(String name : merkmale.keySet()) {
					output += "<tr><td>" + name + "</td><td>" + merkmale.get(name) +"</td></tr>"; // Eigenschaft 1
				}
		this.output += "</table></td></tr>"		
				+ "<tr><td colspan=\"2\">" + htmlOutput.outPreisformat(this.produktModel.getPreisBrutto()) + " " + htmlOutput.outPreisverordnung(this.produktModel.getSteuerSatz()) + "</td></tr>" // Titel
				+ "<tr><td colspan=\"2\">" + this.produktModel.getBeschreibung() + "</td></tr>" // Produktbeschreibung
				+ "<tr><td colspan=\"2\">Warenkorb</td></tr>" // Button Warenkorb
				+ "<tr><td colspan=\"2\">" // Button Warenkorb"
				+ "<form action=\"/SaatgutOnline/Warenkorb\" method=\"POST\">"
				+ "<input type=\"hidden\" name=\"menge\" value=\"" + warenkorbmenge + "\">"
				+ "<input type=\"hidden\" name=\"produkt\" value=\"" + this.produktModel.getId() + "\">"
				+ "<input type=\"image\" name=\"absenden\" value=\"senden\">absenden</form>"
				+ "</td></tr></table>";	
System.out.println("Warenkorbmenge: " +warenkorbmenge);
		return this.output;
	}

}
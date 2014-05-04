package view;

import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import controller.ProduktController;
import model.ProduktModel;

public class ProduktinfoView {
	private ProduktController produktController;
	private ResourceBundle resourceBundle;
	private ProduktModel produktModel;
	private HtmlAusgabe htmlAusgabe;
	private String output;
	private HashMap<String, String> merkmale;
	private static int warenkorbmenge;
	
	public ProduktinfoView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.produktModel = new ProduktModel();
		this.htmlAusgabe = new HtmlAusgabe(request);
		
		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
	}

	//texte.getString("WILLKOMMEN");
	public String anzeigenProduktinfo(int id) {	
		this.produktModel = this.produktController.getProdukt(id);
		this.merkmale = this.produktModel.getMerkmale();
		warenkorbmenge = 1;
		
		this.output = "<table class=\"produktinfo\">\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo bild\"><img src=\"resources/bilder/palme.jpg\" alt=\"Phoenix Canariensis\"></td>\n"
					+ "<td>\n"
					
					+ "<table class=\"produktinfo mittlerespalte\">\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo titel\">" + this.htmlAusgabe.outLinkProduktinfo(produktModel.getName(), produktModel.getId()) + "</td>\n"
					+ "</tr>\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo\">" + this.produktModel.getVpe() + "</td>\n"
					+ "</tr>\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo bestellnummer\">" + this.resourceBundle.getString("BESTELLNUMMER") + " " + produktModel.getBestellnummer() + "</td>\n"
					+ "</tr>\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo\">\n"
					+ "<table class=\"produktmerkmale\">\n";
					for(String name : merkmale.keySet()) {
						output += "<tr><td class=\"produktmerkmale name\">" + name + "</td><td class=\"produktmerkmale wert\">" + merkmale.get(name) +"</td></tr>"; // Eigenschaft 1
					}
		this.output	+= "</td>\n"
					+ "</table>\n"
					+ "</tr>\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo\">unter Merkmale</td>\n"
					+ "</tr>\n"
					+ "</table>\n"
					+ "</td>\n"
					+ "<td class=\"produktinfo\">\n"
					
					+ "<table class=\"produktinfo rechtespalte\">\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo preis\">" + this.htmlAusgabe.outPreisformat(produktModel.getPreisBrutto()) + "</td>\n"
					+ "</tr>\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo preisverordnung\">" + this.htmlAusgabe.outPreisverordnung(this.resourceBundle.getString("VERSANDKOSTEN"), produktModel.getSteuerSatz()) + "</td>\n"
					+ "</tr>\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo warenkorb\">";
					if(produktModel.getBestand() == 0) {
						this.output += this.resourceBundle.getString("NICHTVORRAETIG");
					} else {
						this.output += "<form action=\"/SaatgutOnline/Warenkorb\" method=\"POST\">\n"
		    		+ "<input type=\"hidden\" name=\"menge\" value=\"" + warenkorbmenge + "\">\n"
					+ "<input type=\"hidden\" name=\"produkt\" value=\"" + produktModel.getId() + "\">\n"
		    		+ "<input type=\"image\" src=\"resources/bilder/icons/warenkorb.gif\" alt=\"Warenkorb\">\n"
		    		+ "</form>\n";
					}
		this.output	+= "</td>\n"
					+ "</tr>\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo preis\">bla bla</td>\n"
					+ "</tr>\n"
					+ "</table>\n"
					+ ""
					+ "</td>\n"
					+ "</tr>\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo beschreibung\" colspan=\"3\">" + this.produktModel.getBeschreibung() + "</td>\n"
					+ "</tr>\n"
					+ "<tr>\n"
					+ "<td class=\"produktinfo\" colspan=\"3\">zusätzliche Informationen</td>\n"
					+ "</tr>\n"
					+ "</table>\n";
		/*
		this.output += "<table class=\"produktinfo\">"
				+ "<tr><td rowspan=\"3\">Cell 1</td><td>" + this.produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td>" + this.produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td><table class=\"produktinfo\">";
				for(String name : merkmale.keySet()) {
					output += "<tr><td>" + name + "</td><td>" + merkmale.get(name) +"</td></tr>"; // Eigenschaft 1
				}
		this.output += "</table></td></tr>"		
				+ "<tr><td colspan=\"2\">" + htmlAusgabe.outPreisformat(this.produktModel.getPreisBrutto()) + " " + htmlAusgabe.outPreisverordnung(this.produktModel.getSteuerSatz()) + "</td></tr>" // Titel
				+ "<tr><td colspan=\"2\">" + this.produktModel.getBeschreibung() + "</td></tr>" // Produktbeschreibung
				+ "<tr><td colspan=\"2\">Warenkorb</td></tr>" // Button Warenkorb
				+ "<tr><td colspan=\"2\">" // Button Warenkorb"
				+ "<form action=\"/SaatgutOnline/Warenkorb\" method=\"POST\">"
				+ "<input type=\"hidden\" name=\"menge\" value=\"" + warenkorbmenge + "\">"
				+ "<input type=\"hidden\" name=\"produkt\" value=\"" + this.produktModel.getId() + "\">"
				+ "<input type=\"image\" name=\"absenden\" value=\"senden\">absenden</form>"
				+ "</td></tr></table>";	
System.out.println("Warenkorbmenge: " +warenkorbmenge);
*/
		return this.output;
	}

}
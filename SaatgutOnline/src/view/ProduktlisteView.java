package view;

import java.util.ArrayList;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ProduktModel;
import controller.ProduktController;

public class ProduktlisteView {
	private ProduktController produktController;
	private HtmlAusgabe htmlOutput;
	private ResourceBundle resourceBundle;
	private String output;
	private ArrayList<ProduktModel> produktliste;
	private String kategorie;
	private static int warenkorbmenge;
	
	public ProduktlisteView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.htmlOutput = new HtmlAusgabe(request);
		this.produktliste = new ArrayList<>();
		this.kategorie = request.getParameter("kategorie");

		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
		
	}
	
	
	/**
	 * Diese Methode gibt einen <code>String</code> zurück , welcher den HTML-Code in der <code>ProduktlisteServlet</code> ausgibt.
	 * 
	 * @return <code>String</code> - HTML-Code
	 * 
	 * @see servlet.ProduktlisteServlet
	 * 
	 */
	
	public String anzeigenProduktliste(HttpServletRequest request) {	
		//TODO Internationalisierung einbauen

		
		this.produktliste = this.produktController.getProduktliste(this.kategorie, request);
		warenkorbmenge = 1;
		//TODO Comparator einfügen
		this.output = "<table class=\"produktinfo\">"
				+ "<tr><td align=\"left\">Kategoriename</td></tr>"
				+ "<tr><td align=\"right\">sortieren: Name "
				+ "<a href=\"/SaatgutOnline/Produktliste?kategorie=1&p_anzeige=pn,3,0\"><img src=\"resources/bilder/icons/pfeil_hoch_runter.gif\" width=\"5\" height=\"10\" border=\"0\" alt=\"Sortierung\"></a> | "
				+ "Preis "
				+ "<a href=\"/SaatgutOnline/Produktliste?kategorie=1&p_anzeige=pp,3,0\"><img src=\"resources/bilder/icons/pfeil_hoch_runter.gif\" width=\"5\" height=\"10\" border=\"0\" alt=\"Sortierung\"></a>"
				+ "</td></tr>"
				+ "<tr><td colspan=\"2\"  style=\"background-image:url(resources/bilder/icons/trennlinie.gif);height: 2px; background-repeat:repeat-x;\">&nbsp;</td></tr><tr><td>";
		for (int i = 0; i < this.produktliste.size(); i++) {
			ProduktModel produktModel = this.produktliste.get(i);
			this.output += "<table class=\"produktliste\">"
			+ "<tr>"
			+ "<td class=\"produktliste bild\" rowspan=\"4\"><img src=\"resources/bilder/phoenix_canariensis.jpg\" width=\"100\" height=\"100\" alt=\"Phoenix Canariensis\"></td>"
	    	+ "<td class=\"produktliste titel\">" + produktModel.getName() + "</td>"
	    	+ "<td class=\"produktliste preis\">" + this.htmlOutput.outPreisformat(produktModel.getPreisBrutto()) + "</td>"
	    	+ "</tr>"
	    	+ "<tr>"
    		+ "<td class=\"produktliste bestellnummer\">" + this.resourceBundle.getString("BESTELLNUMMER") + " " + produktModel.getBestellnummer() + "</td><td class=\"produktliste preisverordnung\">" + this.htmlOutput.outPreisverordnung(produktModel.getSteuerSatz()) + "</td>"
    		+ "</tr>"
    		+ "<tr>"
    		+ "<td class=\"produktliste beschreibung\" colspan=\"2\">" + this.htmlOutput.outKurzeProduktbeschreibung(produktModel.getBeschreibung(), 300, produktModel.getId()) + "</td>"
    		+ "</tr>"
    		+ "<tr>"
    		+ "<td><a href=\"/SaatgutOnline/Produktinfo?produkt=" + produktModel.getId() + "\"><b>Details</b></a></td>"
    		+ "<td align=\"right\">";
			if(produktModel.getBestand() == 0) {
				this.output += this.resourceBundle.getString("NICHTVORRAETIG");
				System.out.println("Vorrätig: " + this.resourceBundle.getString("NICHTVORRAETIG"));
			} else {
				this.output += "<form action=\"/SaatgutOnline/Warenkorb\" method=\"POST\">"
    		+ "<input type=\"hidden\" name=\"menge\" value=\"" + warenkorbmenge + "\">"
			+ "<input type=\"hidden\" name=\"produkt\" value=\"" + produktModel.getId() + "\">"
    		+ "<input type=\"image\" src=\"resources/bilder/icons/warenkorb.gif\" alt=\"Warenkorb\">"
    		+ "</form>";
			}
			this.output += "</td></tr>"    		
    		+ "<tr><td colspan=\"3\">&nbsp;</td>"
    		+ "</tr>"
    		+ "</table>"
			+ "</td></tr><tr><td colspan=\"2\"  style=\"background-image:url(resources/bilder/icons/trennlinie.gif);height: 1px; background-repeat:repeat-x;\">&nbsp;</td></tr><tr><td>";
		}
		this.output += "</td></tr></table>";	

		return output;
	}

}
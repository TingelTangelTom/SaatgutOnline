package view;

import java.util.ArrayList;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ProduktModel;
import controller.HtmlOutput;
import controller.ProduktController;

public class ProduktlisteView {
	private ProduktController produktController;
	private HtmlOutput htmlOutput;
	private ResourceBundle resourceBundle;
	private String output;
	private ArrayList<ProduktModel> produktliste;
	private String kategorie;
	
	public ProduktlisteView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.htmlOutput = new HtmlOutput(request);
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
		
		//TODO Comparator einfügen
		this.output = "<table class=\"produktinfo\">"
				+ "<tr><td align=\"right\">sortieren: Name "
				+ "<a href=\"/SaatgutOnline/Produktliste?kategorie=1&p_anzeige=pn,3,0\"><img src=\"resources/bilder/icons/pfeil_hoch_runter.gif\" width=\"5\" height=\"10\" border=\"0\" alt=\"Sortierung\"></a> | "
				+ "Preis "
				+ "<a href=\"/SaatgutOnline/Produktliste?kategorie=1&p_anzeige=pp,3,0\"><img src=\"resources/bilder/icons/pfeil_hoch_runter.gif\" width=\"5\" height=\"10\" border=\"0\" alt=\"Sortierung\"></a> | "
				+ "</td></tr><tr><td>"
				+ "<tr><td align=\"right\"></td></tr>&nbsp;<tr><td>";
		for (int i = 0; i < this.produktliste.size(); i++) {
			ProduktModel produktModel = this.produktliste.get(i);
			this.output += "<table width=\"100%\" border=\"0\">"
			+ "<tr>"
			+ "<td rowspan=\"4\" style=\"width: 110px;\"><img src=\"resources/bilder/phoenix_canariensis.jpg\" width=\"100\" height=\"100\" alt=\"Phoenix Canariensis\"></td>"
	    	+ "<td colspan=\"2\">" + produktModel.getName() + "</td>"
	    	+ "<td align=\"right\" rowspan=\"2\">" + this.htmlOutput.outPreisformat(produktModel.getPreisBrutto()) + "</td>"
	    	+ "</tr>"
	    	+ "<tr>"
    		+ "<td colspan=\"2\">" + this.resourceBundle.getString("BESTELLNUMMER") + " " + produktModel.getBestellnummer() + "</td>"
    		+ "</tr>"
    		+ "<tr>"
    		+ "<td colspan=\"3\">" + this.htmlOutput.outKurzeProduktbeschreibung(produktModel.getBeschreibung(), 300, produktModel.getId()) + "</td>"
    		+ "</tr>"
    		+ "<tr>"
    		+ "<td>Keine Ahnung</td>"
    		+ "<td>Auf Lager</td>"
    		+ "<td align=\"right\">"
    		+ "<form action=\"\" method=\"POST\">"
    		+ "<input type=\"image\" src=\"resources/bilder/icons/warenkorb.gif\" alt=\"Warenkorb\">"
    		+ "<input type=\"hidden\" name=\"sprache\" value=\"en\""
    		+ "</form>"
    		+ "</td></tr>"    		
    		+ "<tr><td colspan=\"4\">&nbsp;</td>"
    		+ "</tr>"
    		+ "</table>";
		}
		this.output += "</td></tr></table>";	

		return output;
	}

}
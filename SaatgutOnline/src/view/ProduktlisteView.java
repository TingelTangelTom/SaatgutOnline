package view;

import java.util.ArrayList;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.ProduktModel;
import controller.ProduktController;

public class ProduktlisteView {
	private ProduktController produktController;
	private String output;
	private ArrayList<ProduktModel> produktliste;
	private String kategorie;
	
	public ProduktlisteView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.produktliste = new ArrayList<>();
		this.kategorie = request.getParameter("kategorie");

		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute("sprache");
		PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
	}
	//TODO Internationalisierung einbauen
	//texte.getString("WILLKOMMEN");
	
	/**
	 * Diese Methode gibt einen <code>String</code> zurück , welcher den HTML-Code in der <code>ProduktlisteServlet</code> ausgibt.
	 * 
	 * @return <code>String</code> - HTML-Code
	 * 
	 * @see servlet.ProduktlisteServlet
	 * 
	 */
	
	public String anzeigenProduktliste(HttpServletRequest request) {	
		
		this.produktliste = this.produktController.getProduktliste(this.kategorie, request);
		//TODO Comparator einfügen
		this.output = "<table class=\"produktinfo\">"
				+ "<tr><td align=\"right\">sortieren: Name "
				+ "<a href=\"/SaatgutOnline/Produktliste?kategorie=1&p_anzeige=pn,3,0\"><img src=\"resources/bilder/icons/pfeil_hoch_runter.gif\" width=\"5\" height=\"10\" border=\"0\" alt=\"Home\"></a> | "
				+ "Preis "
				+ "<a href=\"/SaatgutOnline/Produktliste?kategorie=1&p_anzeige=pp,3,0\"><img src=\"resources/bilder/icons/pfeil_hoch_runter.gif\" width=\"5\" height=\"10\" border=\"0\" alt=\"Home\"></a> | "
				+ "</td></tr><tr><td>"
				+ "<tr><td align=\"right\"></td></tr>&nbsp;<tr><td>";
		for (int i = 0; i < this.produktliste.size(); i++) {
			ProduktModel produktModel = this.produktliste.get(i);
			this.output += "<table width=\"100%\" border=\"0\">"
			+ "<tr>"
			+ "<td rowspan=\"4\" style=\"width: 110px;\"><img src=\"resources/bilder/phoenix_canariensis.jpg\" width=\"100\" height=\"100\" alt=\"Phoenix Canariensis\"></td>"
	    	+ "<td colspan=\"2\">" + produktModel.getName() + "</td>"
	    	+ "<td align=\"right\" rowspan=\"2\">" + produktModel.getPreisBrutto() + "</td>"
	    	+ "</tr>"
	    	+ "<tr>"
    		+ "<td colspan=\"2\">" + produktModel.getKategorieId() + "</td>"
    		+ "</tr>"
    		+ "<tr>"
    		+ "<td colspan=\"3\">" + produktModel.getBeschreibung() + "</td>"
    		+ "</tr>"
    		+ "<tr>"
    		+ "<td>Keine Ahnung</td>"
    		+ "<td>Auf Lager</td>"
    		+ "<td align=\"right\">Warenkorb</td>"
    		+ "</tr>"    		
    		+ "<tr><td colspan=\"4\">&nbsp;</td>"
    		+ "</tr>"
    		+ "</table>";
		}
		this.output += "</td></tr></table>";	

		return output;
	}

}
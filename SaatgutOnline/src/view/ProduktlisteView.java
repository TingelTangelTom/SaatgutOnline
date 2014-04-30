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

	//texte.getString("WILLKOMMEN");
	public String anzeigenProduktliste() {	
		
		this.produktliste = this.produktController.getProduktliste(this.kategorie);
/*
	      Collections.sort(this.produktliste); // autoListe aufsteigend sortieren
	      for (ProduktModel aktuellesAuto : this.produktliste) {
	        System.out.println(aktuellesAuto.getName()); // Farbe: gruen, Leistung: 50
	        System.out.println("-----");
	                                  // Farbe: rot, Leistung: 55
	                                  // Farbe: schwarz, Leistung: 75
	      }
	      Collections.reverse(this.produktliste); // andersrum sortieren (75,55,50)
	      for (ProduktModel aktuellesAuto : this.produktliste) {
	        System.out.println(aktuellesAuto.getName());
	        System.out.println("------");
	      } 
*/
		this.output = "<table class=\"produktinfo\">"
				+ "<tr><td align=\"right\">sortieren: Name "
				+ "<a href=\"/SaatgutOnline/Produktliste?kategorie=1&sort=1&typ=name\">absteigend</a> - aufsteigend | Preis absteigend - aufsteigend </td></tr><tr><td>"
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
    		+ "<td colspan=\"2\">" + produktModel.getKategorie_id() + "</td>"
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
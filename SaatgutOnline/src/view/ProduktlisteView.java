package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ProduktModel;
import controller.ProduktController;
import controller.SucheController;

public class ProduktlisteView {
	private ProduktController produktController;
	private SucheController sucheController;
	private HtmlAusgabe htmlAusgabe;
	private ResourceBundle resourceBundle;
	private String output;
	private ArrayList<ProduktModel> produktliste;
	private String kategorie;
	private static int warenkorbmenge;
	
	public ProduktlisteView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.sucheController = new SucheController(request);
		this.htmlAusgabe = new HtmlAusgabe(request);
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
		HttpSession session = ((HttpServletRequest) request).getSession();
		String suchparameter = request.getParameter("suchbegriff");
		this.output = "";
		
		// Standardmenge, die im Textfeld Menge eingetragen wird
		warenkorbmenge = 1;
		
		// Festlegung, ob die Suche gestart werden soll
		
		String suchen = "false";
		
		if(request.getParameter("suchen") != null) {
			suchen = request.getParameter("suchen");
		}		
		
		// Festlegung, ob die erweiterte Suche angezeigt werden soll
		
		String erweitertesuche = "false";
		
		if(request.getParameter("erweitertesuche") != null) {
			erweitertesuche = request.getParameter("erweitertesuche");
		}

		// Festlegung, was in der Statusanzeige eingeblendet werden soll
		
		String statusanzeige;
		
		if(suchen.equals("true")) {
			this.produktliste = this.sucheController.getProduktliste(request, suchparameter);
			statusanzeige = this.produktliste.size() + " gefundene Produkte";
		} else {
			if(this.kategorie == null) {
				this.kategorie = "1";
			}
			
			this.produktliste = this.produktController.getProduktliste(request, this.kategorie, false);
			statusanzeige = this.htmlAusgabe.outKategoriename(this.kategorie);
		
		}
		
		//TODO syso entfernen
		String p_anzeige = session.getAttribute("sortierung_sortierspalte_kuerzel") + "," + session.getAttribute("sortierung_produktanzahl") + "," + session.getAttribute("sortierung_limit_von");
		System.out.println("XxXxXXxxX--------------->" + p_anzeige);
		
		// Laden der Kategorien für das Select-Feld
		HashMap<Integer, String> kategorien = new HashMap<Integer, String>(this.produktController.getAlleKategorien());
		
		//TODO syso entfernen
		System.out.println("-----> Kategorie: " + this.kategorie);
		
		// Ausgabe der Produktliste
		this.output += "<table class=\"produktliste\">\n"
						+ "<tr><td colspan=\"2\">";
				
		// Erweiterte Suche anzeigen
				if(erweitertesuche.equals("true")) {
				this.output += "<form action=\"/SaatgutOnline/Produktliste\" method=\"GET\">\n"
								+"<table class=\"produktliste\" width=\"100%\" border=\"0\">\n"
								+"<tr>\n"
								+"<td colspan\"8\">" + this.resourceBundle.getString("ERWEITERTESUCHE") + "</td>\n"
								+"</tr>\n"
					    		+ "<tr>\n<td colspan=\"8\" style=\"background-image:url(resources/bilder/icons/trennlinie.gif);height: 1px; background-repeat:repeat-x;\">&nbsp;</td>\n</tr>\n"
								+"<tr>\n"
								+"<td>" + this.resourceBundle.getString("KATEGORIE") + "</td>\n"
								+"<td><select class=\"festeSelectBoxBreite\" name=\"kategorie\" id=1>\n"
								+ "<option value=\"0\">Alle Kategorien</option>\n";
				
							    for(Integer key : kategorien.keySet()) {
							    	this.output += "<option value=\"" + key + "\">" + kategorien.get(key) + "</option>\n";
							    }

				this.output += "</select></td>\n"
								+"<td>&nbsp;</td>\n"
								+"<td>" + this.resourceBundle.getString("BESCHREIBUNG") + "</td>\n"
								+"<td><input class=\"festeSelectBoxBreite\" type=\"number\" name=\"beschreibung\" id=3></td>\n"
								+"<td>&nbsp;</td>\n"
								+"<td>" + this.resourceBundle.getString("PREIS_VON") + "</td>\n"
								+"<td><input class=\"festeSelectBoxBreite\" type=\"number\" name=\"preis_von\" id=5></td>\n"
								+"</tr>\n"
								+"<tr>\n"
								+"<td>" + this.resourceBundle.getString("NAME") + "</td>\n"
								+"<td><input class=\"festeSelectBoxBreite\" type=\"text\" name=\"name\" id=2></td>\n"
								+"<td>&nbsp;</td>\n"
								+"<td>" + this.resourceBundle.getString("BESTELLNUMMER") + "</td>\n"
								+"<td><input class=\"festeSelectBoxBreite\" type=\"text\" name=\"artikelnummer\" id=4></td>\n"
								+"<td>&nbsp;</td>\n"
								+"<td>" + this.resourceBundle.getString("PREIS_BIS") + "</td>\n"
								+"<td><input class=\"festeSelectBoxBreite\" type=\"number\" name=\"preis_bis\" id=6>"
								+ "<input type=\"hidden\" name=\"p_anzeige\" id=\"p_anzeige\" value=\"" + p_anzeige +"\">"
								+ "<input type=\"hidden\" name=\"erweitertesuche\" id=\"erweitertesuche\" value=\"true\">"
								+ "<input type=\"hidden\" name=\"suchen\" id=\"suchen\" value=\"true\">"
								+ "</td>\n"
								+"</tr>\n"
								+"<tr>\n"
								+"<td colspan=\"8\">&nbsp;<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Senden\"></td>\n"
								+"</tr>\n"
								+ "<tr><td>&nbsp;</td></tr>\n"
					    		+ "<tr>\n<td colspan=\"8\" style=\"background-image:url(resources/bilder/icons/trennlinie.gif);height: 1px; background-repeat:repeat-x;\">&nbsp;</td>\n</tr>\n"
								+ "<tr><td>&nbsp;</td></tr>\n"
					    		+"</table>\n"
								+"</form>\n";
				}		
				
				
				this.output += "</td></tr>"
								+ "<tr>\n<td class=\"produktliste kategoriename\">" + statusanzeige + "</td>\n"
								+ "<td class=\"produktliste sortierung\">";
		
		if(erweitertesuche.equals("true")) {
			this.output += "<a href=\"/SaatgutOnline/Produktliste?erweitertesuche=false&suchen=false&kategorie=1&p_anzeige=pn,3,0\">" + this.resourceBundle.getString("ERWEITERTESUCHE_WAHL_AUS") + "</a>  ";
		} else {
			this.output += "<a href=\"/SaatgutOnline/Produktliste?erweitertesuche=true&suchen=false&kategorie=1&p_anzeige=pn,3,0\">" + this.resourceBundle.getString("ERWEITERTESUCHE_WAHL_AN") + "</a>  ";
		}
		
		this.output += "" + this.resourceBundle.getString("SORTIEREN") + ": " + this.resourceBundle.getString("NAME") + " "
				+ "<a href=\"/SaatgutOnline/Produktliste?kategorie=1&p_anzeige=pn,3,0\"><img src=\"resources/bilder/icons/pfeil_hoch_runter.gif\" width=\"5\" height=\"10\" border=\"0\" alt=\"Sortierung\"></a> | "
				+ " " + this.resourceBundle.getString("PREIS") + " "
				+ "<a href=\"/SaatgutOnline/Produktliste?kategorie=1&p_anzeige=pp,3,0\"><img src=\"resources/bilder/icons/pfeil_hoch_runter.gif\" width=\"5\" height=\"10\" border=\"0\" alt=\"Sortierung\"></a>"
				+ "</td>\n</tr>\n"
				+ "<tr>\n<td colspan=\"2\" style=\"background-image:url(resources/bilder/icons/trennlinie.gif);height: 2px; background-repeat:repeat-x;\">&nbsp;</td>\n</tr>\n";

		for (int i = 0; i < this.produktliste.size(); i++) {
			ProduktModel produktModel = this.produktliste.get(i);
			this.output += "<tr>\n<td class=\"produktliste listenprodukt\" colspan=\"2\"><table class=\"produktliste\">\n"
			+ "<tr>\n"
			+ "<td class=\"produktliste bild\" rowspan=\"4\"><img src=\"resources/bilder/phoenix_canariensis.jpg\" width=\"100\" height=\"100\" alt=\"Phoenix Canariensis\"></td>\n"
	    	+ "<td class=\"produktliste titel\">" + htmlAusgabe.outLinkProduktinfo(produktModel.getName(), produktModel.getId()) + "</td>\n"
	    	+ "<td class=\"produktliste preis\">" + this.htmlAusgabe.outPreisformat(produktModel.getPreisBrutto()) + "</td>\n"
	    	+ "</tr>\n"
	    	+ "<tr>\n"
    		+ "<td class=\"produktliste bestellnummer\">" + this.resourceBundle.getString("BESTELLNUMMER") + " " + produktModel.getBestellnummer() + "</td><td class=\"produktliste preisverordnung\">" + this.htmlAusgabe.outPreisverordnung(this.resourceBundle.getString("VERSANDKOSTEN"), produktModel.getSteuerSatz()) + "</td>\n"
    		+ "</tr>\n"
    		+ "<tr>\n"
    		+ "<td class=\"produktliste beschreibung\" colspan=\"2\">" + this.htmlAusgabe.outKurzeProduktbeschreibung(this.resourceBundle.getString("MEHRANZEIGEN"), produktModel.getBeschreibung(), 300, produktModel.getId()) +  "</td>\n"
    		+ "</tr>\n"
    		+ "<tr>\n"
    		+ "<td>" + htmlAusgabe.outLinkProduktinfo(this.resourceBundle.getString("DETAILS"), produktModel.getId()) + "</td>\n"
    		+ "<td align=\"right\">";
			if(produktModel.getBestand() == 0) {
				this.output += this.resourceBundle.getString("NICHTVORRAETIG");
			} else {
				this.output += "<form action=\"/SaatgutOnline/Warenkorb\" method=\"POST\">\n"
    		+ "<input type=\"hidden\" name=\"menge\" value=\"" + warenkorbmenge + "\">\n"
			+ "<input type=\"hidden\" name=\"produkt\" value=\"" + produktModel.getId() + "\">\n"
    		+ "<input type=\"image\" src=\"resources/bilder/icons/warenkorb.gif\" alt=\"Warenkorb\">\n"
    		+ "</form>\n";
			}
			this.output += "</td>\n</tr>\n"    		
    		+ "<tr>\n<td colspan=\"3\">&nbsp;</td>\n"
    		+ "</tr>\n<tr>\n<td colspan=\"3\" style=\"background-image:url(resources/bilder/icons/trennlinie.gif);height: 1px; background-repeat:repeat-x;\">&nbsp;</td>\n</tr>\n"
    		+ "</table></td></tr>\n"
			+ "";
		}
		this.output += "</td>\n</tr>\n</table>\n";	

		return output;
	}

}
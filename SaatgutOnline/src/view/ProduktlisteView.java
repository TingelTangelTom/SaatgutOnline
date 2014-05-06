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

import sun.security.util.Length;
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
	private HttpSession session;

	
	public ProduktlisteView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.sucheController = new SucheController(request);
		this.htmlAusgabe = new HtmlAusgabe(request);
		this.produktliste = new ArrayList<>();
		this.kategorie = request.getParameter("kategorie");
		this.session = request.getSession();
		
		Locale locale = (Locale)session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
		
	}

	public String anzeigenProduktliste(HttpServletRequest request) {

		//TODO Internationalisierung einbauen
		HttpSession session = ((HttpServletRequest) request).getSession();
		String suchparameter = request.getParameter("suchbegriff");
		this.produktController.setSortierung(request);
		this.output = "";
		
		// Standardmenge, die im Textfeld Menge eingetragen wird
		warenkorbmenge = 1;
		
		// Festlegung, ob die Suche gestart werden soll
		String suchen = "false";
		
		if(request.getParameter("suchen") != null) {
			suchen = request.getParameter("suchen");
		}
		
		String suchbegriff = request.getParameter("suchen");		
		
		String angebote = "false";
		
		if(request.getParameter("angebote") != null) {
			angebote = request.getParameter("angebote");
		}	
		
		// Festlegung, ob die erweiterte Suche angezeigt werden soll
		String erweitertesuche = "false";
		
		if(request.getParameter("erweitertesuche") != null) {
			erweitertesuche = request.getParameter("erweitertesuche");
		}

		// Festlegung, was in der Statusanzeige eingeblendet werden soll
		String statusanzeige;
		
		if(suchen.equals("true")) {
			System.out.println("Produkte aus der Suche");
			this.produktliste = this.sucheController.getProduktliste(request, suchparameter);
			statusanzeige = this.produktliste.size() + " gefundene Produkte";
		} else if (angebote.equals("true")) {
			System.out.println("Angebote wurden aufgerufen");
			this.produktliste = this.produktController.getProduktliste(request, this.kategorie, false);
			statusanzeige = resourceBundle.getString("ANGEBOT");
		} else {
			if(this.kategorie == null) {
				this.kategorie = "1";
			}
			this.produktliste = this.produktController.getProduktliste(request, this.kategorie, false);
			statusanzeige = this.htmlAusgabe.outKategoriename(this.kategorie);
		}
		
		
		
		// Laden der Kategorien für das Select-Feld
		HashMap<Integer, String> kategorien = new HashMap<Integer, String>(this.produktController.getAlleKategorien());
		
		// Ausgabe der Produktliste
		this.output += "<table class=\"produktliste\">\n"
					+ "<tr><td class=\"produktliste\" colspan=\"2\">";
				
		// Erweiterte Suche anzeigen
		if(erweitertesuche.equals("true")) {
		this.output += "<form action=\"/SaatgutOnline/Produktliste\" method=\"GET\">\n"
					+"<table class=\"produktliste\">\n"
					+"<tr>\n"
					+"<td class=\"produktliste suche\" colspan\"8\">" + this.resourceBundle.getString("ERWEITERTESUCHE") + "</td>\n"
					+"</tr>\n"
		    		+ "<tr>\n<td colspan=\"8\" style=\"background-image:url(resources/bilder/icons/trennlinie.gif);height: 1px; background-repeat:repeat-x;\">&nbsp;</td>\n</tr>\n"
					+"<tr>\n"
					+"<td class=\"produktliste suchebreite\">" + this.resourceBundle.getString("KATEGORIE") + "</td>\n"
					+"<td class=\"produktliste suchebreite\"><select class=\"festeSelectBoxBreite\" name=\"kategorie\" id=1>\n"
					+ "<option value=\"0\">Alle Kategorien</option>\n";
	
				    for(Integer key : kategorien.keySet()) {
				    	this.output += "<option value=\"" + key + "\">" + kategorien.get(key) + "</option>\n";
				    }

		this.output += "</select></td>\n"
					+"<td class=\"produktliste sucheleerspalte\">&nbsp;</td>\n"
					+"<td class=\"produktliste suchebreite\">" + this.resourceBundle.getString("BESCHREIBUNG") + "</td>\n"
					+"<td class=\"produktliste suchebreite\"><input class=\"festeSelectBoxBreite\" type=\"number\" name=\"beschreibung\" id=3></td>\n"
					+"<td class=\"produktliste sucheleerspalte\">&nbsp;</td>\n"
					+"<td class=\"produktliste suchebreite\">" + this.resourceBundle.getString("PREIS_VON") + "</td>\n"
					+"<td class=\"produktliste suchebreite\"><input class=\"festeSelectBoxBreite\" type=\"number\" name=\"preis_von\" id=5></td>\n"
					+"</tr>\n"
					+"<tr>\n"
					+"<td class=\"produktliste suchebreite\">" + this.resourceBundle.getString("NAME") + "</td>\n"
					+"<td class=\"produktliste suchebreite\"><input class=\"festeSelectBoxBreite\" type=\"text\" name=\"name\" id=2></td>\n"
					+"<td class=\"produktliste sucheleerspalte\">&nbsp;</td>\n"
					+"<td class=\"produktliste suchebreite\">" + this.resourceBundle.getString("PRODUKTNUMMER") + "</td>\n"
					+"<td class=\"produktliste suchebreite\"><input class=\"festeSelectBoxBreite\" type=\"text\" name=\"produktnummer\" id=4></td>\n"
					+"<td class=\"produktliste sucheleerspalte\">&nbsp;</td>\n"
					+"<td class=\"produktliste suchebreite\">" + this.resourceBundle.getString("PREIS_BIS") + "</td>\n"
					+"<td class=\"produktliste suchebreite\"><input class=\"festeSelectBoxBreite\" type=\"number\" name=\"preis_bis\" id=6>"
					+ "<input type=\"hidden\" name=\"erweitertesuche\" id=\"erweitertesuche\" value=\"true\">"
					+ "<input type=\"hidden\" name=\"suchen\" id=\"suchen\" value=\"true\">"
					+ "</td>\n"
					+"</tr>\n"
					+"<tr>\n"
					+"<td class=\"produktliste buttons_rechts\" colspan=\"8\">&nbsp;<input type=\"image\" src=\"resources/bilder/icons/suche_button.jpg\" alt=\"Warenkorb\"></td>\n"
					+"</tr>\n"
					+ "<tr><td>&nbsp;</td></tr>\n"
		    		+ "<tr>\n<td colspan=\"8\" style=\"background-image:url(resources/bilder/icons/trennlinie.gif);height: 1px; background-repeat:repeat-x;\">&nbsp;</td>\n</tr>\n"
		    		+"</table>\n"
					+"</form>\n";
		}		
						
		this.output += "</td></tr>"
					+ "<tr>\n<td class=\"produktliste kategoriename\">" + statusanzeige + "</td>\n"
					+ "<td class=\"produktliste sortierung\">";
		
		if(erweitertesuche.equals("true")) {
			if(suchen.equals("true")) {
				this.output += "<a href=\"/SaatgutOnline/Produktliste?" + this.htmlAusgabe.outParameterLink(request, false, true, suchbegriff) + "\">" + this.resourceBundle.getString("ERWEITERTESUCHE_WAHL_AUS") + "</a>  ";
			} else {
				this.output += "<a href=\"/SaatgutOnline/Produktliste?" + this.htmlAusgabe.outParameterLink(request, false, false, suchbegriff) + "\">" + this.resourceBundle.getString("ERWEITERTESUCHE_WAHL_AUS") + "</a>  ";				
			}
		} else {
			if(suchen.equals("true")) {
				this.output += "<a href=\"/SaatgutOnline/Produktliste?" + this.htmlAusgabe.outParameterLink(request, true, true, suchbegriff) + "\">" + this.resourceBundle.getString("ERWEITERTESUCHE_WAHL_AN") + "</a>  ";
			} else {
				this.output += "<a href=\"/SaatgutOnline/Produktliste?" + this.htmlAusgabe.outParameterLink(request, true, false, suchbegriff) + "\">" + this.resourceBundle.getString("ERWEITERTESUCHE_WAHL_AN") + "</a>  ";
			}	
		}
		
		this.output += "" + this.resourceBundle.getString("SORTIEREN") + ": " + this.resourceBundle.getString("NAME") + " "
				+ "<a href=\"/SaatgutOnline/Produktliste?" + this.htmlAusgabe.outParameterLink(request, "pn") + "\"><img src=\"resources/bilder/icons/pfeil_hoch_runter.gif\" width=\"5\" height=\"10\" border=\"0\" alt=\"Sortierung\"></a> | "
				+ " " + this.resourceBundle.getString("PREIS") + " "
				+ "<a href=\"/SaatgutOnline/Produktliste?" + this.htmlAusgabe.outParameterLink(request, "pp") + "\"><img src=\"resources/bilder/icons/pfeil_hoch_runter.gif\" width=\"5\" height=\"10\" border=\"0\" alt=\"Sortierung\"></a>"
				+ "</td>\n</tr>\n"
				+ "<tr>\n<td colspan=\"2\" style=\"background-image:url(resources/bilder/icons/trennlinie.gif);height: 2px; background-repeat:repeat-x;\">&nbsp;</td>\n</tr>\n";

		for (int i = 0; i < this.produktliste.size(); i++) {
			ProduktModel produktModel = this.produktliste.get(i);
			String bruttopreis;
			String angebot = "";
			if(produktModel.getPreisBrutto() > produktModel.getPreisAngebotBrutto() && produktModel.getPreisAngebotBrutto() > 0) {
				bruttopreis =  this.htmlAusgabe.outPreisformat(produktModel.getPreisAngebotBrutto()) + " <span style=\"color: red;\"><s>" + this.htmlAusgabe.outPreisformat(produktModel.getPreisBrutto()) + "</s></span>";
			} else {
				bruttopreis = this.htmlAusgabe.outPreisformat(produktModel.getPreisBrutto());
			}
			this.output += "<tr>\n<td class=\"produktliste listenprodukt\" colspan=\"2\"><table class=\"produktliste\">\n"
			+ "<tr>\n"
			+ "<td class=\"produktliste bild\" rowspan=\"4\"><img src=\"resources/bilder/phoenix_canariensis.jpg\" width=\"100\" height=\"100\" alt=\"Phoenix Canariensis\"></td>\n"
	    	+ "<td class=\"produktliste titel\">";
			if(produktModel.getPreisBrutto() > produktModel.getPreisAngebotBrutto() && produktModel.getPreisAngebotBrutto() > 0) {
				angebot = "<span style=\"color: red;\"> Angebot</span>";
			}
			this.output += htmlAusgabe.outLinkProduktinfo(produktModel.getName(), produktModel.getId()) + " " + angebot + "</td>\n"
	    	+ "<td class=\"produktliste preis\">" + bruttopreis + this.htmlAusgabe.outPreisformatEnglischerZusatz(produktModel.getPreisBrutto()) + "</td>\n"
	    	+ "</tr>\n"
	    	+ "<tr>\n"
    		+ "<td class=\"produktliste produktnummer\">" + this.resourceBundle.getString("PRODUKTNUMMER") + " " + produktModel.getProduktnummer() + "</td><td class=\"produktliste preisverordnung\">" + this.htmlAusgabe.outPreisverordnung(this.resourceBundle.getString("VERSANDKOSTEN"), produktModel.getSteuerSatz()) + "</td>\n"
    		+ "</tr>\n"
    		+ "<tr>\n"
    		+ "<td class=\"produktliste beschreibung\" colspan=\"2\">" + this.htmlAusgabe.outKurzeProduktbeschreibung(this.resourceBundle.getString("MEHRANZEIGEN"), produktModel.getBeschreibung(), 300, produktModel.getId()) +  "</td>\n"
    		+ "</tr>\n"
    		+ "<tr>\n"
    		+ "<td>" + htmlAusgabe.outLinkProduktinfo(this.resourceBundle.getString("DETAILS"), produktModel.getId()) + "</td>\n"
    		+ "<td class=\"produktliste buttons_rechts\">";
			if(produktModel.getBestand() == 0) {
				this.output += this.resourceBundle.getString("NICHTVORRAETIG");
			} else {
				this.output += "<form action=\"/SaatgutOnline/Warenkorb\" method=\"POST\">\n"
    		+ "" +this.resourceBundle.getString("MENGE") + " <input class=\"festeTextBoxBreiteMenge\" type=\"text\" name=\"menge\" value=\"" + warenkorbmenge + "\">\n"
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
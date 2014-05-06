package view;

import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import controller.ProduktController;
import model.ProduktModel;

/**
 * <p>Die Klasse <code>ProduktinfoView</code> ist für die Zusammenstellung des 
 * <i>HTML</i>-Codes für die Einzelansicht des Produkts zust&auml;ndig.</p>
 * 
 * @author Simon Ankele
 * @version 1.0
 * @since 1.7.0_51
 * 
 */
public class ProduktinfoView {
	private ProduktController produktController;
	private ResourceBundle resourceBundle;
	private ProduktModel produktModel;
	private HtmlAusgabe htmlAusgabe;
	private String output;
	private HashMap<String, String> merkmale;
	private static int warenkorbmenge;
	
	/**
	 * 
	 * <p>Konstruktor der Klasse <code>ProduktinfoView</code></p>
	 * <p>Der Konstruktor erstellt die Objekte <code>ProduktController</code>, <code>ProduktModel</code> und  
	 * <code>HtmlAusgabe</code> und &uuml;bergibt den Klassenvariablen <i>kategorie</i> 
	 * und <i>session</i> die benötigten Werte. Desweiteren werden in <i>this.resourceBundle</i> die benötigten 
	 * Texte der aktuellen Sprache abgelegt.</p>
	 * 
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @see javax.servlet.http.HttpServletRequest
	 */
	
	public ProduktinfoView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.produktModel = new ProduktModel();
		this.htmlAusgabe = new HtmlAusgabe(request);
		
		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
	}

	/**
	 * <p>Die Methode <code>anzeigenProduktinfo</code> liefert den <i>HTML</i>-Code f&uuml;r die Anzeige 
	 * der Einzelansicht eines Produkts. Sie l&auml;sst Daten im <code>ProduktController</code> und in der 
	 * <code>HtmlAusgabe</code> bearbeiten und erstellen, um den <i>HTML</i>-Code richtig 
	 * darstellen zu können.</p>
	 * 
	 * @param id - Produkt-ID des anzuzeigenden Produkts
	 * @return <i>String</i> des <i>HTML</i>-Codes f&uuml;r die Produktliste
	 * @see controller#ProduktController
	 * @see view#HtmlAusgabe
	 */
	
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
					+ "<td class=\"produktinfo bestellnummer\">" + this.resourceBundle.getString("PRODUKTNUMMER") + " " + produktModel.getProduktnummer() + "</td>\n"
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
		    		+ "" +this.resourceBundle.getString("MENGE") + " <input class=\"festeTextBoxBreiteMenge\" type=\"text\" name=\"menge\" value=\"" + warenkorbmenge + "\">\n"
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

		return this.output;
	}

}
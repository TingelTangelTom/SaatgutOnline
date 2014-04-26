package view;

import java.util.Locale;

import controller.HtmlOutput;
import controller.ProduktinfoController;
import model.ProduktModel;

public class ProduktinfoView {
	ProduktinfoController produktController;
	ProduktModel produktModel;
	HtmlOutput htmlOutput;
	private String output;	
	public static final int ANZAHL_PRODUKTE_PRO_ZEILE   = 5;
	public static final int ANZAHL_ZEILEN   = 5;
	
	public ProduktinfoView() {
		this.produktController = new ProduktinfoController();
		this.produktModel = new ProduktModel();
		this.htmlOutput = new HtmlOutput();
	}

	//texte.getString("WILLKOMMEN");
	public String anzeigenProduktinfo() {	
		produktModel = this.produktController.getProdukt(1, 1);
		Locale sprache = Locale.GERMAN;
		output = "<table class=\"produktinfo\">"
				+ "<tr><td rowspan=\"6\">Cell 1</td><td colspan=\"2\">" + produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td colspan=\"2\">" + produktModel.getName() + "</td></tr>" // Titel
				+ "<tr><td>Bechriftung</td><td>Cell 3</td></tr>" // Eigenschaft 1
				+ "<tr><td>Bechriftung</td><td>Cell 3</td></tr>" // Eigenschaft 2
				+ "<tr><td>Bechriftung</td><td>Cell 3</td></tr>" // Eigenschaft 3
				+ "<tr><td colspan=\"2\">" + htmlOutput.outPreisformat(sprache, produktModel.getPreis()) + " " + htmlOutput.outPreisverordnung(sprache, 19.00) + "</td></tr>" // Titel
				+ "<tr><td colspan=\"3\">" + produktModel.getBeschreibung() + "</td></tr>" // Produktbeschreibung
				+ "<tr><td colspan=\"3\">Warenkorb</td></tr>" // Button Warenkorb
				+ "</table>";
				
				/*
						+ "<tr>\n<td style=\"width: 340px; text-align: center;\">\n"
						+ "<img src=\"resources/bilder/phoenix_canariensis.jpg\" alt=\"Logo\">\n"
						+ "</td\n><td style=\"width: 430px;\">\n"
						+ produktModel.getName() + "<br />"
						+ "<produktinfo>" + produktModel.getPreis() + "<produktinfo><br />"
						+ produktModel.getGewicht()
						+ "</td></tr><tr><td colspan=\"2\">"
						+ produktModel.getBeschreibung()
						+ "</td></tr><tr><td colspan=\"2\">"
						+ "Button 1 Button 2"
						
						*/
					
		

		
		//produktModel = this.produktController.getProdukt(1);
		//htmlOutput = String.valueOf(produktModel.getKategorieId());
		return output;
	}
	/*
	public String anzeigenListenProdukt() {	
		produktModel = this.produktController.getProdukt(1, 1);
		htmlOutput = "<table border='1' width=150  height=200 cellspacing='0' cellpadding='0'>"
						+ "<tr style=\"height:100;\">"
						+ "<td>"
						+ produktModel.getBeschreibung()
						+ "</td></tr><tr><td>"
						+ "Titel"
						+ "</td></tr><tr><td>"		
						+ "Preis mit Angaben"
						+ "</td></tr><tr><td>"
						+ "Button 1 Button 2"
						+ "</td></tr></table>";
		
		
		//htmlOutput = String.valueOf(produktModel.getKategorieId());
		return htmlOutput;
	}
	*/
}

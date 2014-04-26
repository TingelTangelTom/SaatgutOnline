package view;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import controller.ProduktinfoController;
import model.ProduktModel;

public class ProduktinfoView {
	ProduktinfoController produktController;
	ProduktModel produktModel;
	private String htmlOutput;	
	public static final int ANZAHL_PRODUKTE_PRO_ZEILE   = 5;
	public static final int ANZAHL_ZEILEN   = 5;
	
	public ProduktinfoView()
	{
		this.produktController = new ProduktinfoController();
		this.produktModel = new ProduktModel();
	}
	
	public ResourceBundle outLocale() {
			
		// Sprachwahl des Kunden
		Locale flaggenwahl = Locale.ENGLISH;
		
		ResourceBundle textbundle = PropertyResourceBundle.getBundle("I18N." + flaggenwahl.getLanguage() + ".Produktinfo", flaggenwahl);
		
		return textbundle;
	}
	
	ResourceBundle texte = outLocale();
	
	//texte.getString("WILLKOMMEN");
	public String anzeigenProduktinfo() {	
		produktModel = this.produktController.getProdukt(1, 1);
		htmlOutput = "<table border='1' width=100%  height=100% cellspacing='0' cellpadding='0'>"
						+ "<tr style=\"height:100;\">"
						+ "<td>"
						+ "Bild"
						+ "</td><td>"
						+ produktModel.getName() + "<br />"
						+ produktModel.getPreis() + "<br />"
						+ produktModel.getGewicht()
						+ "</td></tr><tr><td>"
						+ produktModel.getBeschreibung()
						+ "</td><td>"

						+ "</td></tr>"
						+ "<tr><td colspan=\"2\">"
						
						+ "</td></tr>"
						+ "<tr><td colspan=\"2\">"
						+ "Button 1 Button 2"
						+ "</td></tr></table>";
		

		
		//produktModel = this.produktController.getProdukt(1);
		//htmlOutput = String.valueOf(produktModel.getKategorieId());
		return htmlOutput;
	}
	
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
	
}

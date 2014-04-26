package controller;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class HtmlOutput {
	
	public String outPreisformat(Locale sprache, double preis) {
		
		NumberFormat waehrungsFormat = NumberFormat.getCurrencyInstance(sprache);
		String waehrung = waehrungsFormat.format(preis);

		return waehrung;
		
	}
	
	public String outPreisverordnung(Locale sprache, double mwst) {
		
		NumberFormat prozentFormat = NumberFormat.getPercentInstance(sprache);
		String prozent = prozentFormat.format(mwst / 100);
		ResourceBundle text = PropertyResourceBundle.getBundle("I18N." + sprache.getLanguage() + ".Produktinfo", sprache); // Pfad muss noch angepasst werden
		
		return MessageFormat.format(text.getString("PREISTEXT"), prozent);
		
	}
}
package controller;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HtmlOutput {
	private Locale locale;
	private HttpSession session;
	
	public HtmlOutput(HttpServletRequest request) {
		
		this.session = request.getSession();
		this.locale = (Locale)session.getAttribute("sprache");
		
	}
	
	public String outPreisformat(double preis) {
		
		NumberFormat waehrungsFormat = NumberFormat.getCurrencyInstance(this.locale);
		String waehrung = waehrungsFormat.format(preis);

		return waehrung;
		
	}
	
	public String outPreisverordnung(double mwst) {
		
		NumberFormat prozentFormat = NumberFormat.getPercentInstance(this.locale);
		String prozent = prozentFormat.format(mwst / 100);
		ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("I18N." + this.locale.getLanguage() + ".Produktinfo", this.locale); // Pfad muss noch angepasst werden
		
		return MessageFormat.format(resourceBundle.getString("PREISTEXT"), prozent);
		
	}
}
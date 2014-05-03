package view;

import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ProduktModel;
import controller.ProduktController;

public class SuchergebnisseView {
	
	private ProduktController produktController;
	private ResourceBundle resourceBundle;
	private ProduktModel produktModel;
	private HtmlAusgabe htmlAusgabe;
	private String output;
	private HashMap<String, String> merkmale;
	private static int warenkorbmenge;
	
	public SuchergebnisseView(HttpServletRequest request) {
		
		this.produktController = new ProduktController(request);
		this.produktModel = new ProduktModel();
		this.htmlAusgabe = new HtmlAusgabe(request);
		
		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute("sprache");
		this.resourceBundle = PropertyResourceBundle.getBundle("I18N." + locale.getLanguage() + "." + getClass().getSimpleName(), locale);
	}
	
	public String anzeigenSuchergebnisse() {
		this.output = "Test Suchergebnisse";
		return output;
		
	}

}

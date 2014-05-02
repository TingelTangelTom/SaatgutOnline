package view;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import controller.DatenbankController;

public class HtmlAusgabe extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private Locale locale;
	private HttpSession session;
	
	public HtmlAusgabe(HttpServletRequest request) {
		
		this.session = request.getSession();
		this.locale = (Locale)session.getAttribute("sprache");
		
	}
	
/*
		                      
		    System.out.println("doGet... TestNumberFormat !");
		    
		    response.setContentType("text/html");
		    response.setCharacterEncoding("ISO-8859-15");   
		    PrintWriter out = response.getWriter();
		    String title    = "TestNumberFormat Beispiel";

		    double test = 1234.5666;
		    NumberFormat formatter = NumberFormat.getCurrencyInstance();
		    
		    out.println( ServletUtilities.headWithTitle(title) +
		                "<body bgcolor=\"#FDF5E6\">\n" +
		                "<center>\n" +
		                "<h1>" + title + "</h1>" +
		                "Unicode: &#8364 HTML: &
		\n" +  // wird im Forum nicht richtig dargestellt  #8364 und euro;
		                "Betrag: " + formatter.format(test) + "\n" +
		                "</center>\n" +
		                "</body>\n" +
		                "</html>" );
		  }
*/
	public String outPreisformat(double preis) {
		
		NumberFormat waehrungsFormat = NumberFormat.getCurrencyInstance(this.locale);
		waehrungsFormat.setCurrency(Currency.getInstance("EUR"));
		
		


		return waehrungsFormat.format(preis);

	}
	
	/**
	 * 
	 * Diese Methode gibt einen <b>rechtskonformen Text</b> als <code>String</code> für die Preisangabe zurück.
	 * Er setzt sich aus der Angabe der Mehrwertsteuer und einem sichtbaren Link für die Versandkosten zusammen.
	 * @param mwst - <code>double</code>-Parameter des Prozentwert der Mehrwertsteuer als
	 *  
	 * @return <code>String</code>, welcher einen rechtskonformen Text inklusive Versandkosten-Link und Mehrwertsteuer enthält
	 * 
	 * @see model#HtmlAusgabe
	 * 
	 */
	public String outPreisverordnung(double mwst) {
		
		NumberFormat prozentFormat = NumberFormat.getPercentInstance(this.locale);
		String prozent = prozentFormat.format(mwst / 100);
		ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("I18N." + this.locale.getLanguage() + ".ProduktinfoView", this.locale); // Pfad muss noch angepasst werden
		
		return MessageFormat.format(resourceBundle.getString("PREISTEXT"), prozent) + " <a href=\"/SaatgutOnline/Versandkosten\"><b>" + resourceBundle.getString("VERSANDKOSTEN") + "</b></a>";
		
	}
	
	public String outKurzeProduktbeschreibung(String beschreibung, int zeichen, int id) {
		String kurzeBeschreibung = beschreibung.substring(0,zeichen);
		kurzeBeschreibung += "<a href=\"/SaatgutOnline/Produktinfo?produkt=" + id + "\"><b>...(mehr)</b></a>";
		return kurzeBeschreibung;
	}
	
	public String outKategoriename (String kategorie_id) {
		String kategoriename = null;
		
		String query = "SELECT kb.kategorie_beschreibung "
				+ "FROM kategorie AS k "
				+ "INNER JOIN kategorie_beschreibung AS kb ON k.kategorie_id = kb.kategorie_id "
				+ "WHERE kb.sprache_id = '" + session.getAttribute("spracheId") + "' AND kb.kategorie_id = '" + kategorie_id + "'";
		
		try {
			
			ResultSet resultset = DatenbankController.sendeSqlRequest(query);

			while (resultset.next()){
				
				kategoriename = resultset.getString(1);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return kategoriename;
	}

}
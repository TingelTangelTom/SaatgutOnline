package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ProduktModel;



public class ProduktinfoController {
	
	private ProduktModel produktModel;
	private Locale locale;
	private double steuersatz;
	//private double preisBrutto;
	private HashMap<String, String> merkmale;
	
	public ProduktinfoController(HttpServletRequest request) {
		super();
		
		this.produktModel = new ProduktModel();
		this.merkmale = new HashMap<String, String>();
		HttpSession session = request.getSession();
		this.locale = (Locale)session.getAttribute("sprache");
		
	}

	// Testabfrage
	public ProduktModel getProdukt(int id) {
		
		DatenbankController.getVerbindung();

		try {
			String query = "SELECT p.produkt_id, p.produkt_bestand, pb.produkt_name, pb.produkt_beschreibung,"
						+ "pb.produkt_suchbegriffe, pb.produkt_angesehen, p.produkt_preis, p.produkt_gewicht,"
						+ "p.produkt_steuer_id, p.produkt_datum_hinzugefuegt, p.produkt_datum_geaendert FROM "
						+ "produkt AS p INNER JOIN produktbeschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "WHERE pb.sprache_id = '1' AND p.produkt_id = '" + id + "' ORDER "
						+ "BY pb.produkt_name";

		
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()){
				this.produktModel.setId(resultset.getInt(1));
				this.produktModel.setBestand(resultset.getInt(2));
				this.produktModel.setName(resultset.getString(3));
				this.produktModel.setBeschreibung(resultset.getString(4));
				this.produktModel.setSuchbegriffe(resultset.getString(5));
				this.produktModel.setAngesehen(resultset.getInt(6));
				this.produktModel.setPreisNetto(resultset.getInt(7));
				this.produktModel.setGewicht(resultset.getInt(8));
				this.produktModel.setSteuerId(resultset.getInt(9));
				this.produktModel.setHinzugefeugt(resultset.getDate(10));
				this.produktModel.setGeaendert(resultset.getDate(11));
				this.produktModel.setSteuerId(resultset.getInt(9));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String query = "SELECT steuersatz FROM steuersatz WHERE steuersatz_id = " + this.produktModel.getSteuersatz_id();
			
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			if(resultset.next()){
				steuersatz = resultset.getDouble(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.produktModel.setSteuersatz(steuersatz);
		this.produktModel.setPreisBrutto(this.produktModel.getPreisNetto() * this.produktModel.getSteuersatz() / 100 + this.produktModel.getPreisNetto());
		
		try {
			//String query = "SELECT produktmerkmal_name, produktmerkmal_wert FROM produktmerkmal_zuordnung WHERE sprache_id = '2' AND produkt_id = '1'";
			
			String query = "(SELECT produktmerkmal_id, produktmerkmal_wert FROM produktmerkmal_zuordnung WHERE produkt_id = '1' AND sprache_id = '2')";
			String query2 = "(SELECT produktmerkmal_name FROM produktmerkmal WHERE ";
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			
			String name;
			String wert;
			
			while (resultset.next()){
				name = resultset.getString(1);
				wert = resultset.getString(2);
				System.out.println(name + " " + wert);
				this.merkmale.put(name, wert);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.produktModel.setMerkmale(merkmale);

		return produktModel;
	}

	public double getProduktSteuersatz(int id, int sprache_id) {
		
		
		
		return steuersatz;
	}	

}
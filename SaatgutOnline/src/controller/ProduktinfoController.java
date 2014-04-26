package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.ProduktModel;



public class ProduktinfoController {
	
	private ProduktModel produktModel;
	
	public ProduktinfoController() {
		super();
		this.produktModel = new ProduktModel();
	}

	// Testabfrage
	public ProduktModel getProdukt(int id, int sprache_id) {
		
		DatenbankController.getVerbindung();
		try {
			String query = "SELECT p.produkt_id, p.produkt_bestand, pb.produkt_name, pb.produkt_beschreibung,"
						+ "pb.produkt_suchbegriffe, pb.produkt_angesehen, p.produkt_preis, p.produkt_gewicht,"
						+ "p.produkt_steuer_id, p.produkt_datum_hinzugefuegt, p.produkt_datum_geaendert FROM "
						+ "produkt AS p INNER JOIN produktbeschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "WHERE pb.sprache_id = '" + sprache_id + "' AND p.produkt_id = '" + id + "' ORDER "
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
				this.produktModel.setPreis(resultset.getInt(7));
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
			String query = "SELECT steuer_satz FROM steuer WHERE steuer_id = '1'";
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()){
				this.produktModel.setPreis(this.produktModel.getPreis() * resultset.getDouble(1) / 100 + this.produktModel.getPreis());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		return produktModel;
	}

}
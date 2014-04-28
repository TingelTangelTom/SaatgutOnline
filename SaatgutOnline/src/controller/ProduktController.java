package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ProduktModel;



public class ProduktController {
	
	private ProduktModel produktModel;
	private double steuersatz;
	//private double preisBrutto;
	private HashMap<String, String> merkmale;
	private int sprache_id;
	private ArrayList<ProduktModel> produktliste;
	
	public ProduktController(HttpServletRequest request) {
		super();
		
		HttpSession session = request.getSession();
		this.produktModel = new ProduktModel();
		this.merkmale = new HashMap<String, String>();
		this.produktliste = new ArrayList<>();
		this.sprache_id = (int)session.getAttribute("spracheId");
		
	}

	// Testabfrage
	public ProduktModel getProdukt(int id) {
		
		DatenbankController.getVerbindung();
		
		try {
			String query = "SELECT p.produkt_id, p.produkt_bestand, pb.produkt_name, pb.produkt_beschreibung,"
						+ "pb.produkt_suchbegriffe, pb.produkt_angesehen, p.produkt_preis, p.produkt_gewicht,"
						+ "p.produkt_steuer_id, p.produkt_datum_hinzugefuegt, p.produkt_datum_geaendert FROM "
						+ "produkt AS p INNER JOIN produktbeschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "WHERE pb.sprache_id = '" + this.sprache_id + "' AND p.produkt_id = '" + id + "' ORDER "
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
			e.printStackTrace();
		}

		this.produktModel.setSteuersatz(steuersatz);
		this.produktModel.setPreisBrutto(this.produktModel.getPreisNetto() * this.produktModel.getSteuersatz() / 100 + this.produktModel.getPreisNetto());
		
		try {
			
			String query = "SELECT pf.produktfelder_name AS name, pfz.produktfelder_wert AS wert FROM produktfelder AS pf "
							+ "LEFT JOIN produktfelder_zuordnung AS pfz ON pfz.produktfelder_id = pf.produktfelder_id WHERE pfz.produkt_id = '1' AND "
							+ "pfz.produktfelder_wert<>'' AND (pf.sprache_id= '" + this.sprache_id + "' AND pf.sprache_id = '" + this.sprache_id + "') ORDER BY pf.sortier_reihenfolge";
			
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
			e.printStackTrace();
		}

		this.produktModel.setMerkmale(merkmale);

		return this.produktModel;
	}

	public ArrayList<ProduktModel> getProduktliste(int kategorie_id) {
		
		DatenbankController.getVerbindung();
		
		try {
			ArrayList<Integer> produkte = new ArrayList<>();
			//String query = "SELECT p.produkt_id, p.produkt_bestand, p.kategorie_id, p.produkt_steuer_id FROM produkt AS p LEFT JOIN kategorie AS k ON p.kategorie_id = k.eltern_id WHERE k.sprache_id = '" + this.sprache_id + "'";
			String query = "SELECT p.produkt_id,k.kategorie_id, p.produkt_bestand, p.kategorie_id, p.produkt_steuer_id FROM produkt AS p "
					+ "LEFT JOIN kategorie AS k ON k.kategorie_id = p.kategorie_id WHERE k.kategorie_id = '6' AND k.sprache_id= '1'";					

			String query2 = "SELECT pf.produktfelder_name AS name, pfz.produktfelder_wert AS wert FROM produktfelder AS pf "
					+ "LEFT JOIN produktfelder_zuordnung AS pfz ON pfz.produktfelder_id = pf.produktfelder_id WHERE pfz.produkt_id = '1' AND "
					+ "pfz.produktfelder_wert<>'' AND (pf.sprache_id= '" + this.sprache_id + "' AND pf.sprache_id = '" + this.sprache_id + "') ORDER BY pf.sortier_reihenfolge";
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()){
				produkte.add(resultset.getInt(1));
				System.out.println("Produkt ID. " + resultset.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		try {	
			System.out.println(this.sprache_id);
			String query = "SELECT p.produkt_id, p.produkt_bestand, p.kategorie_id, p.produkt_steuer_id "
							+ "FROM produkt AS p LEFT JOIN kategorie AS k ON p.kategorie_id = k.kategorie_id WHERE "
							+ "k.eltern_id = '" + kategorie_id + "'";
		
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()){
				this.produktModel.setId(resultset.getInt(1));
				//System.out.println(resultset.getInt(1));
				this.produktModel.setBestand(resultset.getInt(2));
				this.produktModel.setKategorieId(resultset.getInt(3));
				this.produktModel.setSteuerId(resultset.getInt(4));

				//System.out.println(resultset.getInt(4));
				String query2 = "SELECT produkt_name, produkt_beschreibung FROM produktbeschreibung WHERE sprache_id = "
								+ "'" + this.sprache_id + "' AND produkt_id = '" + resultset.getInt(1) + "'";
		
			
			ResultSet resultset2 = statement.executeQuery(query2);
			this.produktModel.setName(resultset2.getString(1));
			this.produktModel.setBeschreibung(resultset2.getString(2));
			System.out.println(resultset.getString(1));
			this.produktliste.add(this.produktModel);
			System.out.println(this.produktModel);	
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		//System.out.println("test");
		
		for (int i = 0; i < this.produktliste.size(); i++) {
			//System.out.println(i);
		}
		
		return this.produktliste;
	}
	
	public double getProduktSteuersatz(int id, int sprache_id) {
		
		return steuersatz;
	}	

}
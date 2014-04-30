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
	private HashMap<String, String> merkmale;
	private int sprache_id;
	private String kategorie;
	private String sortierung;
	private String anzahlProdukte;
	private String limitVon;
	private String limitBis;
	private String sortierspalte;
	
	public ProduktController(HttpServletRequest request) {
		super();
		
		HttpSession session = request.getSession();
		this.produktModel = new ProduktModel();
		this.merkmale = new HashMap<String, String>();
		this.sprache_id = (int)session.getAttribute("spracheId");
		this.kategorie = request.getParameter("kategorie");
		
		this.getSortierung(request);
		
	}

	// Testabfrage
	public ProduktModel getProdukt(int id) {
		
		DatenbankController.getVerbindung();
		
		try {
			
			String query = "SELECT p.produkt_id, p.produkt_bestand, pb.produkt_name, pb.produkt_beschreibung,"
						+ "pb.produkt_suchbegriffe, p.produkt_angesehen, p.produkt_preis, p.produkt_gewicht,"
						+ "p.produkt_steuer_id, p.produkt_datum_hinzugefuegt, p.produkt_datum_geaendert "
						+ "FROM produkt AS p "
						+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "WHERE pb.sprache_id = '" + this.sprache_id + "' AND p.produkt_id = '" + id + "'";

		
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()){
				this.produktModel = new ProduktModel();
				this.produktModel.setId(resultset.getInt(1));
				this.produktModel.setBestand(resultset.getInt(2));
				this.produktModel.setName(resultset.getString(3));
				this.produktModel.setBeschreibung(resultset.getString(4));
				this.produktModel.setSuchbegriffe(resultset.getString(5));
				this.produktModel.setAngesehen(resultset.getInt(6));
				this.produktModel.setPreisNetto(resultset.getDouble(7));
				this.produktModel.setGewicht(resultset.getInt(8));
				this.produktModel.setSteuerBetrag(resultset.getDouble(9));
				this.produktModel.setHinzugefeugt(resultset.getDate(10));
				this.produktModel.setGeaendert(resultset.getDate(11));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			String query = "SELECT steuersatz FROM steuersatz WHERE steuersatz_id = " + this.produktModel.getSteuerBetrag();
			
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			
			if(resultset.next()){
				steuersatz = resultset.getDouble(1);
			}
			
			this.produktModel.setSteuerSatz(steuersatz);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.produktModel.setPreisBrutto(this.produktModel.getPreisNetto() * this.produktModel.getSteuerSatz() / 100 + this.produktModel.getPreisNetto());
		this.produktModel.setSteuerBetrag(this.produktModel.getPreisBrutto() + this.produktModel.getPreisNetto());
		
		try {
			
			String query = "SELECT pf.produktfelder_name AS name, pfz.produktfelder_wert AS wert "
							+ "FROM produktfelder AS pf "
							+ "LEFT JOIN produktfelder_zuordnung AS pfz ON pfz.produktfelder_id = pf.produktfelder_id "
							+ "WHERE pfz.produkt_id = '" + id + "' "
							+ "AND pfz.produktfelder_wert<>'' "
							+ "AND (pf.sprache_id= '" + this.sprache_id + "' AND pf.sprache_id = '" + this.sprache_id + "') "
							+ "ORDER BY pf.sortier_reihenfolge";
			
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			
			String name;
			String wert;
			
			while (resultset.next()){
				name = resultset.getString(1);
				wert = resultset.getString(2);
				this.merkmale.put(name, wert);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.produktModel.setMerkmale(merkmale);
		
		return this.produktModel;
	}

	public ArrayList<ProduktModel> getProduktliste(String kategorie_id) {
		
		DatenbankController.getVerbindung();
		ArrayList<Integer> kategorie_ids = new ArrayList<>();
		ArrayList<ProduktModel> produkte = new ArrayList<>();

		try {
			System.out.println("Gewählte Kategorie: " + this.kategorie);
			Statement statement = DatenbankController.verbindung.createStatement();
			String kategorie_query = "SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + this.kategorie + "' OR (kategorie_id = '" + this.kategorie + "' AND eltern_id = 0)";
			
			ResultSet kategorie_resultset = statement.executeQuery(kategorie_query);
			
			while(kategorie_resultset.next()){
				System.out.println("Kategorie ID. " + kategorie_resultset.getInt(1));
				kategorie_ids.add(kategorie_resultset.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String spaltenwahl = null;
	        switch (sortierspalte) {
	          case "pn":
	        	  spaltenwahl = "pb.produkt_name";
	            break;
	          case "pk":
	        	  spaltenwahl = "p.kategorie";
	            break;
	          case "pb":
	        	  spaltenwahl = "p.produkt_bestand";
	            break;
	          case "pa":
	        	  spaltenwahl = "p.angesehen";
	            break;
	          case "pp":
	        	  spaltenwahl = "p.produkt_preis";
	            break;
	          case "pd":
	        	  spaltenwahl = "p.produkt_datum_hinzugefuegt";
	            break;
	          default:
	        	  spaltenwahl = "pb.produkt_name";
	        	break;
	        }
	      
		
		for (int i = 0; i < kategorie_ids.size(); i++) {	
			
			String produkt_query = "SELECT p.produkt_id, pb.produkt_name "
					+ "FROM produkt AS p "
					+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
					+ "WHERE pb.sprache_id = '" + this.sprache_id + "' AND  p.kategorie_id = '" + kategorie_ids.get(i) + "' ORDER BY " + spaltenwahl + " " + this.sortierung;

			
			try {
				
			Statement statement2 = DatenbankController.verbindung.createStatement();
			ResultSet produkt_resultset = statement2.executeQuery(produkt_query);

			while(produkt_resultset.next()){
				System.out.println("Produkt ID hinzugefügt: " + produkt_resultset.getInt(1));
				
				//System.out.println(this.produktModel.getBestand());
				produkte.add(this.getProdukt(produkt_resultset.getInt(1)));
				//System.out.println("Produkt " + produkt_resultset.getInt(1) + " hinzugefügt");
								
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		System.out.println("Gespeicherte Produkte: ");
		
		for (int i = 0; i < produkte.size(); i++) {
			System.out.println(produkte.get(i).getId());
			
		}
		return produkte;
	}

	public void getSortierung (HttpServletRequest request) {

		if(request.getParameter("p_anzeige") == null) {
			this.sortierung = "ASC";
			this.anzahlProdukte = "3";
			this.limitVon = "1";
			this.limitBis = "4";
			this.sortierspalte = "pb.produkt_name";
		} else {
			String[] parameterAufteilung = sortierung.split(",");
			if(parameterAufteilung[0].equalsIgnoreCase("true")) {
				this.sortierung = "ASC";
			} else {
				this.sortierung = "DESC";
			}
			this.sortierung = parameterAufteilung[0];
			this.anzahlProdukte = parameterAufteilung[1];
			this.limitVon = parameterAufteilung[2];
			this.limitBis = parameterAufteilung[3];
			this.limitBis = parameterAufteilung[4];
		}
		System.out.println(this.sortierung);
		System.out.println(this.anzahlProdukte);
		System.out.println(this.limitVon);
		System.out.println(this.limitBis);

	}

}
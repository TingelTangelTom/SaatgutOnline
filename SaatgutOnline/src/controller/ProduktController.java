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
	private ProduktController produktController;
	private double steuersatz;
	//private double preisBrutto;
	private HashMap<String, String> merkmale;
	private int sprache_id;
	private ArrayList<ProduktModel> produktliste;
	private String kategorie;
	
	public ProduktController(HttpServletRequest request) {
		super();
		
		HttpSession session = request.getSession();
		this.produktModel = new ProduktModel();
		this.merkmale = new HashMap<String, String>();
		this.produktliste = new ArrayList<>();
		this.sprache_id = (int)session.getAttribute("spracheId");
		this.kategorie = request.getParameter("kategorie");
		
	}

	// Testabfrage
	public ProduktModel getProdukt(int id) {
		
		DatenbankController.getVerbindung();
		
		try {
			
			System.out.println("getProdukt holt Produkt mit der ID: " + id);
			String query = "SELECT p.produkt_id, p.produkt_bestand, pb.produkt_name, pb.produkt_beschreibung,"
						+ "pb.produkt_suchbegriffe, pb.produkt_angesehen, p.produkt_preis, p.produkt_gewicht,"
						+ "p.produkt_steuer_id, p.produkt_datum_hinzugefuegt, p.produkt_datum_geaendert "
						+ "FROM produkt AS p "
						+ "INNER JOIN produktbeschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "WHERE pb.sprache_id = '" + this.sprache_id + "' AND p.produkt_id = '" + id + "' "
						+ "ORDER BY pb.produkt_name";
		
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()){
				this.produktModel = new ProduktModel();
				
				this.produktModel.setId(resultset.getInt(1));
				System.out.println("ProduktID " + resultset.getInt(1) + " wurde gespeichert!");
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
		System.out.println("Nochmaliger Test der ProduktId. Sie lautet: " + this.produktModel.getId());
		return this.produktModel;
	}

	public ArrayList<ProduktModel> getProduktliste(String kategorie_id) {
		
		DatenbankController.getVerbindung();
		ArrayList<Integer> kategorie_ids = new ArrayList<>();
		ArrayList<ProduktModel> produkte = new ArrayList<>();
		//if(this.kategorie == null) {
			//this.kategorie = "1";
		//}
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
		
		for (int i = 0; i < kategorie_ids.size(); i++) {				
			String produkt_query = "SELECT produkt_id, produkt_bestand FROM produkt WHERE kategorie_id = '" + kategorie_ids.get(i) +"'";
			try {
			Statement statement2 = DatenbankController.verbindung.createStatement();
			ResultSet produkt_resultset = statement2.executeQuery(produkt_query);
			
			while(produkt_resultset.next()){
				System.out.println("Produkt ID. " + produkt_resultset.getInt(1));
				System.out.println("Bestand. " + produkt_resultset.getInt(2));
				
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
	
	public double getProduktSteuersatz(int id, int sprache_id) {
		
		return steuersatz;
	}	

}
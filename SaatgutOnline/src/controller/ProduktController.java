package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;




import model.ProduktModel;


/**
 * 
 * Der ProduktController liefert Rückgabewerte für <code>ProduktlisteView</code>
 * und <code>ProduktinfoView</code>. Ausserdem stellt er Methoden zur Verfügung, 
 * welche für das Bearbeiten der Rückgabewerte benötigt werden.
 * 
 * @author Simon Ankele
 *
 */
public class ProduktController {
	
	private ProduktModel produktModel;
	private double steuersatz;
	private int sprache_id;
	private String kategorie;
	private String sortierung;
	private String anzahlProdukte;
	private String limitVon;
	private String sortierspalte;
	
	public ProduktController(HttpServletRequest request) {
		super();
		
		HttpSession session = request.getSession();
		this.produktModel = new ProduktModel();
		this.sprache_id = (int)session.getAttribute("spracheId");
		this.kategorie = request.getParameter("kategorie");
		
		this.getSortierung(request);
		
	}

	/**
	 * Liest aus der Datenbank einen bestimmtes Produkt (<code>ProduktModel</code>) aus,
	 * welches über die Produkt ID gesucht wird.
	 * 
	 * @param  id - ID des Produktes, über die in der Datenbank gesucht wird
	 *              
	 * @return <code>ProduktModel</code> - Liefert ein Produkt mit allen Inhalten zurück
	 * 
	 * @see model#ProduktModel
	 * 
	 * @
	 */
	
	public ProduktModel getProdukt(int id) {
		
		try {
			
			String query = "SELECT p.produkt_id, p.produkt_bestand, pb.produkt_name, pb.produkt_beschreibung,"
						+ "pb.produkt_suchbegriffe, p.produkt_angesehen, p.produkt_preis, p.produkt_gewicht,"
						+ "p.produkt_steuer_id, p.produkt_datum_hinzugefuegt, p.produkt_datum_geaendert "
						+ "FROM produkt AS p "
						+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "WHERE pb.sprache_id = '" + this.sprache_id + "' AND p.produkt_id = '" + id + "'";
			
			ResultSet resultset = DatenbankController.sendeSqlRequest(query);
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
			
			ResultSet resultset = DatenbankController.sendeSqlRequest(query);
			
			if(resultset.next()){
				steuersatz = resultset.getDouble(1);
			}
			
			this.produktModel.setSteuerSatz(steuersatz);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.produktModel.setPreisBrutto(this.produktModel.getPreisNetto() * this.produktModel.getSteuerSatz() / 100 + this.produktModel.getPreisNetto());
		this.produktModel.setSteuerBetrag(this.produktModel.getPreisBrutto() + this.produktModel.getPreisNetto());
		
		getProduktMerkmale(id);

		return this.produktModel;
	}
	
	/**
	 * Diese Methode setzt im <code>ProduktModel</code> die Merkmale des Produktes. 
	 * 
	 * @param name - Beschreibung des Merkmals
	 * @param wert - der Wert des Merkmals
	 * @param id - ID des Produktes, über die in der Datenbank gesucht wird
	 */
	
	public void getProduktMerkmale(int id) {
			
		HashMap<String, String> merkmale = new HashMap<String, String>();
		String name;
		String wert;
		String query = "SELECT pf.produktfelder_name AS name, pfz.produktfelder_wert AS wert "
						+ "FROM produktfelder AS pf "
						+ "LEFT JOIN produktfelder_zuordnung AS pfz ON pfz.produktfelder_id = pf.produktfelder_id "
						+ "WHERE pfz.produkt_id = '" + id + "' "
						+ "AND pfz.produktfelder_wert<>'' "
						+ "AND (pf.sprache_id= '" + this.sprache_id + "' AND pf.sprache_id = '" + this.sprache_id + "') "
						+ "ORDER BY pf.sortier_reihenfolge";
		
		try {
			
			ResultSet resultset = DatenbankController.sendeSqlRequest(query);

			while (resultset.next()){
				name = resultset.getString(1);
				wert = resultset.getString(2);
				merkmale.put(name, wert);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.produktModel.setMerkmale(merkmale);
	}
	
	/**
	 * Diese Methode erstellt eine ArrayList mit Produkten (ProduktModel).
	 * Jedes Produkt hat eine Kategorie ID. Dadurch lässt sich feststellen, 
	 * ob sich ein Produkt in einer Unterkategorie oder in der Hauptkategorie befindet. 
	 * Falls die Kategorie eine Hauptkategorie ist, werden alle Produkte der 
	 * Hauptkategorie und der dazugehörigen Unterkategorien in die ArrayList geschrieben.
	 * <code>getProdukt()</code>
	 *  
	 * @param  kategorie_id				Die Kategorie ID ist im Produkt hinterlegt
	 *            
	 * @return <code>ArrayList</code> 	Liefert eine ArrayList mit Produkten zurück
	 * 
	 */

	public ArrayList<ProduktModel> getProduktliste(String kategorie_id) {
		
		ArrayList<ProduktModel> produkte = new ArrayList<>();
		
		if(this.kategorie == null) {
			this.kategorie = "1";
		}
		System.out.println("Kategorie: " + this.kategorie);
		String produkt_query = "SELECT p.produkt_id, pb.produkt_name "
								+ "FROM produkt AS p "
								+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
								+ "WHERE pb.sprache_id = '" + this.sprache_id + "' "
								+ "AND  p.kategorie_id IN (SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + this.kategorie + "' OR (kategorie_id = '" + this.kategorie + "' AND eltern_id = 0)) "
								+ "ORDER BY " + this.sortierspalte + " " + this.sortierung + ""
								+ " LIMIT " + this.limitVon + "," + this.anzahlProdukte;
		
		try {
			
			ResultSet produkt_resultset = DatenbankController.sendeSqlRequest(produkt_query); 

			while(produkt_resultset.next()){
				
				produkte.add(this.getProdukt(produkt_resultset.getInt(1)));
								
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return produkte;
	}
	//TODO Javadoc param bearbeiten
	/**
	 * Die Methode <code>getSortierung (HttpServletRequest request)</code> legt fest, 
	 * wie Produkte in der Produktliste-Anschau angezeigt werden. Festgelegt wird, 
	 * wieviele Produkte pro Seite angezeigt werden sollen, welche Sortierreihenfolge 
	 * sie haben (DESC | ASC), ab welchem Produkt die Anzeige stattfinden soll und nach 
	 * welchem Kriterium die Anzeige ausgegeben werden soll (Name, Preis, Bestand, Datum, 
	 * Kategorie und beliebte Produkte).
	 * 
	 * @param request
	 * @param sortierung - ASC | DESC
	 * @param anzahlProdukte - wieviele Produkte angezeigt werden sollen
	 * @param sortierung - ab welchem Produkt in der Produktliste die Ausgabe starten soll
	 * @param limitVon - nach welchem Kriterium sortiert werden soll
	 * 
	 */

	public void getSortierung (HttpServletRequest request) {
		String parameter = request.getParameter("p_anzeige");
		if(request.getParameter("p_anzeige") == null) {
			
			this.sortierung = "ASC";
			this.anzahlProdukte = "3";
			this.limitVon = "0";
			this.sortierspalte = "pb.produkt_name";
			
		} else {
			
			String[] parameterAufteilung = parameter.split(",");
			if(this.sortierung != null) {
				this.sortierung = "DESC";
			} else {
				this.sortierung = "ASC";
			}
	        switch (parameterAufteilung[3]) {
	          case "pn":
	        	  this.sortierspalte = "pb.produkt_name";
	            break;
	          case "pk":
	        	  this.sortierspalte = "p.kategorie";
	            break;
	          case "pb":
	        	  this.sortierspalte = "p.produkt_bestand";
	            break;
	          case "pa":
	        	  this.sortierspalte = "p.angesehen";
	            break;
	          case "pp":
	        	  this.sortierspalte = "p.produkt_preis";
	            break;
	          case "pd":
	        	  this.sortierspalte = "p.produkt_datum_hinzugefuegt";
	            break;
	          default:
	        	  this.sortierspalte = "pb.produkt_name";
	        	break;
	        	
	        }
	        System.out.println("Parameter - Sortierung: " + this.sortierung);
			this.anzahlProdukte = parameterAufteilung[1];
			this.limitVon = parameterAufteilung[2];

		}


	}

}
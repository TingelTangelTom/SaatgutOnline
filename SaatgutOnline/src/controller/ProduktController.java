package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public ProduktController(HttpServletRequest request) {
		super();
		
		HttpSession session = request.getSession();
		this.produktModel = new ProduktModel();
		this.sprache_id = (int)session.getAttribute("spracheId");
		
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
						+ "pb.produkt_suchbegriffe, p.produkt_angesehen, p.produkt_preis, p.produkt_vpe,"
						+ "p.produkt_steuer_id, p.produkt_datum_hinzugefuegt, p.produkt_datum_geaendert, p.produkt_bestellnummer "
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
				this.produktModel.setPreisNetto(runden(resultset.getDouble(7), 2));
				this.produktModel.setVpe(resultset.getInt(8));
				this.produktModel.setSteuerBetrag(resultset.getDouble(9));
				this.produktModel.setHinzugefeugt(resultset.getDate(10));
				this.produktModel.setGeaendert(resultset.getDate(11));
				this.produktModel.setBestellnummer(resultset.getString(12));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getProduktMerkmale(id);
		getSteuerinformationen(id);

		return this.produktModel;
	}	
	
	public HashMap<Integer, String> getAlleKategorien() {
		HashMap<Integer, String> kategorien = new HashMap<Integer, String>();
		Integer kategorie_id;
		String wert;
		try {
			/*
			String query = "SELECT kategorie_id, kategorie_beschreibung "
						+ "FROM kategorie_beschreibung "
						+ "WHERE sprache_id = '" + this.sprache_id + "'";
			*/
			String query = "SELECT k.kategorie_id, k.eltern_id, kb.kategorie_name "
			+ "FROM kategorie AS k "
			+ "INNER JOIN kategorie_beschreibung AS kb ON k.kategorie_id = kb.kategorie_id "
			+ "WHERE kb.sprache_id = '" + this.sprache_id + "'";
			
			ResultSet resultset = DatenbankController.sendeSqlRequest(query);
			while(resultset.next()){
				kategorie_id = resultset.getInt(1);
				if(resultset.getInt(2) > 0) {
					wert = "- " + resultset.getString(3);
				} else {
					wert = resultset.getString(3);
				}
					kategorien.put(kategorie_id, wert);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return kategorien;
	}
	
	/**
	 * 
	 * @param id - ID des Produktes, über die in der Datenbank gesucht wird
	 */
	private void getSteuerinformationen(int id) {
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
		
		this.produktModel.setPreisBrutto(runden(this.produktModel.getPreisNetto() * this.produktModel.getSteuerSatz() / 100 + this.produktModel.getPreisNetto(), 2));
		this.produktModel.setSteuerBetrag(runden(this.produktModel.getPreisBrutto() - this.produktModel.getPreisNetto(), 2));
	}
	
	/**
	 * Diese Methode setzt im <code>ProduktModel</code> die Merkmale des Produktes. 
	 * 
	 * @param name - Beschreibung des Merkmals
	 * @param wert - der Wert des Merkmals
	 * @param id - ID des Produktes, über die in der Datenbank gesucht wird
	 */
	
	private void getProduktMerkmale(int id) {
			
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

	public ArrayList<ProduktModel> getProduktliste(HttpServletRequest request, String kategorie_id, boolean suche) {
		
		HttpSession session = ((HttpServletRequest) request).getSession(); 
		ArrayList<Integer> kategorien = new ArrayList<>();
		int kategoriesuche_eltern_id = 0;
		String produkt_query;
	
		String kategorie_query = "SELECT eltern_id FROM kategorie WHERE kategorie_id = '" + kategorie_id + "'";
		
		try {
			
			ResultSet produkt_resultset = DatenbankController.sendeSqlRequest(kategorie_query); 

			while(produkt_resultset.next()){
				
				kategoriesuche_eltern_id = produkt_resultset.getInt(1);
								
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(kategoriesuche_eltern_id == 0) {
			produkt_query = "SELECT p.produkt_id "
					+ "FROM produkt AS p "
					+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
					+ "WHERE pb.sprache_id = '" + this.sprache_id + "' "
					+ "AND  p.kategorie_id IN (SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + kategorie_id + "' OR (kategorie_id = '" + kategorie_id + "' AND eltern_id = 0)) "
					+ "ORDER BY " + session.getAttribute("sortierung_sortierspalte") + " " + session.getAttribute("sortierung_reihenfolge") + " "
					+ "LIMIT " + session.getAttribute("sortierung_limit_von") + "," + session.getAttribute("sortierung_produktanzahl") + "";
					
		} else {
			produkt_query = "SELECT p.produkt_id "
					+ "FROM produkt AS p "
					+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
					+ "WHERE pb.sprache_id = '" + this.sprache_id + "' "
					+ "AND  p.kategorie_id = '" + kategorie_id +"' "
					+ "ORDER BY " + session.getAttribute("sortierung_sortierspalte") + " " + session.getAttribute("sortierung_reihenfolge") + " "
					+ "LIMIT " + session.getAttribute("sortierung_limit_von") + "," + session.getAttribute("sortierung_produktanzahl") + "";

		}
		/*
		try {
		
			ResultSet produkt_resultset = DatenbankController.sendeSqlRequest(kategorie_query); 

			while(produkt_resultset.next()){
				
				kategorien.add(produkt_resultset.getInt(1));
								
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		System.out.println(produkt_query);
		ArrayList<ProduktModel> produkte = new ArrayList<>();

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
	 * Die Methode <code>getSortierung(HttpServletRequest request)</code> legt fest, 
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

	public void getSortierung(HttpServletRequest request) {
		HttpSession session = ((HttpServletRequest) request).getSession();
		System.out.println("existiert die Session?");

		if(request.getParameter("sortierung") != null) {
			System.out.println("----> Sortierung ist ungleich null");
			//  Wenn sortierung_sortierspalte bereits auf demselben Wert steht, wechselt die Sortierreihenfolge
			if(request.getParameter("sortierung").equals((String)session.getAttribute("sortierung_sortierspalte_kuerzel"))) {
				System.out.println("gleicher Wert");
				if(session.getAttribute("sortierung_reihenfolge").equals("DESC")) {
					System.out.println("reihenfolge war gleich");
					session.setAttribute("sortierung_reihenfolge", "ASC");
				} else {
					System.out.println("reihenfolge war NICHT gleich");		
					session.setAttribute("sortierung_reihenfolge", "DESC");					
				}
				
			}
			
			switch (request.getParameter("sortierung")) {
	          case "pn":
	        	  session.setAttribute("sortierung_sortierspalte", "pb.produkt_name");
	        	  session.setAttribute("sortierung_sortierspalte_kuerzel", "pn");
	            break;
	          case "pk":
	        	  session.setAttribute("sortierung_sortierspalte", "p.kategorie");
	        	  session.setAttribute("sortierung_sortierspalte_kuerzel", "pk");
	            break;
	          case "pb":
	        	  session.setAttribute("sortierung_sortierspalte", "p.produkt_bestand");
	        	  session.setAttribute("sortierung_sortierspalte_kuerzel", "pb");
	            break;
	          case "pa":
	        	  session.setAttribute("sortierung_sortierspalte", "p.angesehen");
	        	  session.setAttribute("sortierung_sortierspalte_kuerzel", "pa");
	            break;
	          case "pp":
	        	  session.setAttribute("sortierung_sortierspalte", "p.produkt_preis");	
	        	  session.setAttribute("sortierung_sortierspalte_kuerzel", "pp");
	            break;
	          case "pd":
	        	  session.setAttribute("sortierung_sortierspalte", "p.produkt_datum_hinzugefuegt");
	        	  session.setAttribute("sortierung_sortierspalte_kuerzel", "pd");
	            break;
	          default:
	        	  session.setAttribute("sortierung_sortierspalte", "pb.produkt_name");
	        	  session.setAttribute("sortierung_sortierspalte_kuerzel", "pn");
	        	break;	
	        }
			
		}

	}
	//TODO kommt das da wirklich hin?
	public static double runden(double wert, int stellen) {
		double gerundet = Math.round(wert * Math.pow(10d, stellen));
		return gerundet / Math.pow(10d, stellen);
	} 

}
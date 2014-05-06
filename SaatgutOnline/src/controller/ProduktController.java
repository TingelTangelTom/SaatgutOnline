package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import view.WarenkorbView;
import model.ProduktModel;

/**
 * <p>Die Klasse <code>ProduktController</code> liefert Rückgabewerte für die 
 * Darstellung und Organisation der Produktliste und der Einzelansicht von Produkten Verfuegung.</p>
 * 
 * @author Simon Ankele
 * @version 1.0
 * @since 1.7.0_51
 * 
 */

public class ProduktController {
	
	private ProduktModel produktModel;
	private double steuersatz;
	private int sprache_id;
	
	/**
	 * <p>Konstruktor der Klasse <code>ProduktController</code></p>
	 * <p>Ruft die Methode <code>warenkorbAusSessionHolen()</code> auf.
	 * </br>Die Methode holt einen bestehenden Warenkorb aus der <code>HttpSession</code>
	 * und legt ihn in der Variable <i>warenkorb</i> ab. Falls kein Warenkorb in der Session
	 * liegt, wird er erzeugt.</p>
	 * <p>Ruft die Methode <code>warenkorbAktualiseren()</code> auf.
	 * </br>Die Methode stellt die Funktionalitaet fuer den Warenkorb zur Verfuegung:
	 * </br>- Produkt hinzufuegen
	 * </br>- Produkt entfernen
	 * </br>- Menge aendern</p>
	 * <p>Der aktuelle Warenkorb wird in die Session geschrieben.
	 * </p>
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @param response - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpSession
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	
	/**
	 * 
	 * <p>Konstruktor der Klasse <code>ProduktController</code></p>
	 * <p>Der Konstruktor erstellt ein <code>ProduktModel</code>-Objekt und speichert 
	 * das Session-Attribut <i>spracheId</i> in der Klassenvariablen <i>sprache_id</i>
	 * 
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * 
	 */
	
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
						+ "p.produkt_steuer_id, p.produkt_datum_hinzugefuegt, p.produkt_datum_geaendert, p.produkt_produktnummer "
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
				this.produktModel.setProduktnummer(resultset.getString(12));
				
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getProduktMerkmale(id);
		getSteuerinformationen(id);
		getAngebot(id);

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
	
	private void getAngebot(int id) {
		try {
			String count_query = "SELECT COUNT(a.produkt_id) FROM angebot AS a LEFT JOIN produkt AS p ON p.produkt_id = a.produkt_id WHERE a.produkt_id = '" + id + "' AND a.gueltig_bis > now()";
			String produkt_query = "SELECT a.angebotspreis, a.gueltig_bis FROM angebot AS a LEFT JOIN produkt AS p ON p.produkt_id = a.produkt_id WHERE a.produkt_id = '" + id + "' AND a.gueltig_bis > now()";
			
			ResultSet resultset_count = DatenbankController.sendeSqlRequest(count_query);
			
			if (resultset_count!= null) {
				
				ResultSet resultset_produkt = DatenbankController.sendeSqlRequest(produkt_query);
				
				if(resultset_produkt.next()){
					this.produktModel.setPreisAngebotNetto(resultset_produkt.getDouble(1));
					this.produktModel.setGueltig_bis(resultset_produkt.getDate(2));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.produktModel.setPreisAngebotBrutto(runden(this.produktModel.getPreisAngebotNetto() * this.produktModel.getSteuerSatz() / 100 + this.produktModel.getPreisAngebotNetto(), 2));
		
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
		String produkt_query = "";
	
		String kategorie_query = "SELECT eltern_id FROM kategorie WHERE kategorie_id = '" + kategorie_id + "'";
		
		try {
			
			ResultSet produkt_resultset = DatenbankController.sendeSqlRequest(kategorie_query); 

			while(produkt_resultset.next()){
				
				kategoriesuche_eltern_id = produkt_resultset.getInt(1);
								
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		 
		  			String count_query = "SELECT COUNT(a.produkt_id) FROM angebot AS a LEFT JOIN produkt AS p ON p.produkt_id = a.produkt_id WHERE a.produkt_id = '" + id + "' AND a.gueltig_bis > now()";
			String produkt_query = "SELECT a.angebotspreis, a.gueltig_bis FROM angebot AS a LEFT JOIN produkt AS p ON p.produkt_id = a.produkt_id WHERE a.produkt_id = '" + id + "' AND a.gueltig_bis > now()";
			
			ResultSet resultset_count = DatenbankController.sendeSqlRequest(count_query);
			
			if (resultset_count!= null) {
				
				ResultSet resultset_produkt = DatenbankController.sendeSqlRequest(produkt_query);
				
				if(resultset_produkt.next()){
					this.produktModel.setPreisAngebotNetto(resultset_produkt.getDouble(1));
					this.produktModel.setGueltig_bis(resultset_produkt.getDate(2));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		  */

		
		
		
		
		
		System.out.println("+++Anfang Angebot+++");
		if(request.getParameter("angebote") != null && request.getParameter("angebote").equals("true")) {
				System.out.println("angebote werden gesucht");
				produkt_query = "SELECT produkt_id FROM angebot WHERE gueltig_bis > now()";
			
		} else {
			System.out.println("Er geht ins else");
			if(kategoriesuche_eltern_id == 0) {
				System.out.println("Er ist im alle kategorien");

				produkt_query = "SELECT p.produkt_id "
						+ "FROM produkt AS p "
						+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "WHERE pb.sprache_id = '" + this.sprache_id + "' "
						+ "AND  p.kategorie_id IN (SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + kategorie_id + "' OR (kategorie_id = '" + kategorie_id + "' AND eltern_id = 0)) "
						+ "ORDER BY " + session.getAttribute("sortierung_sortierspalte") + " " + session.getAttribute("sortierung_reihenfolge");
			} else {
				System.out.println("Er ist in bestimmtere kategorie");

				produkt_query = "SELECT p.produkt_id "
						+ "FROM produkt AS p "
						+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "WHERE pb.sprache_id = '" + this.sprache_id + "' "
						+ "AND  p.kategorie_id = '" + kategorie_id +"' "
						+ "ORDER BY " + session.getAttribute("sortierung_sortierspalte") + " " + session.getAttribute("sortierung_reihenfolge");
			}
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

	public void setSortierung(HttpServletRequest request) {
		HttpSession session = ((HttpServletRequest) request).getSession();
		System.out.println("existiert die Session?");

		if(request.getParameter("sn") != null) {
			System.out.println("----> Sortierung ist ungleich null - Request ist " + request.getParameter("sn") + " und Session ist " + (String)session.getAttribute("sortierung_sortierspalte_kuerzel"));
			//  Wenn sortierung_sortierspalte bereits auf demselben Wert steht, wechselt die Sortierreihenfolge
			if(request.getParameter("sn").equals((String)session.getAttribute("sortierung_sortierspalte_kuerzel"))) {
				System.out.println("gleicher Wert");
				if(session.getAttribute("sortierung_reihenfolge").equals("DESC")) {
					System.out.println("reihenfolge war gleich");
					session.setAttribute("sortierung_reihenfolge", "ASC");
				} else {
					System.out.println("reihenfolge war NICHT gleich");		
					session.setAttribute("sortierung_reihenfolge", "DESC");					
				}
				
			} else {
				switch (request.getParameter("sn")) {
		          case "pn":
		        	  session.setAttribute("sortierung_sortierspalte", "pb.produkt_name");
		        	  session.setAttribute("sortierung_sortierspalte_kuerzel", "pn");
		            break;
		          case "pp":
		        	  session.setAttribute("sortierung_sortierspalte", "p.produkt_preis");	
		        	  session.setAttribute("sortierung_sortierspalte_kuerzel", "pp");
		            break;
		          default:
		        	  session.setAttribute("sortierung_sortierspalte", "pb.produkt_name");
		        	  session.setAttribute("sortierung_sortierspalte_kuerzel", "pn");
		        	break;	
		        }
			}

		}

	}
	//TODO kommt das da wirklich hin?
	public static double runden(double wert, int stellen) {
		double gerundet = Math.round(wert * Math.pow(10d, stellen));
		return gerundet / Math.pow(10d, stellen);
	} 

}
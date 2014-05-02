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
	private String kategorie;
	
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
				this.produktModel.setPreisNetto(resultset.getDouble(7));
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
		
		this.produktModel.setPreisBrutto(this.produktModel.getPreisNetto() * this.produktModel.getSteuerSatz() / 100 + this.produktModel.getPreisNetto());
		this.produktModel.setSteuerBetrag(this.produktModel.getPreisBrutto() - this.produktModel.getPreisNetto());
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

	public ArrayList<ProduktModel> getProduktliste(String kategorie_id, HttpServletRequest request) {
		
		HttpSession session = ((HttpServletRequest) request).getSession(); 
		ArrayList<ProduktModel> produkte = new ArrayList<>();
		
		if(this.kategorie == null) {
			this.kategorie = "1";
		}
		System.out.println("Sessionwerte Sortierung");
		System.out.println("Reihenfolge: " + session.getAttribute("sortierung_reihenfolge"));
		System.out.println("Anzahl: " + session.getAttribute("sortierung_produktanzahl"));
		System.out.println("Limit: " + session.getAttribute("sortierung_limit_von"));

		String produkt_query = "SELECT p.produkt_id "
								+ "FROM produkt AS p "
								+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
								+ "WHERE pb.sprache_id = '" + this.sprache_id + "' "
								+ "AND  p.kategorie_id IN (SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + kategorie_id + "' OR (kategorie_id = '" + kategorie_id + "' AND eltern_id = 0)) "
								+ "ORDER BY " + session.getAttribute("sortierung_sortierspalte") + " " + session.getAttribute("sortierung_reihenfolge") + " "
								+ "LIMIT " + session.getAttribute("sortierung_limit_von") + "," + session.getAttribute("sortierung_produktanzahl");
				/*
		case report.type
        when 'P' then amount SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + this.kategorie + "' OR (kategorie_id = '" + this.kategorie + "' AND eltern_id = 0)
        when 'N' then -amount SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + this.kategorie + "' OR (kategorie_id = '" + this.kategorie + "' AND eltern_id = 0)
    end as amount
    
    

	

SELECT 
        id
        , IF(type = 'P', amount, amount * -1) as amount
FROM    report


CASE
WHEN (SELECT eltern_id FROM produkt WHERE produkt_id=2) > 0 THEN (SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + this.kategorie + "' OR (kategorie_id = '" + this.kategorie + "' AND eltern_id = 0))
ELSE (SELECT kategorie_id FROM kategorie WHERE kategorie_id = '" + this.kategorie + "')
END) as myorder
		
IF(eltern_id > 0, SELECT kategorie_id FROM kategorie WHERE (eltern_id = '" + this.kategorie + "' OR (kategorie_id = '" + this.kategorie + "' AND eltern_id = 0)), SELECT kategorie_id FROM kategorie WHERE kategorie_id = '" + this.kategorie + "')
		
CASE
    WHEN (SELECT eltern_id FROM kategorie WHERE kategorie_id = '" + kategorie_id + "') = 0 THEN SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + this.kategorie + "' OR (kategorie_id = '" + this.kategorie + "' AND eltern_id = 0)
    ELSE kategorie_id
END CASE


IF Bedingung THEN Anweisung(en)
[ELSEIF Bedingung THEN  � Anweisung(en)]
[ELSE Anweisung(en)]
END IF
*/

		
		if(kategorie_id == null) {
			kategorie_id = "1";
		}
		
		String produkt_query2 = "SELECT p.produkt_id "
				+ "FROM produkt AS p "
				+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
				+ "WHERE pb.sprache_id = '" + this.sprache_id + "' "
				+ "AND  p.kategorie_id IN (SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + kategorie_id + "' OR (kategorie_id = '" + kategorie_id + "' AND eltern_id = 0)) "
				+ "ORDER BY " + session.getAttribute("sortierung_sortierspalte") + " " + session.getAttribute("sortierung_reihenfolge") + " "
				+ "LIMIT " + session.getAttribute("sortierung_limit_von") + "," + session.getAttribute("sortierung_produktanzahl") + "";


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
		HttpSession session = ((HttpServletRequest) request).getSession();
		String parameter = request.getParameter("p_anzeige");

		if(session.getAttribute("sortierung_reihenfolge") == null) {
			session.setAttribute("sortierung_reihenfolge", "ASC");
			session.setAttribute("sortierung_produktanzahl", 3);
			session.setAttribute("sortierung_limit_von", 0);
			session.setAttribute("sortierung_sortierspalte", "pb.produkt_name");
      	    session.setAttribute("sortierung_sortierspalte_kuerzel", "pn");
		} 
		
		if(parameter != null) {
			
			String[] parameterAufteilung = parameter.split(",");

			//  Wenn sortierung_sortierspalte bereits auf demselben Wert steht, wechselt die Sortierreihenfolge
			if(parameterAufteilung[0].equals(session.getAttribute("sortierung_sortierspalte_kuerzel"))) {
				
				if(session.getAttribute("sortierung_reihenfolge") == "DESC") {
					session.setAttribute("sortierung_reihenfolge", "ASC");
				} else {
					session.setAttribute("sortierung_reihenfolge", "DESC");					
				}
				
			}
			
			switch (parameterAufteilung[0]) {
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
			
			if(parameterAufteilung[1] != session.getAttribute("sortierung_produktanzahl")) {
				session.setAttribute("sortierung_produktanzahl", parameterAufteilung[1]);
			}
			
			if(parameterAufteilung[2] != session.getAttribute("sortierung_limit_von")) {
				session.setAttribute("sortierung_limit_von", parameterAufteilung[2]);
			}
			
		}

	}

}
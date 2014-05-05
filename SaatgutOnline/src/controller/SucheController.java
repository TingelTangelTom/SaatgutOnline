package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import sun.swing.PrintingStatus;
import model.ProduktModel;

public class SucheController {
	
	private ProduktController produktController;
	private ProduktModel produktModel;
	private double steuersatz;
	private int sprache_id;
	private Pattern pattern;
	
	public SucheController(HttpServletRequest request) {
		super();
		
		HttpSession session = request.getSession();
		this.produktModel = new ProduktModel();
		this.sprache_id = (int)session.getAttribute("spracheId");
		this.produktController = new ProduktController(request);
	
	}
	
	public String getSuchkategorie(String suchparameter) {
		
		String[] gesuchteKategorien = {"kategorie:", "name:", "beschreibung:"};
		String gefundeneKategorie = null;

		String zwischenstring;

		if(!suchparameter.equals("")) {
			System.out.println("immer noch null");
			for (int i = 0; i < gesuchteKategorien.length; i++) {
				Pattern pattern = Pattern.compile("(?i)^" + gesuchteKategorien[i]);
				Matcher matcher = pattern.matcher(suchparameter);	
		        
				while (matcher.find()) {
		        	zwischenstring = suchparameter.substring(matcher.start(), matcher.end());
		        	gefundeneKategorie = zwischenstring.replace(":", "");
		        	System.out.println("Gefundene Kategorie: " + gefundeneKategorie);
		        }
					
			}

	        System.out.println("Neuer Suchstring: " + suchparameter);
		}
		
		return gefundeneKategorie;
	}
	
	public ArrayList<String> getSuchbegriffe(String suchparameter) {
		
		ArrayList<String> suchbegriffe = new ArrayList<>();
		String suchparameter_helfer = null;
		String zwischenstring;
		
		System.out.println("#####################");
		System.out.println("REGEX TEST");
		System.out.println("#####################");
		
		

		suchparameter = suchparameter.replace(this.getSuchkategorie(suchparameter) + ":", "");
		//String suchparameter = "Kategorie: +\"New York| 89418, USA\" 200,5,\"Nevada 89418, USA\" -Trachycarpus Fortunei Simon";

		/*
		 * Die eingegebene Zeichenkette in der Suchfunktion kontrolliert zuerst,
		 * ob in einer bestimmten Kategorie gesucht werden soll. Falls ja, wird 
		 * die Kategorie aus der Zeichenkette gelöscht
		 */
		
		System.out.println("Ist es NULL?: " + suchparameter.equals(""));
		System.out.println(suchparameter);

		
		/*
		 * In der neuen Zeichenkette wird nach Zeichenketten in Anführungszeichen gesucht.
		 * Eine gefundene Zeichenkette wird in einer ArrayList gespeichert und aus der 
		 * eigentlichen Zeichenkette entfernt.
		 */
		if(!suchparameter.equals("")) {
	
			Pattern pattern = Pattern.compile("[+-]?\"([^\"]*)\"");
			Matcher matcher = pattern.matcher(suchparameter);	
	        
			System.out.println("Gefundene Begriffe in Anführungszeichen:");
	        while (matcher.find()) {
	        	zwischenstring = suchparameter.substring(matcher.start(), matcher.end());
	        	suchparameter_helfer = zwischenstring.replace(zwischenstring, "");
	        	zwischenstring = zwischenstring.replace("\"", "");
	        	suchbegriffe.add(zwischenstring);
	        	System.out.println(zwischenstring);
	        }
	        
			if(suchparameter_helfer != null) {
				suchparameter = suchparameter_helfer;
			}
	        
		}
		        
        System.out.println("Neuer Suchstring: " + suchparameter_helfer);
        
        //TODO Text einfügen
        /*
         * 
         */
        
		if(!suchparameter.equals("")) {
	
	        pattern = Pattern.compile("(([+-][a-zA-Z0-9]{3,})|([+-][a-zA-Z0-9]{3,}))");
	        Matcher matcher = pattern.matcher(suchparameter);	
			System.out.println("Gefundene Begriffe mit Minus oder Plus:");
	        while (matcher.find()) {
	        	zwischenstring = suchparameter.substring(matcher.start(), matcher.end());
	        	System.out.println(zwischenstring);
	        	suchparameter_helfer = zwischenstring.replace(zwischenstring, "");
	        	suchbegriffe.add(zwischenstring);
	
	        }
	        
			if(suchparameter_helfer != null) {
				suchparameter = suchparameter_helfer;
			}

	        System.out.println("Neuer Suchstring: " + suchparameter);
	        
		}
		
		if(!suchparameter.equals("")) {
			
	        //TODO Text einfügen
	        pattern = Pattern.compile("[a-zA-Z0-9]{3,}");
	        Matcher matcher = pattern.matcher(suchparameter);	
			System.out.println("Gefundene Wörter oder Zahlen:");
	        while (matcher.find()) {
	        	zwischenstring = suchparameter.substring(matcher.start(), matcher.end());
	        	System.out.println(zwischenstring);
	        	suchparameter_helfer = zwischenstring.replace(zwischenstring, "");
	        	suchbegriffe.add(zwischenstring);
	        	
	        }

		}
        
        suchparameter = suchparameter_helfer;
        System.out.println("Neuer Suchstring: " + suchparameter);

		System.out.println("#####################");

		return suchbegriffe;
	}
	
	public ArrayList<ProduktModel> getProduktliste(HttpServletRequest request, String suchparameter) {
		
		System.out.println("#######################");
		System.out.println("BEGINN ERWEITERTE SUCHE");
		System.out.println("#######################");
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		// Benötigte Parameter aus dem Request holen
		int kategorie = Integer.parseInt(request.getParameter("kategorie"));
		String name = request.getParameter("name");
		String beschreibung = request.getParameter("beschreibung");
		String produktnummer = request.getParameter("produktnummer");
		ArrayList<Integer> produkte_ids = new ArrayList<>();
		int preis_von = 0;
		int preis_bis = 0;
		
		if(!request.getParameter("preis_von").equals("")) {
			preis_von = Integer.parseInt(request.getParameter("preis_von"));
		} else {
			preis_von = 0;
			System.out.println("Preis auf 0 gesetzt: " + preis_von);
		}
		
		if(!request.getParameter("preis_bis").equals("")) {
			preis_bis = Integer.parseInt(request.getParameter("preis_bis"));
		}

		String produkt_query;
		ArrayList<ProduktModel> produkte = new ArrayList<>();
		
		produkt_query = "SELECT p.produkt_id "
						+ "FROM produkt AS p "
						+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "INNER JOIN kategorie_beschreibung AS kb ON p.kategorie_id = kb.kategorie_id "
						+ "WHERE pb.sprache_id = '" + this.sprache_id + "' ";
						if(kategorie > 0) {
							produkt_query += "AND p.kategorie_id  IN (SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + kategorie + "' OR (kategorie_id = '" + kategorie + "' AND eltern_id = 0)) ";
						}						
						if(!name.equals("")) {
							produkt_query += "AND MATCH (pb.produkt_name) AGAINST ('" + name + "') ";
						}
						if(!beschreibung.equals("")) {
							produkt_query += "AND MATCH (pb.produkt_beschreibung) AGAINST ('" + beschreibung + "') ";
						}
						if(!produktnummer.equals("")) {
							produkt_query += "AND p.produkt_produktnummer = '" + produktnummer + "' ";
						}
						if(preis_von >= 0 && preis_bis > 0 && preis_von < preis_bis) {
							produkt_query += "AND p.produkt_preis BETWEEN " + preis_von + " AND " + preis_bis;
						}
		produkt_query += "ORDER BY " + session.getAttribute("sortierung_sortierspalte") + " " + session.getAttribute("sortierung_reihenfolge") + " "
						+ "LIMIT " + session.getAttribute("sortierung_limit_von") + "," + session.getAttribute("sortierung_produktanzahl") + "";


		System.out.println(produkt_query);

		try {
			
			ResultSet produkt_resultset = DatenbankController.sendeSqlRequest(produkt_query); 

			while(produkt_resultset.next()){
				
				produkte_ids.add(produkt_resultset.getInt(1));
							
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	    HashSet<Integer> hashSet = new HashSet<Integer>(produkte_ids);
	    produkte_ids.clear();
	    produkte_ids.addAll(hashSet);
		
		for(Integer id : produkte_ids) {
			produkte.add(this.produktController.getProdukt(id));
		}
		
		
		//produkte.add(this.produktController.getProdukt(produkt_resultset.getInt(1)));
	

/*
		if(suchkategorie != null && suchkategorie.equals("kategorie")) {
			//String kategorie_id = "SELECT kategorie" 
		}
		if(suchkategorie != null) {
			if(suchkategorie.equals("name")) {
				produkt_query += "pb.produkt_name";
			} else if (suchkategorie.equals("kategorie")) {
				produkt_query += "kb.kategorie_name";
			} else if (suchkategorie.equals("beschreibung")) {
				produkt_query += "pb.produkt_beschreibung";
			}
			
			produkt_query += ") AGAINST ('";
		
			for(String suchbegriff : suchbegriffe) {
				produkt_query += suchbegriff + " ";
			}
				
			produkt_query += "')";
		} else {
			produkt_query += "kb.kategorie_name) AGAINST ('";
			
			for(String suchbegriff : suchbegriffe) {
				produkt_query += suchbegriff + " ";
			}
			
			produkt_query += "') OR MATCH (pb.produkt_name, pb.produkt_beschreibung, pb.produkt_suchbegriffe) AGAINST ('";
			
			for(String suchbegriff : suchbegriffe) {
				produkt_query += suchbegriff + " ";
			}
			
			produkt_query += "')";
			
		}
		
		
		ArrayList<ProduktModel> produkte = new ArrayList<>();
		
		
		String suchkategorie = this.getSuchkategorie(suchparameter);
		
		HashSet<Integer> produkte_ids = new HashSet<>();
		
		ArrayList<String> mussProdukte;
		ArrayList<String> kannProdukte;
		ArrayList<String> suchbegriffe = this.getSuchbegriffe(suchparameter);
		String produkt_query;
		
		System.out.println("Größe des Arrays: " + suchbegriffe.size());
		System.out.println("+++ Suchkategorie +++");
		System.out.println(suchkategorie);
		System.out.println("+++ Suchbegriffe +++");
		
		produkt_query = "SELECT p.produkt_id "
						+ "FROM produkt AS p "
						+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id INNER JOIN kategorie_beschreibung AS kb ON p.kategorie_id = kb.kategorie_id "
						+ "WHERE pb.sprache_id = '" + this.sprache_id + "' AND MATCH (";
		
		if(suchkategorie != null && suchkategorie.equals("kategorie")) {
			//String kategorie_id = "SELECT kategorie" 
		}
		if(suchkategorie != null) {
			if(suchkategorie.equals("name")) {
				produkt_query += "pb.produkt_name";
			} else if (suchkategorie.equals("kategorie")) {
				produkt_query += "kb.kategorie_name";
			} else if (suchkategorie.equals("beschreibung")) {
				produkt_query += "pb.produkt_beschreibung";
			}
			
			produkt_query += ") AGAINST ('";
		
			for(String suchbegriff : suchbegriffe) {
				produkt_query += suchbegriff + " ";
			}
				
			produkt_query += "')";
		} else {
			produkt_query += "kb.kategorie_name) AGAINST ('";
			
			for(String suchbegriff : suchbegriffe) {
				produkt_query += suchbegriff + " ";
			}
			
			produkt_query += "') OR MATCH (pb.produkt_name, pb.produkt_beschreibung, pb.produkt_suchbegriffe) AGAINST ('";
			
			for(String suchbegriff : suchbegriffe) {
				produkt_query += suchbegriff + " ";
			}
			
			produkt_query += "')";
			
		}
		
		//produkt_query += "p.produkt_bestellnummer";
		
		
		/*
		for(String suchbegriff : suchbegriffe) {
			produkt_query += suchbegriff + " ";
		}
			
		produkt_query += "') OR MATCH (pb.produkt_name, pb.produkt_beschreibung, pb.produkt_suchbegriffe) AGAINST ('";
		
				for(String suchbegriff : suchbegriffe) {
			produkt_query += suchbegriff + " ";
		}	
		
		produkt_query += "')";

		System.out.println("QUERY: " + produkt_query);
		
		try {
			
			ResultSet produkt_resultset = DatenbankController.sendeSqlRequest(produkt_query); 

			while(produkt_resultset.next()){
				
				produkte_ids.add(produkt_resultset.getInt(1));
							
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(Integer id : produkte_ids) {
			produkte.add(this.produktController.getProdukt(id));
		}
		//produkte.add(this.produktController.getProdukt(produkt_resultset.getInt(1)));
	
		    HashSet<ProduktModel> hashSet = new HashSet<ProduktModel>(produkte);
		    produkte.clear();
		    produkte.addAll(hashSet);
	
		
		System.out.println("+++Suche: " + produkte.size() + " gefundene Produkte");
		
	
		SELECT * FROM webad_07 WHERE MATCH (eventTitel,beschreibung,detail_ort,veranstalter) AGAINST ('hallo') AND eventRubrik='Ausgang' AND ort='8000 Zürich'
		
		WHERE MATCH (title, body, url) AGAINST ('Berlin London Paris');
		
		if(suchkategorie != null) {
			produkt_query = "SELECT p.produkt_id "
					+ "FROM produkt AS p "
					+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id"
					+ "WHERE p.produkt_bestellnummer, p.produkt_preis, pb.produkt_name, pb.produkt_beschreibung LIKE
					
		//TODO raus
		for(String suchbegriff : suchbegriffe) {
			if(suchbegriff.contains("-") | suchbegriff.contains("+")) {
				produkt_query += suchbegriff + ",";
				suchbegriffe.
			}
		}
		
		System.out.println("+++muss+++");
		for(String suchbegriff : mussProdukte) {
			System.out.println(suchbegriff);
		}
		
		System.out.println("+++kann+++");
		for(String suchbegriff : kannProdukte) {
			System.out.println(suchbegriff);
		}
		
		//String suche_query = " p.kategorie_id in()
		
		if(suchkategorie != null) {
			produkt_query = "SELECT p.produkt_id "
					+ "FROM produkt AS p "
					+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
					+ "WHERE p.produkt_bestellnummer, p.produkt_preis, pb.produkt_name, pb.produkt_beschreibung IN ("
					+ "AND  p.kategorie_id IN (SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + kategorie_id + "' OR (kategorie_id = '" + kategorie_id + "' AND eltern_id = 0)) "
					+ "ORDER BY " + session.getAttribute("sortierung_sortierspalte") + " " + session.getAttribute("sortierung_reihenfolge") + " "
					+ "LIMIT " + session.getAttribute("sortierung_limit_von") + "," + session.getAttribute("sortierung_produktanzahl") + "";
		} else {
			produkt_query = "SELECT p.produkt_id "
					+ "FROM produkt AS p "
					+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
					+ "WHERE pb.sprache_id = '" + this.sprache_id + "' "
					+ "AND  p.kategorie_id = '" + kategorie_id +"'"
					+ "ORDER BY " + session.getAttribute("sortierung_sortierspalte") + " " + session.getAttribute("sortierung_reihenfolge") + " "
					+ "LIMIT " + session.getAttribute("sortierung_limit_von") + "," + session.getAttribute("sortierung_produktanzahl") + "";
		}
	*/
		return produkte;
	}

}
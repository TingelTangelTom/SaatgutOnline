package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ProduktModel;

public class SucheController {
	
	private ProduktModel produktModel;
	private double steuersatz;
	private int sprache_id;
	private Pattern pattern;
	
	public SucheController(HttpServletRequest request) {
		super();
		
		HttpSession session = request.getSession();
		this.produktModel = new ProduktModel();
		this.sprache_id = (int)session.getAttribute("spracheId");
		
		
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
			/*
			if(suchparameter_helfer != null) {
				suchparameter = suchparameter_helfer;
			}
			*/

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
	        	zwischenstring = zwischenstring.replace(zwischenstring, "");
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
	        	zwischenstring = zwischenstring.replace(zwischenstring, "");
	        	suchbegriffe.add(zwischenstring);
	        	
	        }
	        
			if(suchparameter_helfer != null) {
				suchparameter = suchparameter_helfer;
			}

		}
        
        suchparameter = suchparameter_helfer;
        System.out.println("Neuer Suchstring: " + suchparameter);

		System.out.println("#####################");

		return suchbegriffe;
	}
	
	public ArrayList<ProduktModel> getProduktliste(HttpServletRequest request,String suchparameter) {
		
		String suchkategorie = this.getSuchkategorie(suchparameter);
		ArrayList<String> suchbegriffe = this.getSuchbegriffe(suchparameter);
		return null;
	}

}
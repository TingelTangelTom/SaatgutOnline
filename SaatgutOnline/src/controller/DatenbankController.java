package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatenbankController {
	// Connection
	public 	static Connection verbindung = null;
	// Hostname
	private static String 	  dbHost 	 = "localhost";
	// Port (Standard: 3306)
	private static String     dbPort 	 = "3306";
	// Datenbank - Name
	private static String 	  datenbank  = "saatgutonline";
	// Datenbank - Benutzer
	private static String 	  dbBenutzer = "root";
	// Datenbank - Passwort
	private static String 	  dbPasswort = "root";
	 
	public DatenbankController() {

		try {
			// Datenbanktreiber laden.
			Class.forName("com.mysql.jdbc.Driver");
		    // Verbindung zur Datenbank 'database' herstellen.
			verbindung = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
		           + dbPort + "/" + datenbank + "?" + "user=" + dbBenutzer + "&"
		           + "password=" + dbPasswort);
		    System.out.println("Verbindung zur Datenbank wurde hergestellt.");
		} catch(SQLException e) { 
			System.out.println("Verbindung zur Datenbank konnte nicht hergestellt werden.");
	    	System.out.println(e.getMessage());
	    	System.out.println(e.getSQLState());
	    	System.out.println(e.getErrorCode());
	    	
	    } catch(Exception e) {  
	    	System.out.println("Verbindung zur Datenbank konnte nicht hergestellt werden.");
	        System.out.println(e.getClass().toString() +" / "+ e.getMessage());
	    }
	}
	  
		// Wenn Verbindung getrennt ist, neue Verbindung aufbauen
		static Connection getVerbindung() {
		    if(verbindung == null)
		      new DatenbankController();
		    return verbindung;
		}
}
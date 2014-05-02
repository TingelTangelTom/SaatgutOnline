package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatenbankController {
	public static Connection verbindung;
	private static String dbHost;
	private static String dbPort;
	private static String dbName;
	private static String dbBenutzer;
	private static String dbPasswort;
	 
	public DatenbankController() {
	}
	
	public static ResultSet sendeSqlRequest(String query) {
		kontrolliereVerbindung();
		try {
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			return resultset;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Connection sqlVerbindungAufbauen () {
		try {
			// Datenbanktreiber laden.
			Class.forName("com.mysql.jdbc.Driver");	// TODO nur 1x ausführen???
		    // Verbindung zur Datenbank herstellen.
			verbindung = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
		           + dbPort + "/" + dbName + "?" + "user=" + dbBenutzer + "&"
		           + "password=" + dbPasswort);
		    System.out.println("Verbindung zur Datenbank wurde hergestellt.");
		    return verbindung;
		} catch(SQLException e) { 
			System.out.println("Verbindung zur Datenbank konnte nicht hergestellt werden.");
	    	System.out.println(e.getMessage());
	    	System.out.println(e.getSQLState());
	    	System.out.println(e.getErrorCode());
	    	return null;
	    } catch(Exception e) {  
	    	System.out.println("Verbindung zur Datenbank konnte nicht hergestellt werden.");
	        System.out.println(e.getClass().toString() +" / "+ e.getMessage());
	        return null;
	    }
	}
	  
	// Wenn Verbindung getrennt ist, neue Verbindung aufbauen
	private static void kontrolliereVerbindung() {
	    if(verbindung == null) {
	    	verbindung = sqlVerbindungAufbauen();
	    }
	}
	
	public static void setDbHost(String dbHost) {
		DatenbankController.dbHost = dbHost;
	}
	public static void setDbPort(String dbPort) {
		DatenbankController.dbPort = dbPort;
	}
	public static void setDbName(String dbName) {
		DatenbankController.dbName = dbName;
	}
	public static String getDbName() {
		return DatenbankController.dbName;
	}
	public static void setDbBenutzer(String dbBenutzer) {
		DatenbankController.dbBenutzer = dbBenutzer;
	}
	public static void setDbPasswort(String dbPasswort) {
		DatenbankController.dbPasswort = dbPasswort;
	}
}
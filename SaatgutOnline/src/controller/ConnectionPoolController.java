package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPoolController {
	
	private int dbMaximalePoolgroesse;
	private String dbHost;
	private String dbPort;
	private String dbName;
	private String dbBenutzer;
	private String dbPasswort;
	private Vector<Connection> connectionPool = new Vector<Connection>();
	
	private ConnectionPoolController() {
		ladeKonfiguration();
		initialisiereConnectionPool();
	}
	
	private void ladeKonfiguration() {
		this.dbBenutzer = KonfigurationController.getDbBenutzer();
		this.dbHost = KonfigurationController.getDbHost();
		this.dbMaximalePoolgroesse = KonfigurationController.getDbMaximalePoolgroesse();
		this.dbName = KonfigurationController.getDbName();
		this.dbPasswort = KonfigurationController.getDbPasswort();
		this.dbPort = KonfigurationController.getDbPort();		
		
	}

	private static class ConnectionPoolControllerHolder { 
		private static final ConnectionPoolController INSTANCE = new ConnectionPoolController();
	}
 
	public static ConnectionPoolController getInstance() {
		return ConnectionPoolControllerHolder.INSTANCE;
	}
	
	private void initialisiereConnectionPool()
    {
		while(!pruefeObConnectionPoolVoll())
        {
            // Connection Pool ist nicht voll. Weitere Verbindungen hinzufügen, bis der Pool voll ist
            connectionPool.addElement(this.sqlVerbindungAufbauen());
        }
    }

	private synchronized boolean pruefeObConnectionPoolVoll()
	{
		// Poolgroesse pruefen
		if(this.connectionPool.size() < dbMaximalePoolgroesse)
		{
			return false;
		}
		return true;
	}
	
	public Connection sqlVerbindungAufbauen () {
		try {
			// ggfs. Datenbanktreiber laden
			Class.forName("com.mysql.jdbc.Driver");
		    // Verbindung zur Datenbank herstellen
			Connection verbindung = DriverManager.getConnection("jdbc:mysql://" + this.dbHost + ":"
		           + this.dbPort + "/" + this.dbName + "?" + "user=" + this.dbBenutzer + "&"
		           + "password=" + this.dbPasswort);
//		    System.out.println("Verbindung zur Datenbank wurde hergestellt.");
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
	
    public synchronized Connection getVerbindungAusPool()
    {
        Connection verbindung = null;

        // Sind noch freie Verbindungen im Pool? Falls ja, connection aus dem Pool entnehmen
        if(this.connectionPool.size() > 0)
        {
            verbindung = (Connection) connectionPool.firstElement();
            this.connectionPool.removeElementAt(0);
        }
        // Verbindung aushändigen
        return verbindung;
        
        // TODO else: sinnvolle exception werfen!
    }

    public synchronized void verschiebeVerbindungInDenPool(Connection verbindung)
    {
        // Vom Client zurück in den Pool
        this.connectionPool.addElement(verbindung);
    }
	
	public String getDbName() {
		return this.dbName;
	}
	
}

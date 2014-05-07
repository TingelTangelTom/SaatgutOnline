package controller;

/**
 * <p>
 * Die Singleton-Klasse <code>ConnectionPoolController</code> erzeugt einen Pool von SQL-Verbindungen, die ueber
 * die Methode <code>getVerbindungAusPool</code> abgerufen werden koennen. Mit der Methode
 * <code>verschiebeVerbindungInDenPool</code> koennen Verbindungen zurueck in den Pool gelegt werden und stehen
 * wieder zur Verfuegung. Die Anzahl an Verbindungen werden ueber den <code>KonfigurationController</code>
 * <code>dbMaximalePoolgroesse</code> ausgelesen und ist variabel. Die Klasse sollte moeglichst frueh in den
 * Startprozess des Servers eingebunden werden, damit der Pool bei aufruf zur Verfügung steht und nicht erst
 * aufgebaut werden muss.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see KonfigurationController
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPoolController
{
	private int dbMaximalePoolgroesse;
	private String dbHost;
	private String dbPort;
	private String dbName;
	private String dbBenutzer;
	private String dbPasswort;
	private Vector<Connection> connectionPool = new Vector<Connection>();

	private ConnectionPoolController()
	{
		ladeKonfiguration();
		initialisiereConnectionPool();
	}

	private void ladeKonfiguration()
	{
		this.dbBenutzer = KonfigurationController.getDbBenutzer();
		this.dbHost = KonfigurationController.getDbHost();
		this.dbMaximalePoolgroesse = KonfigurationController.getDbMaximalePoolgroesse();
		this.dbName = KonfigurationController.getDbName();
		this.dbPasswort = KonfigurationController.getDbPasswort();
		this.dbPort = KonfigurationController.getDbPort();
	}

	private static class ConnectionPoolControllerHolder
	{
		private static final ConnectionPoolController INSTANCE = new ConnectionPoolController();
	}
	/**
	 * Dient der Instantiierung der Klasse <code>ConnectionPoolController</code>
	 * @return INSTANCE
	 */
	public static ConnectionPoolController getInstance()
	{
		return ConnectionPoolControllerHolder.INSTANCE;
	}

	private void initialisiereConnectionPool()
	{
		while (!pruefeObConnectionPoolVoll())
		{
			// Connection Pool ist nicht voll. Weitere Verbindungen hinzufuegen, bis der Pool voll ist
			connectionPool.addElement(this.sqlVerbindungAufbauen());
		}
	}

	private boolean pruefeObConnectionPoolVoll()
	{
		// Poolgroesse pruefen
		if (this.connectionPool.size() < dbMaximalePoolgroesse)
		{
			return false;
		}
		return true;
	}

	/**
	 * Neue SQL-Verbindung erstellen
	 * 
	 * @return Connection
	 */
	public Connection sqlVerbindungAufbauen()
	{
		try
		{
			// ggfs. Datenbanktreiber laden
			Class.forName("com.mysql.jdbc.Driver");
			// Verbindung zur Datenbank herstellen
			Connection verbindung = DriverManager.getConnection("jdbc:mysql://" + this.dbHost + ":" + this.dbPort
					+ "/" + this.dbName + "?" + "user=" + this.dbBenutzer + "&" + "password=" + this.dbPasswort);
			return verbindung;
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
			System.out.println(e.getErrorCode());
			return null;
		}
		catch (Exception e)
		{
			System.out.println(e.getClass().toString() + " / " + e.getMessage());
			return null;
		}
	}

	/**
	 * SQL-Verbindung aus dem Pool entnehmen
	 * 
	 * @return Connection
	 */
	public Connection getVerbindungAusPool()
	{
		Connection verbindung = null;
		// Sind noch freie Verbindungen im Pool? Falls ja, connection aus dem Pool entnehmen
		if (this.connectionPool.size() > 0)
		{
			verbindung = (Connection) connectionPool.firstElement();
			this.connectionPool.removeElementAt(0);
		}
		// Verbindung aushaendigen
		return verbindung;
	}

	/**
	 * SQL-Verbindung in den Pool zurueckgeben
	 * 
	 * @param Connection
	 */
	public void verschiebeVerbindungInDenPool(Connection verbindung)
	{
		// Vom Client zurück in den Pool
		this.connectionPool.addElement(verbindung);
	}

	public String getDbName()
	{
		return this.dbName;
	}
}

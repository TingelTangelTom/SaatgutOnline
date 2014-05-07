package controller;

/**
 * <p>
 * Die Klasse <code>DatenbankController</code> stellt die statische Datenbankmethode
 * <code>sendeSqlRequest()<code> zur Verfuegung.
 * Dabei werden Verbindungen aus dem 
 * <code>ConnectionPoolController</code> verwendet.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see ConnectionPoolController
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatenbankController
{
	public DatenbankController()
	{
	}

	/**
	 * Statische Methode zur Datenbankabfrage. Es wird eine Verbindung aus dem
	 * <code>ConnectionPoolController</code> verwendet. </p>
	 * 
	 * @param String
	 * @return resultSet
	 * @see ConnectionPoolController
	 */
	public static ResultSet sendeSqlRequest(String query)
	{
		ConnectionPoolController connectionPoolController = ConnectionPoolController.getInstance();
		Connection verbindung = connectionPoolController.getVerbindungAusPool();
		try
		{
			Statement statement = verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			connectionPoolController.verschiebeVerbindungInDenPool(verbindung);
			return resultset;
		}
		catch (SQLException e)
		{
			return null;
		}
	}
}
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * <p>
 * Die Klasse <code>PasswortController</code> stellt eine Schnittstelle von und zu der Datenbank bereit.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see https://crackstation.net/hashing-security.htm
 */
public class PasswortController
{
	/**
	 * Laedt ein zu einer Kunden-ID passendes Passwort-Hash aus der Datenbank
	 * 
	 * @param int kundeId
	 * @return String
	 */
	public static String ladePasswortHashAusDb(int kundeId)
	{
		String query = "SELECT * FROM " + KonfigurationController.getDbName() + ".passwort WHERE kunde_id = "
				+ kundeId;
		ResultSet result = DatenbankController.sendeSqlRequest(query);
		String passwortHash = null;
		try
		{
			while (result.next())
			{
				passwortHash = (result.getString("passwort_hash"));
			}
			return passwortHash;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Speichert einen Passwort-Hash samt Kunden-ID in die Datenbank
	 * 
	 * @param int kundeId
	 * @param String
	 *            passwortHash
	 */
	public static synchronized void speicherePasswortHashInDb(String passwortHash, int kundeId)
	{
		Connection verbindung = ConnectionPoolController.getInstance().getVerbindungAusPool();
		PreparedStatement prepSql;
		try
		{
			prepSql = verbindung.prepareStatement("INSERT INTO " + KonfigurationController.getDbName()
					+ ".passwort (kunde_id,passwort_hash)" + " VALUES (" + "?,?" + ")");
			prepSql.setInt(1, kundeId);
			prepSql.setString(2, passwortHash);
			prepSql.executeUpdate();
			ConnectionPoolController.getInstance().verschiebeVerbindungInDenPool(verbindung);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
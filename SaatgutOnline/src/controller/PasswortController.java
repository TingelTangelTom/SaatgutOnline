package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswortController {
	
	public static String ladePasswortHashAusDb (int kundeId) {

		String query = "SELECT * FROM " + KonfigurationController.getDbName()
        + ".passwort WHERE kunde_id = " + kundeId;
		ResultSet result = DatenbankController.sendeSqlRequest(query);
		String passwortHash = null;
		
		try {
			while(result.next()) {
				passwortHash = (result.getString("passwort_hash"));
			}
		return passwortHash;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static synchronized void speicherePasswortHashInDb (String passwortHash, int kundeId) {
		 
		Connection verbindung = ConnectionPoolController.getInstance().getVerbindungAusPool();
         PreparedStatement prepSql;
         try {
			prepSql = verbindung.prepareStatement(
			         "INSERT INTO " + KonfigurationController.getDbName()
			         + ".passwort (kunde_id,passwort_hash)"
			         + " VALUES ("
			         + "?,?"
			         + ")");
         prepSql.setInt(1, kundeId);
         prepSql.setString(2, passwortHash);
         
         prepSql.executeUpdate();
         
         ConnectionPoolController.getInstance().verschiebeVerbindungInDenPool(verbindung);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
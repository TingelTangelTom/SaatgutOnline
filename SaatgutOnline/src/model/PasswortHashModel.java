package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.ConnectionPoolController;
import controller.DatenbankController;
import controller.KonfigurationController;

public class PasswortHashModel {
	
	private String passwortHash;
	
	public PasswortHashModel() {
	}
	
	public PasswortHashModel ladePasswortHashAusDb (int kundeId) {

		PasswortHashModel passwort = new PasswortHashModel ();
		
		String query = "SELECT * FROM " + KonfigurationController.getDbName()
        + ".passwort WHERE kunden_id = " + kundeId;
		ResultSet result = DatenbankController.sendeSqlRequest(query);
		
		try {
		passwort.passwortHash=(result.getString("passwort_hash"));
		
		return passwort;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public synchronized void speicherePasswortHashInDb () {
		 
		Connection verbindung = ConnectionPoolController.getInstance().getVerbindungAusPool();

         PreparedStatement prepSql;

         try {
			prepSql = verbindung.prepareStatement(
			         "INSERT INTO " + KonfigurationController.getDbName()
			         + ".passwort ("
			         + ")"
			         + " VALUES ("
			         + "?"
			         + ")");

         prepSql.setString(1, getPasswortHash());
         
         prepSql.executeUpdate();
         
         ConnectionPoolController.getInstance().verschiebeVerbindungInDenPool(verbindung);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPasswortHash() {
		return this.passwortHash;
	}
	public void setPasswortHash (String passwortHash) {
		this.passwortHash = passwortHash;
	}
	
}

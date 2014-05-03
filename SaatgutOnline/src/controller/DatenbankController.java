package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatenbankController {
	
	public DatenbankController() {
	}
	
	public static ResultSet sendeSqlRequest(String query) {
		ConnectionPoolController connectionPoolController =
				ConnectionPoolController.getInstance();
		Connection verbindung = connectionPoolController.getVerbindungAusPool();
		try {
			Statement statement = verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			connectionPoolController.verschiebeVerbindungInDenPool(verbindung);
			return resultset;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
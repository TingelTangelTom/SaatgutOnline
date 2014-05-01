package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.DatenschutzView;

public class DatenschutzController {
	
	private String datenschutzText;
	
	public DatenschutzController(HttpServletRequest request, HttpServletResponse response) {

		// Datenbankabfrage : Aktuellsten Datenschutz ausgeben
		try {
			String query = "SELECT datenschutz_txt FROM datenschutz ORDER BY datenschutz_datum_hinzugefuegt DESC LIMIT 1";
			//  

			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			if (resultset.next()) {
				datenschutzText = resultset.getString(1);
				 System.out.println(datenschutzText);
				 new DatenschutzView(request, response, datenschutzText);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.AGBView;

public class AGBController {

	private String agbText;

	public AGBController(HttpServletRequest request, HttpServletResponse response) {

		// Datenbankabfrage : Aktuellste AGB ausgeben
		try {
			String query = "SELECT agb_txt FROM agb ORDER BY agb_datum_hinzugefuegt DESC LIMIT 1";

			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			if (resultset.next()) {
				agbText = resultset.getString(1);
				 System.out.println(agbText);
				 new AGBView(request, response, agbText);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.AGBView;

/**
 * Diese Klasse liest die aktuellsten AGB aus der Datenbank aus.
 * 
 * @author Anja
 *
 */
public class AGBController {

	/**
	 * Variablen zum zwischenspeichern des Datenbankinhaltes.
	 */
	private String agbText;

	/**
	 * Konstruktor für den AGBController.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws SQLException
	 * 
	 * @author Anja
	 */
	public AGBController(HttpServletRequest request, HttpServletResponse response) {

		// Datenbankabfrage : Aktuellste AGB ausgeben
		try {
			String query = "SELECT agb_txt FROM agb ORDER BY agb_datum_hinzugefuegt DESC LIMIT 1";

			ResultSet resultSet = DatenbankController.sendeSqlRequest(query);
			if (resultSet.next()) {
				agbText = resultSet.getString(1);
				 System.out.println(agbText);
				 new AGBView(request, response, agbText);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

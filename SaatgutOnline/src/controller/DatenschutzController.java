package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.DatenschutzView;

/**
 * Diese Klasse liest die aktuellste Datenschutzerklärung aus der Datenbank aus.
 * 
 * @author Anja
 *
 */
public class DatenschutzController {
	
	/**
	 * Variablen zum zwischenspeichern des Datenbankinhaltes
	 */
	private String datenschutzText;
	
	/**
	 * Konstruktor für den DatenschutzController.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws SQLException
	 * 
	 * @author Anja
	 */
	public DatenschutzController(HttpServletRequest request, HttpServletResponse response) {

		// Datenbankabfrage : Aktuellsten Datenschutz ausgeben
		try {
			String query = "SELECT datenschutz_txt FROM datenschutz "
					+ "ORDER BY datenschutz_datum_hinzugefuegt DESC LIMIT 1";

			ResultSet resultSet = DatenbankController.sendeSqlRequest(query);
			if (resultSet.next()) {
				datenschutzText = resultSet.getString(1);
				 System.out.println(datenschutzText);
				 new DatenschutzView(request, response, datenschutzText);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

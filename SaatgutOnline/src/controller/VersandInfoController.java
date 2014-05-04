package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.VersandInfoView;

public class VersandInfoController {
	
	/**
	 * Variablen zum zwischenspeichern des Datenbankinhaltes.
	 */
	private String versandInfoText;

	/**
	 * Konstruktor für den VersandInfoController.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws SQLException
	 * 
	 * @author Anja
	 */
	public VersandInfoController(HttpServletRequest request, HttpServletResponse response) {

		// Datenbankabfrage : Aktuellste AGB ausgeben
		try {
			String query = "SELECT versand_info_txt FROM versand_info ORDER BY versand_info_datum_hinzugefuegt DESC LIMIT 1";

			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			if (resultset.next()) {
				versandInfoText = resultset.getString(1);
				 System.out.println(versandInfoText);
				 new VersandInfoView(request, response, versandInfoText);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

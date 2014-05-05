package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.VersandInfoView;

/**
 * Diese Klasse liest die aktuellsten Versand- und Zahlungsbedingungen aus der Datenbank aus.
 * 
 * @author Anja Dietrich
 * 
 */
public class VersandInfoController {

	private String versandInfoText;

	/**
	 * Konstruktor für den VersandInfoController.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws SQLException
	 * 
	 */
	public VersandInfoController(HttpServletRequest request, HttpServletResponse response) {

		// Liest die (auf der Shopseite) eingestellte Sprache aus der Session
		HttpSession session = ((HttpServletRequest) request).getSession();
		int sprache = (int) session.getAttribute("spracheId");

		// Datenbankabfrage : Aktuellste AGB ausgeben
		String query = "SELECT versand_info_text FROM versand_info WHERE sprache_id=" + sprache
				+ " ORDER BY versand_info_datum_hinzugefuegt DESC LIMIT 1";

		try {
			ResultSet resultSet = DatenbankController.sendeSqlRequest(query);
			if (resultSet.next()) {
				versandInfoText = resultSet.getString(1);
				System.out.println(versandInfoText);
				new VersandInfoView(request, response, versandInfoText);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
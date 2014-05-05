package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.DatenschutzView;

/**
 * Diese Klasse liest die aktuellste Datenschutzerklärung aus der Datenbank aus.
 * 
 * @author Anja Dietrich
 * 
 */
public class DatenschutzController {

	private String datenschutzText;

	/**
	 * Konstruktor für den DatenschutzController.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws SQLException
	 * 
	 */
	public DatenschutzController(HttpServletRequest request, HttpServletResponse response) {

		// Liest die (auf der Shopseite) eingestellte Sprache aus der Session
		HttpSession session = ((HttpServletRequest) request).getSession();
		int sprache = (int) session.getAttribute("spracheId");

		// Datenbankabfrage : Aktuellsten Datenschutz ausgeben
		String query = "SELECT datenschutz_text FROM datenschutz WHERE sprache_id=" + sprache
				+ " ORDER BY datenschutz_datum_hinzugefuegt DESC LIMIT 1";

		try {
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
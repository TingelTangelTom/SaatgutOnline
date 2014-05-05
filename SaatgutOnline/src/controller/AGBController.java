package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.AGBView;

/**
 * Diese Klasse liest die aktuellsten AGB aus der Datenbank aus.
 * 
 * @author Anja
 *
 */
public class AGBController {

	private String agbText;

	/**
	 * Konstruktor für den AGBController.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws SQLException
	 * 
	 */
	public AGBController(HttpServletRequest request, HttpServletResponse response) {
		
		//Liest die (auf der Shopseite) eingestellte Sprache aus der Session
		HttpSession session = ((HttpServletRequest) request).getSession();
		int sprache = (int)session.getAttribute("spracheId");
		
		// Datenbankabfrage : Aktuellste AGB ausgeben
		String query = "SELECT agb_text FROM agb WHERE sprache_id=" + sprache + " ORDER BY agb_datum_hinzugefuegt DESC LIMIT 1";
		
		try {
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

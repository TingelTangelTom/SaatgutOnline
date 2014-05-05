package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.ImpressumView;

/**
 * Diese Klasse liest das Impressum aus der Datenbank aus.
 * 
 * @author Anja Dietrich
 * 
 */
public class ImpressumController {

	// Variablen zum zwischenspeichern des Datenbankinhaltes
	private String unternehmen_adresse;
	private String unternehmen_telefon;
	private String unternehmen_fax;
	private String unternehmen_email;
	private String unternehmen_geschaeftsfuehrung;
	private String registergericht;
	private String register_nr;
	private String umsatzsteuer_id;
	private String wirtschafts_id;
	private String impressum_copyright;

	/**
	 * Konstruktor für den ImpressumController.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws SQLException
	 * 
	 */
	public ImpressumController(HttpServletRequest request, HttpServletResponse response) {

		// Liest die (auf der Shopseite) eingestellte Sprache aus der Session
		HttpSession session = ((HttpServletRequest) request).getSession();
		int sprache = (int) session.getAttribute("spracheId");

		// Datenbankabfrage : Impressum ausgeben
		String query = "SELECT * FROM impressum WHERE sprache_id=" + sprache;

		try {
			ResultSet resultSet = DatenbankController.sendeSqlRequest(query);
			if (resultSet.next()) {
				unternehmen_adresse = resultSet.getString("unternehmen_adresse");
				unternehmen_telefon = resultSet.getString("unternehmen_telefon");
				unternehmen_fax = resultSet.getString("unternehmen_fax");
				unternehmen_email = resultSet.getString("unternehmen_email");
				unternehmen_geschaeftsfuehrung = resultSet.getString("unternehmen_geschaeftsfuehrung");
				registergericht = resultSet.getString("registergericht");
				register_nr = resultSet.getString("register_nr");
				umsatzsteuer_id = resultSet.getString("umsatzsteuer_id");
				wirtschafts_id = resultSet.getString("wirtschafts_id");
				impressum_copyright = resultSet.getString("impressum_copyright");

				new ImpressumView(request, response, unternehmen_adresse, unternehmen_telefon,
						unternehmen_fax, unternehmen_email, unternehmen_geschaeftsfuehrung, registergericht,
						register_nr, umsatzsteuer_id, wirtschafts_id, impressum_copyright);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
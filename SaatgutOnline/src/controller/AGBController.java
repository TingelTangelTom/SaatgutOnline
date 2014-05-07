package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.AGBView;

/**
 * <p>
 * Die Klasse <code>AGBController</code> liest die aktuellsten AGB aus der Datenbank aus.
 * </p>
 * 
 * @author Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class AGBController
{
	private String agbText;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>AGBController</code> liest die aktuell eingestellte Sprache aus der
	 * <code>HttpSession</code>,</br> und holt die jeweiligen AGB aus der Datenbank.
	 * </p>
	 * <p>
	 * Erzeugt ein neues <code>AGBView</code>Objekt
	 * </p>
	 * <p>
	 * Sendet die Abfrage an den <code>AGBView</code>
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see view.AGBView
	 */
	public AGBController(HttpServletRequest request, HttpServletResponse response)
	{
		// Liest die (auf der Shopseite) eingestellte Sprache aus der Session
		HttpSession session = ((HttpServletRequest) request).getSession();
		int sprache = (int) session.getAttribute("spracheId");
		String query = "SELECT agb_text FROM agb WHERE sprache_id=" + sprache
				+ " ORDER BY agb_datum_hinzugefuegt DESC LIMIT 1";
		try
		{
			ResultSet resultSet = DatenbankController.sendeSqlRequest(query);
			if (resultSet.next())
			{
				agbText = resultSet.getString(1);
				new AGBView(request, response, agbText);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
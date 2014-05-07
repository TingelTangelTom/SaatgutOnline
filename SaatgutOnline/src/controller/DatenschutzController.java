package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.DatenschutzView;

/**
 * <p>
 * Die Klasse <code>DatenschutzController</code> liest die aktuellste Datenschutzerklaerung</br> aus der Datenbank
 * aus.
 * </p>
 * 
 * @author Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class DatenschutzController
{
	private String datenschutzText;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>DatenschutzController</code> liest die aktuellste Datenschutzerklaerung aus der
	 * Datenbank und erzeugt ein neues <code>DatenschutzView</code>Objekt
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
	 * @see view.DatenschutzView
	 */
	public DatenschutzController(HttpServletRequest request, HttpServletResponse response)
	{
		// Liest die (auf der Shopseite) eingestellte Sprache aus der Session
		HttpSession session = ((HttpServletRequest) request).getSession();
		int sprache = (int) session.getAttribute("spracheId");
		String query = "SELECT datenschutz_text FROM datenschutz WHERE sprache_id=" + sprache
				+ " ORDER BY datenschutz_datum_hinzugefuegt DESC LIMIT 1";
		try
		{
			ResultSet resultSet = DatenbankController.sendeSqlRequest(query);
			if (resultSet.next())
			{
				datenschutzText = resultSet.getString(1);
				System.out.println(datenschutzText);
				new DatenschutzView(request, response, datenschutzText);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
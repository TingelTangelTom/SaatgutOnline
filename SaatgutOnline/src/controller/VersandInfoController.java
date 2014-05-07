package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.VersandInfoView;

/**
 * <p>
 * Die Klasse <code>VersandInfoController</code> liest die aktuellsten</br> Versand- und Zahlungsbedingungen aus
 * der Datenbank aus.</br>
 * 
 * @author Anja Dietrich
 * @version 1.0
 * @since 1.7.0_51
 */
public class VersandInfoController
{
	private String versandInfoText;

	/**
	 * Konstruktor für den VersandInfoController.
	 * <p>
	 * Der Konstruktor der Klasse <code>VersandInfoController</code> versendet die vom</br>
	 * <code>KontaktFormularController</code> ausgelesenen Daten als E-Mail und ruft den</br>
	 * <code>KontaktFormularView</code> auf.
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see java.sql.SQLException
	 * @see view.VersandInfoView
	 */
	public VersandInfoController(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = ((HttpServletRequest) request).getSession();
		int sprache = (int) session.getAttribute("spracheId");
		String query = "SELECT versand_info_text FROM versand_info WHERE sprache_id=" + sprache
				+ " ORDER BY versand_info_datum_hinzugefuegt DESC LIMIT 1";
		try
		{
			ResultSet resultSet = DatenbankController.sendeSqlRequest(query);
			if (resultSet.next())
			{
				versandInfoText = resultSet.getString(1);
				System.out.println(versandInfoText);
				new VersandInfoView(request, response, versandInfoText);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
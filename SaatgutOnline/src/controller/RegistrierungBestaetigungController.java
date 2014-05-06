package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.AnmeldungErfolgreichView;
import view.RegistrierungBestaetigungView;
import view.RegistrierungFehlerView;


// asdöflkjasdfkljasödlfkj
/**
 * <p>Die Klasse <code>RegistrierungBestaetigungController</code>
 * erzeugt einen <code>RegistrierungBestaetigungView</code> und ruft
 * die passende Ausgabemethode derselben auf.
 * </p>
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see RegistrierungFehlerView
 */	
public class RegistrierungBestaetigungController {
	private String uuid = null;
	/**
	 * Objekt der Klasse <code>KopfbereichView</code>
	 * @see view.KopfbereichView
	 */
	private RegistrierungBestaetigungView registrierungBestaetigungView;
	/**
	 * Objekt der Klasse <code>HttpSession</code>
	 * @see HttpSession
	 */
	private HttpSession session;
		
	/**
	 * Konstruktor der Klasse <code>KopfbereichController</code>
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @param response - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public RegistrierungBestaetigungController(HttpServletRequest request, HttpServletResponse response)
	{	
		this.session = request.getSession();
		this.registrierungBestaetigungView = new RegistrierungBestaetigungView(request, response);	
		
		if(request.getParameter("uuid") != null) {
			this.uuid = request.getParameter("uuid");
			int count = 0;
			String query = "SELECT * FROM " + KonfigurationController.getDbName()
			        + ".kunde WHERE kunde_uuid ='" + this.uuid + "'";
			ResultSet result = DatenbankController.sendeSqlRequest(query);
				try {
					while(result.next()) {
						count++;
					}
				} catch (SQLException e) {
				}
			if (count == 1) {
				this.registrierungBestaetigungView.outRegistrierungBestaetigungView();
			} else {
//				this.registrierungBestaetigungView.outRegistrierungBestaetigungFehler();
			}
		}
	}
}

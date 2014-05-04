package controller;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.AnmeldungErfolgreichView;
import view.RegistrierungBestaetigungView;

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
			} catch {
				(Exception e)
			}
			if (count == 1) {
				this.registrierungBestaetigungView.outRegistrierungBestaetigungView();
			} else {
				this.registrierungBestaetigungView.outRegistrierungBestaetigungFehler();
			}
	}
}

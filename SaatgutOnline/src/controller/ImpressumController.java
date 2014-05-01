package controller;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.AGBView;
import view.ImpressumView;

public class ImpressumController {

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

	public ImpressumController(HttpServletRequest request, HttpServletResponse response) {
		// private Connection verbindung;
		DatenbankController.getDbName(); // TODO : Richtig so? (vorher: DatenbankController.getVerbindung(); )

		// Datenbankabfrage : Impressum ausgeben
		try {
			String query = "SELECT * FROM impressum";

			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			if (resultset.next()) {
				unternehmen_adresse = resultset.getString("unternehmen_adresse");
				unternehmen_telefon = resultset.getString("unternehmen_telefon");
				unternehmen_fax = resultset.getString("unternehmen_fax");
				unternehmen_email = resultset.getString("unternehmen_email");
				unternehmen_geschaeftsfuehrung = resultset.getString("unternehmen_geschaeftsfuehrung");
				registergericht = resultset.getString("registergericht");
				register_nr = resultset.getString("register_nr");
				umsatzsteuer_id = resultset.getString("umsatzsteuer_id");
				wirtschafts_id = resultset.getString("wirtschafts_id");
				impressum_copyright = resultset.getString("impressum_copyright");

				// System.out.println(unternehmen_adresse + unternehmen_telefon +
				// unternehmen_fax +unternehmen_email + unternehmen_geschaeftsfuehrung +
				// register_nr + register_nr + umsatzsteuer_id + wirtschafts_id +
				// impressum_copyright);

				new ImpressumView(request, response, unternehmen_adresse, unternehmen_telefon,
						unternehmen_fax, unternehmen_email, unternehmen_geschaeftsfuehrung, register_nr,
						umsatzsteuer_id, wirtschafts_id, impressum_copyright);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

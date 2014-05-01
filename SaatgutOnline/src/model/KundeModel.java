/**
 * 
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatenbankController;

/**
 * @author Christof Weigandt
 *
 */

public class KundeModel {

	private int id;
	private String geschlecht;	// TODO anpassen, char oder bool
	private String vorname;
	private String nachname;
	private String benutzername;
	private String geburtsdatum;
	private String firma;
	private String strasse;
	private String hausnummer;
	private String stadt;
	private String postleitzahl;
	private String bundesland;
	private String land;
	private String telefon;
	private String email_adresse;
	private int newsletter;		// int ggfs. in bool konvertieren

	public KundeModel getKundeAusDatenbank (int id) {

		KundeModel kunde = new KundeModel ();
		
		String query = "SELECT * FROM " + DatenbankController.getDbName()
        + ".kunden WHERE kunden_id = " + id;
		ResultSet result = DatenbankController.sendeSqlRequest(query);
		
		try {
			kunde.id=(result.getInt("kunde_id"));
		
		kunde.geschlecht=(result.getString("kunde_geschlecht"));
		kunde.vorname=(result.getString("kunde_vorname"));
		kunde.nachname=(result.getString("kunde_nachname"));
		kunde.benutzername=(result.getString("kunde_benutername"));
		kunde.geburtsdatum=(result.getString("kunde_geburtsdatum"));
		kunde.firma=(result.getString("kunde_firma"));
		kunde.strasse=(result.getString("kunde_strasse"));
		kunde.hausnummer=(result.getString("kunde_hausnummer"));
		kunde.stadt=(result.getString("kunde_stadt"));
		kunde.postleitzahl=(result.getString("kunde_postleitzahl"));
		kunde.bundesland=(result.getString("kunde_bundesland"));
		kunde.land=(result.getString("kunde_land"));
		kunde.telefon=(result.getString("kunde_telefon"));
		kunde.email_adresse=(result.getString("kunde_email_adresse"));
		kunde.newsletter=(result.getInt("kunde_newsletter"));
		return kunde;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}

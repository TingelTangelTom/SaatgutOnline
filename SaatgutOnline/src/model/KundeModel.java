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
	private String geschlecht;		// TODO anpassen, char oder bool
	private String vorname;
	private String nachname;
	private String benutzername;
	private String geburtsdatum;
	private String firma;
	private String strasse;
	private String hausnummer;
	private String ort;				// TODO DB anpassen
	private String postleitzahl;	
	private String bundesland;		// TODO DB anpassen - löschen!
	private String land;			
	private String telefon;			
	private String emailAdresse;	
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
		kunde.ort=(result.getString("kunde_stadt"));
		kunde.postleitzahl=(result.getString("kunde_postleitzahl"));
		kunde.bundesland=(result.getString("kunde_bundesland"));
		kunde.land=(result.getString("kunde_land"));
		kunde.telefon=(result.getString("kunde_telefon"));
		kunde.emailAdresse=(result.getString("kunde_email_adresse"));
		kunde.newsletter=(result.getInt("kunde_newsletter"));
		return kunde;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int getId() {
		return id;
	}

	public String getGeschlecht() {
		return geschlecht;
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public String getGeburtsdatum() {
		return geburtsdatum;
	}

	public String getFirma() {
		return firma;
	}

	public String getStrasse() {
		return strasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public String getOrt() {
		return ort;
	}

	public String getPostleitzahl() {
		return postleitzahl;
	}

	public String getBundesland() {
		return bundesland;
	}

	public String getLand() {
		return land;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getEmailAdresse() {
		return emailAdresse;
	}

	public int getNewsletter() {
		return newsletter;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public void setGeburtsdatum(String geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public void setPostleitzahl(String postleitzahl) {
		this.postleitzahl = postleitzahl;
	}

	public void setBundesland(String bundesland) {
		this.bundesland = bundesland;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public void setEmailAdresse(String emailAdresse) {
		this.emailAdresse = emailAdresse;
	}

	public void setNewsletter(int newsletter) {
		this.newsletter = newsletter;
	}
	
}

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.ConnectionPoolController;
import controller.DatenbankController;
import controller.KonfigurationController;

/**
 * <p>
 * Die Klasse <code>KundeModel</code> repraesentiert die abstrahierten, relevanten und modellierten Daten eines
 * Kunden des Onlineshops. <code>validiereKundendaten(request)</code>.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see DatenbankController
 * @see ConnectionPoolController
 */
public class KundeModel
{
	private int id;
	private int geschlecht;
	private String nachname;
	private String vorname;
	private String benutzername;
	private String firma;
	private String strasse;
	private String hausnummer;
	private String ort;
	private String postleitzahl;
	private String land;
	private String telefon;
	private String emailadresse;
	private String bundesland;
	private String uuid;
	private int newsletter;
	private int freigeschaltet;

	/**
	 * Holt einen Kundendatensatz nach Kunden-ID aus der Datenbank
	 * 
	 * @param id
	 * @return
	 */
	public KundeModel ladeKundeAusDb(int id)
	{
		KundeModel kunde = new KundeModel();
		String query = "SELECT * FROM " + KonfigurationController.getDbName() + ".kunde WHERE kunde_id = " + id;
		ResultSet result = DatenbankController.sendeSqlRequest(query);
		try
		{
			while (result.next())
			{
				kunde.id = (result.getInt("kunde_id"));
				kunde.geschlecht = (result.getInt("kunde_geschlecht"));
				kunde.vorname = (result.getString("kunde_vorname"));
				kunde.nachname = (result.getString("kunde_nachname"));
				kunde.benutzername = (result.getString("kunde_benutzername"));
				kunde.firma = (result.getString("kunde_firma"));
				kunde.strasse = (result.getString("kunde_strasse"));
				kunde.hausnummer = (result.getString("kunde_hausnummer"));
				kunde.ort = (result.getString("kunde_stadt"));
				kunde.postleitzahl = (result.getString("kunde_postleitzahl"));
				kunde.land = (result.getString("kunde_land"));
				kunde.telefon = (result.getString("kunde_telefon"));
				kunde.emailadresse = (result.getString("kunde_email_adresse"));
				kunde.bundesland = (result.getString("kunde_bundesland"));
				kunde.uuid = (result.getString("kunde_uuid"));
				kunde.newsletter = (result.getInt("kunde_newsletter"));
				kunde.freigeschaltet = (result.getInt("kunde_freigeschaltet"));
			}
			return kunde;
		}
		catch (SQLException e)
		{
			return null;
		}
	}

	/**
	 * Holt einen Kundendatensatz nach Benutzernamen aus der Datenbank
	 * 
	 * @param benutzername
	 * @return
	 */
	public synchronized static KundeModel ladeKundeAusDb(String benutzername)
	{
		KundeModel kunde = new KundeModel();
		String query = "SELECT * FROM " + KonfigurationController.getDbName()
				+ ".kunde WHERE kunde_benutzername ='" + benutzername + "'";
		ResultSet result = DatenbankController.sendeSqlRequest(query);
		try
		{
			while (result.next())
			{
				kunde.id = (result.getInt("kunde_id"));
				kunde.geschlecht = (result.getInt("kunde_geschlecht"));
				kunde.vorname = (result.getString("kunde_vorname"));
				kunde.nachname = (result.getString("kunde_nachname"));
				kunde.benutzername = (result.getString("kunde_benutzername"));
				kunde.firma = (result.getString("kunde_firma"));
				kunde.strasse = (result.getString("kunde_strasse"));
				kunde.hausnummer = (result.getString("kunde_hausnummer"));
				kunde.ort = (result.getString("kunde_stadt"));
				kunde.postleitzahl = (result.getString("kunde_postleitzahl"));
				kunde.land = (result.getString("kunde_land"));
				kunde.telefon = (result.getString("kunde_telefon"));
				kunde.emailadresse = (result.getString("kunde_email_adresse"));
				kunde.bundesland = (result.getString("kunde_bundesland"));
				kunde.uuid = (result.getString("kunde_uuid"));
				kunde.newsletter = (result.getInt("kunde_newsletter"));
				kunde.freigeschaltet = (result.getInt("kunde_freigeschaltet"));
			}
		}
		catch (SQLException e)
		{
			return null;
		}
		return kunde;
	}

	/**
	 * Speichert einen Kundendatensatz in die Datenbank. Prepared Statements werden verwendet, um die Gefahr von
	 * SQL-Injections zu verringern. Nach dem speichern wird dem aktuellen Kunden-Objekt die von der Datenbank
	 * vergebene Kunden-ID uebergeben. Fuer den Verbindungsaufbau wird die Klasse ConnectionPoolController
	 * verwendet.
	 * 
	 * @return void
	 * @see ConnectionPoolController
	 * @see KonfigurationController
	 */
	public synchronized void speichereKundeInDb()
	{
		Connection verbindung = ConnectionPoolController.getInstance().getVerbindungAusPool();
		PreparedStatement prepSql;
		try
		{
			prepSql = verbindung.prepareStatement("INSERT INTO " + KonfigurationController.getDbName()
					+ ".kunde (" + "kunde_geschlecht, " + "kunde_vorname, " + "kunde_nachname, "
					+ "kunde_benutzername, " + "kunde_firma, " + "kunde_strasse, " + "kunde_hausnummer, "
					+ "kunde_stadt, " + "kunde_postleitzahl, " + "kunde_land, " + "kunde_telefon, "
					+ "kunde_email_adresse, " + "kunde_bundesland, " + "kunde_newsletter, "
					+ "kunde_freigeschaltet, " + "kunde_uuid" + ")" + " VALUES ("
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" + ")", Statement.RETURN_GENERATED_KEYS);
			prepSql.setInt(1, getGeschlecht());
			prepSql.setString(2, getVorname());
			prepSql.setString(3, getNachname());
			prepSql.setString(4, getBenutzername());
			prepSql.setString(5, getFirma());
			prepSql.setString(6, getStrasse());
			prepSql.setString(7, getHausnummer());
			prepSql.setString(8, getOrt());
			prepSql.setString(9, getPostleitzahl());
			prepSql.setString(10, getLand());
			prepSql.setString(11, getTelefon());
			prepSql.setString(12, getEmailadresse());
			prepSql.setString(13, getBundesland());
			prepSql.setInt(14, getNewsletter());
			prepSql.setInt(15, getFreigeschaltet());
			prepSql.setString(16, getUuid());
			prepSql.executeUpdate();
			ResultSet tabellenSchluessel = prepSql.getGeneratedKeys();
			ConnectionPoolController.getInstance().verschiebeVerbindungInDenPool(verbindung);
			tabellenSchluessel.next();
			this.setId(tabellenSchluessel.getInt(1));
		}
		catch (SQLException e)
		{
		}
	}

	public int getId()
	{
		return id;
	}

	public int getGeschlecht()
	{
		return geschlecht;
	}

	public String getVorname()
	{
		return vorname;
	}

	public String getNachname()
	{
		return nachname;
	}

	public String getBenutzername()
	{
		return benutzername;
	}

	public String getFirma()
	{
		return firma;
	}

	public String getStrasse()
	{
		return strasse;
	}

	public String getHausnummer()
	{
		return hausnummer;
	}

	public String getOrt()
	{
		return ort;
	}

	public String getPostleitzahl()
	{
		return postleitzahl;
	}

	public String getLand()
	{
		return land;
	}

	public String getTelefon()
	{
		return telefon;
	}

	public String getEmailadresse()
	{
		return emailadresse;
	}

	public int getNewsletter()
	{
		return newsletter;
	}

	public String getBundesland()
	{
		return bundesland;
	}

	public String getUuid()
	{
		return uuid;
	}

	public int getFreigeschaltet()
	{
		return freigeschaltet;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setGeschlecht(int geschlecht)
	{
		this.geschlecht = geschlecht;
	}

	public void setVorname(String vorname)
	{
		this.vorname = vorname;
	}

	public void setNachname(String nachname)
	{
		this.nachname = nachname;
	}

	public void setBenutzername(String benutzername)
	{
		this.benutzername = benutzername;
	}

	public void setFirma(String firma)
	{
		this.firma = firma;
	}

	public void setStrasse(String strasse)
	{
		this.strasse = strasse;
	}

	public void setHausnummer(String hausnummer)
	{
		this.hausnummer = hausnummer;
	}

	public void setOrt(String ort)
	{
		this.ort = ort;
	}

	public void setPostleitzahl(String postleitzahl)
	{
		this.postleitzahl = postleitzahl;
	}

	public void setLand(String land)
	{
		this.land = land;
	}

	public void setTelefon(String telefon)
	{
		this.telefon = telefon;
	}

	public void setEmailadresse(String emailAdresse)
	{
		this.emailadresse = emailAdresse;
	}

	public void setNewsletter(int newsletter)
	{
		this.newsletter = newsletter;
	}

	public void setfreigeschaltet(int freigeschaltet)
	{
		this.freigeschaltet = freigeschaltet;
	}

	public void setBundesland(String bundesland)
	{
		this.bundesland = bundesland;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
}

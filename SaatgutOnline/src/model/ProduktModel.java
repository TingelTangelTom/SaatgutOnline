package model;

import java.sql.Date;
import java.util.HashMap;

/**
 * <p>
 * Die Klasse <code>ProduktModel</code> beinhaltet die alle relevanten Produktdaten.
 * </p>
 * 
 * @author Simon
 * @version 1.0
 * @since 1.7.0_51
 */
public class ProduktModel
{
	private int id;
	private int kategorieId;
	private String produktnummer;
	private int bestand;
	private String name;
	private String beschreibung;
	private String suchbegriffe;
	private HashMap<String, String> merkmale;
	private int angesehen;
	private double preisNetto;
	private double preisBrutto;
	private double preisEhemalsNetto;
	private double preisEhemalsBrutto;
	private Date gueltig_bis;
	private int vpe;
	private double steuerSatz;
	private double steuerBetrag;
	private Date hinzugefeugt;
	private Date geaendert;

	/**
	 * <p>
	 * Stellt die Id des Produktes zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return Id des Produktes als <code>int</code>
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * <p>
	 * Setzt die Id des Produktes.
	 * </p>
	 * 
	 * @param id
	 *            - Id des Produkts als <i>int</i>
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * <p>
	 * Stellt die Kategorie-Id des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>int</i> - Kategorie-ID des Produktes
	 */
	public int getKategorieId()
	{
		return kategorieId;
	}

	/**
	 * <p>
	 * Setzt die Id der Kategorie des Produkts.
	 * </p>
	 * 
	 * @param kategorieId
	 *            - Id der Kategorie des Produkts als <i>int</i>
	 */
	public void setKategorieId(int kategorieId)
	{
		this.kategorieId = kategorieId;
	}

	/**
	 * <p>
	 * Stellt die Produktnummer des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>String</i> - Produktnummer des Produktes
	 */
	public String getProduktnummer()
	{
		return produktnummer;
	}

	/**
	 * <p>
	 * Setzt die Produktnummer der Kategorie des Produkts.
	 * </p>
	 * 
	 * @param produktnummer
	 *            - des Produkts als <i>String</i>
	 */
	public void setProduktnummer(String produktnummer)
	{
		this.produktnummer = produktnummer;
	}

	/**
	 * <p>
	 * Stellt den Bestand des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>int</i> - Bestand des Produktes
	 */
	public int getBestand()
	{
		return bestand;
	}

	/**
	 * <p>
	 * Setzt den Bestand des Produkts.
	 * </p>
	 * 
	 * @param bestand
	 *            - des Produkts als <i>int</i>
	 */
	public void setBestand(int bestand)
	{
		this.bestand = bestand;
	}

	/**
	 * <p>
	 * Stellt den Namen des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>String</i> - Name des Produktes
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * <p>
	 * Setzt den Namen des Produkts.
	 * </p>
	 * 
	 * @param name
	 *            - des Produkts als <i>String</i>
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * <p>
	 * Stellt die Beschreibung des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>String</i> - Beschreibung des Produktes
	 */
	public String getBeschreibung()
	{
		return beschreibung;
	}

	/**
	 * <p>
	 * Setzt die Beschreibung des Produkts.
	 * </p>
	 * 
	 * @param beschreibung
	 *            - des Produkts als <i>String</i>
	 */
	public void setBeschreibung(String beschreibung)
	{
		this.beschreibung = beschreibung;
	}

	/**
	 * <p>
	 * Stellt die Suchbegriffe des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>String</i> - Suchbegriffe des Produktes
	 */
	public String getSuchbegriffe()
	{
		return suchbegriffe;
	}

	/**
	 * <p>
	 * Setzt die Suchbegriffe des Produkts.
	 * </p>
	 * 
	 * @param suchbegriffe
	 *            - des Produkts als <i>String</i>
	 */
	public void setSuchbegriffe(String suchbegriffe)
	{
		this.suchbegriffe = suchbegriffe;
	}

	/**
	 * <p>
	 * Stellt die Merkmale des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>HashMap&lt;String,String&gt;</i> - Merkmale des Produktes
	 */
	public HashMap<String, String> getMerkmale()
	{
		return merkmale;
	}

	/**
	 * <p>
	 * Setzt die Merkmale des Produkts.
	 * </p>
	 * 
	 * @param merkmale
	 *            - des Produkts als <i>HashMap&lt;String,String&gt;</i>
	 */
	public void setMerkmale(HashMap<String, String> merkmale)
	{
		this.merkmale = merkmale;
	}

	/**
	 * <p>
	 * Stellt die Anzahl der Zugriffe auf die Einzelansicht des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>int</i> - Zugriffe auf die Einzelansicht des Produkts
	 */
	public int getAngesehen()
	{
		return angesehen;
	}

	/**
	 * <p>
	 * Setzt den Wert, wie oft das Produkt in der Einzelansicht aufgerufen wurde.
	 * </p>
	 * 
	 * @param preisNetto
	 *            - des Produkts als <i>double</i>
	 */
	public void setAngesehen(int angesehen)
	{
		this.angesehen = angesehen;
	}

	/**
	 * <p>
	 * Stellt den Nettopreis des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>double</i> - Nettopreis des Produktes
	 */
	public double getPreisNetto()
	{
		return preisNetto;
	}

	/**
	 * <p>
	 * Setzt den Nettopreis des Produkts.
	 * </p>
	 * 
	 * @param preisNetto
	 *            - des Produkts als <i>double</i>
	 */
	public void setPreisNetto(double preisNetto)
	{
		this.preisNetto = preisNetto;
	}

	/**
	 * <p>
	 * Stellt den Bruttopreis des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>double</i> - Bruttopreis des Produktes
	 */
	public double getPreisBrutto()
	{
		return preisBrutto;
	}

	/**
	 * <p>
	 * Setzt den Nettopreis des Produkts.
	 * </p>
	 * 
	 * @param preisNetto
	 *            - des Produkts als <i>double</i>
	 */
	public void setPreisBrutto(double preisBrutto)
	{
		this.preisBrutto = preisBrutto;
	}

	/**
	 * <p>
	 * Stellt den Angebots-Nettopreis des Produkts zur Verf&uuml;gung (0 = kein Aktionspreis).
	 * </p>
	 * 
	 * @return <i>double</i> - Angebots-Nettopreis des Produktes
	 */
	public double getPreisEhemalsNetto()
	{
		return preisEhemalsNetto;
	}

	/**
	 * <p>
	 * Setzt den Angebots-Nettopreis des Produkts.
	 * </p>
	 * 
	 * @param preisAngebotNetto
	 *            - des Produkts als <i>double</i>
	 */
	public void setPreisEhemalsNetto(double preisEhemalsNetto)
	{
		this.preisEhemalsNetto = preisEhemalsNetto;
	}

	/**
	 * <p>
	 * Stellt den Angebots-Bruttopreis des Produkts zur Verf&uuml;gung (0 = kein Aktionspreis).
	 * </p>
	 * 
	 * @return <i>double</i> - Angebots-Nettopreis des Produktes
	 */
	public double getPreisEhemalsBrutto()
	{
		return preisEhemalsBrutto;
	}

	/**
	 * <p>
	 * Setzt den Angebots-Bruttopreis des Produkts.
	 * </p>
	 * 
	 * @param preisAngebotBrutto
	 *            - des Produkts als <i>double</i>
	 */
	public void setPreisEhemalsBrutto(double preisEhemalsBrutto)
	{
		this.preisEhemalsBrutto = preisEhemalsBrutto;
	}

	/**
	 * <p>
	 * Stellt die G&uuml;ltigkeitsdauer des Angebots des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <code>Date</code> - G&uuml;ltigkeitsdauer des Angebots des Produktes
	 */
	public Date getGueltig_bis()
	{
		return gueltig_bis;
	}

	/**
	 * <p>
	 * Setzt die G&uuml;ltigkeitsdauer des Angebots des Produkts.
	 * </p>
	 * 
	 * @param gueltig_bis
	 *            - des Produkts als <code>Date</code>
	 */
	public void setGueltig_bis(Date gueltig_bis)
	{
		this.gueltig_bis = gueltig_bis;
	}

	/**
	 * <p>
	 * Stellt die Verpackungseinheit des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>int</i> - Verpackungseinheit des Produktes
	 */
	public int getVpe()
	{
		return vpe;
	}

	/**
	 * <p>
	 * Setzt die Verpackungseinheit des Produkts.
	 * </p>
	 * 
	 * @param vpe
	 *            - des Produkts als <code>int</code>
	 */
	public void setVpe(int vpe)
	{
		this.vpe = vpe;
	}

	/**
	 * <p>
	 * Stellt den Steuersatz des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>double</i> - Steuersatz des Produktes
	 */
	public double getSteuerSatz()
	{
		return steuerSatz;
	}

	/**
	 * <p>
	 * Setzt den Steuersatz des Produkts.
	 * </p>
	 * 
	 * @param steuerSatz
	 *            - des Produkts als <code>double</code>
	 */
	public void setSteuerSatz(double steuerSatz)
	{
		this.steuerSatz = steuerSatz;
	}

	/**
	 * <p>
	 * Stellt den Steuerbetrag des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <i>double</i> - Steuersatz des Produktes
	 */
	public double getSteuerBetrag()
	{
		return steuerBetrag;
	}

	/**
	 * <p>
	 * Setzt den Steuerbetrag des Produkts.
	 * </p>
	 * 
	 * @param steuerBetrag
	 *            - des Produkts als <code>double</code>
	 */
	public void setSteuerBetrag(double steuerBetrag)
	{
		this.steuerBetrag = steuerBetrag;
	}

	/**
	 * <p>
	 * Stellt das Datum des Hinzuf&uuml;gens des Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <code>Date</code> - Produktes wurde an welchem Datum hinzugef&uuml;gt
	 */
	public Date getHinzugefeugt()
	{
		return hinzugefeugt;
	}

	/**
	 * <p>
	 * Setzt das Datum, an dem das Produkt erstellt wurde.
	 * </p>
	 * 
	 * @param geaendert
	 *            - Erstellungsdatum des Produkts als <code>Date</code>
	 */
	public void setHinzugefeugt(Date hinzugefeugt)
	{
		this.hinzugefeugt = hinzugefeugt;
	}

	/**
	 * <p>
	 * Stellt das Datum der letzten &Auml;nderung am Produkts zur Verf&uuml;gung.
	 * </p>
	 * 
	 * @return <code>Date</code> - letzte &Auml;nderung am Produkt
	 */
	public Date getGeaendert()
	{
		return geaendert;
	}

	/**
	 * <p>
	 * Setzt das &Auml;nderungsdatum des Produkts.
	 * </p>
	 * 
	 * @param geaendert
	 *            - des Produkts als <code>Date</code>
	 */
	public void setGeaendert(Date geaendert)
	{
		this.geaendert = geaendert;
	}
}
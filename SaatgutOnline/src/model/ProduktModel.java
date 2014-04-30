package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;



public class ProduktModel implements Comparable<ProduktModel> {
	private int id;
	private int kategorieId;
	private int bestand;
	private String name;
	private String beschreibung;
	private HashMap<String, String> merkmale;
	private String suchbegriffe;
	private int angesehen;
	private double preisNetto;
	private double preisBrutto;
	private double gewicht;
	private int steuersatzId;
	private double steuersatz;
	private Date hinzugefeugt;
	private Date geaendert;
	
	public ProduktModel() {
		super();		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKategorie_id() {
		return kategorieId;
	}

	public void setKategorieId(int kategorieId) {
		this.kategorieId = kategorieId;
	}

	public int getBestand() {
		return bestand;
	}

	public void setBestand(int bestand) {
		this.bestand = bestand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getSuchbegriffe() {
		return suchbegriffe;
	}

	public void setSuchbegriffe(String suchbegriffe) {
		this.suchbegriffe = suchbegriffe;
	}

	public int getAngesehen() {
		return angesehen;
	}

	public void setAngesehen(int angesehen) {
		this.angesehen = angesehen;
	}

	public double getPreisNetto() {
		return preisNetto;
	}

	public void setPreisNetto(double d) {
		this.preisNetto = d;
	}	
	
	public double getPreisBrutto() {
		return preisBrutto;
	}

	public void setPreisBrutto(double d) {
		this.preisBrutto = d;
	}

	public double getGewicht() {
		return gewicht;
	}

	public void setGewicht(double gewicht) {
		this.gewicht = gewicht;
	}

	public int getSteuersatz_id() {
		return steuersatzId;
	}

	public void setSteuerId(int steuerId) {
		this.steuersatzId = steuerId;
	}

	public Date getHinzugefeugt() {
		return hinzugefeugt;
	}

	public void setHinzugefeugt(Date hinzugefeugt) {
		this.hinzugefeugt = hinzugefeugt;
	}

	public Date getGeaendert() {
		return geaendert;
	}

	public void setGeaendert(Date geaendert) {
		this.geaendert = geaendert;
	}

	public double getSteuersatz() {
		return steuersatz;
	}

	public void setSteuersatz(double steuersatz) {
		this.steuersatz = steuersatz;
	}

	public HashMap<String, String> getMerkmale() {
		return merkmale;
	}

	public void setMerkmale(HashMap<String, String> merkmale) {
		this.merkmale = merkmale;
	}
	
	@Override
    public int compareTo(ProduktModel produktModel) {
      return ((String)name).compareTo((String)produktModel.name);
      // return ((String)farbe).compareTo((String)o.farbe); // alternativ nach farbe sortieren
    }
}
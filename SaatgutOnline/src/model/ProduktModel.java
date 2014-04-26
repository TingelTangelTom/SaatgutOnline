package model;

import java.sql.Date;

public class ProduktModel {
	private int id;
	private int kategorieId;
	private int bestand;
	private String name;
	private String beschreibung;
	private String suchbegriffe;
	private int angesehen;
	private double preis;
	private double gewicht;
	private int steuersatzId;
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

	public double getPreis() {
		return preis;
	}

	public void setPreis(double d) {
		this.preis = d;
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
	
}
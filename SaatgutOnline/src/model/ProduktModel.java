package model;

import java.sql.Date;
import java.util.HashMap;



public class ProduktModel {
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
	private double preisAngebotNetto;
	private double preisAngebotBrutto;
	private Date gueltig_bis;
	private double vpe;
	private double steuerSatz;
	private double steuerBetrag;
	private Date hinzugefeugt;
	private Date geaendert;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getKategorieId() {
		return kategorieId;
	}
	
	public void setKategorieId(int kategorieId) {
		this.kategorieId = kategorieId;
	}
	
	public String getProduktnummer() {
		return produktnummer;
	}
	
	public void setProduktnummer(String produktnummer) {
		this.produktnummer = produktnummer;
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
	
	public HashMap<String, String> getMerkmale() {
		return merkmale;
	}
	
	public void setMerkmale(HashMap<String, String> merkmale) {
		this.merkmale = merkmale;
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
	
	public void setPreisNetto(double preisNetto) {
		this.preisNetto = preisNetto;
	}
	
	public double getPreisBrutto() {
		return preisBrutto;
	}
	
	public void setPreisBrutto(double preisBrutto) {
		this.preisBrutto = preisBrutto;
	}
	
	public double getPreisAngebotNetto() {
		return preisAngebotNetto;
	}

	public void setPreisAngebotNetto(double preisAngebotNetto) {
		this.preisAngebotNetto = preisAngebotNetto;
	}

	public double getPreisAngebotBrutto() {
		return preisAngebotBrutto;
	}

	public void setPreisAngebotBrutto(double preisAngebotBrutto) {
		this.preisAngebotBrutto = preisAngebotBrutto;
	}

	public Date getGueltig_bis() {
		return gueltig_bis;
	}

	public void setGueltig_bis(Date gueltig_bis) {
		this.gueltig_bis = gueltig_bis;
	}
	
	public double getVpe() {
		return vpe;
	}
	
	public void setVpe(double vpe) {
		this.vpe = vpe;
	}
	
	public double getSteuerSatz() {
		return steuerSatz;
	}
	
	public void setSteuerSatz(double steuerSatz) {
		this.steuerSatz = steuerSatz;
	}
	
	public double getSteuerBetrag() {
		return steuerBetrag;
	}
	
	public void setSteuerBetrag(double steuerBetrag) {
		this.steuerBetrag = steuerBetrag;
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
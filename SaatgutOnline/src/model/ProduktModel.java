package model;

public class ProduktModel {
	private int id;
	private int kategorie_id;
	
	public ProduktModel() {
		super();		
	}	
	
	public ProduktModel(int id, int kategorieId) {
		super();		
	}
	
	public int getNummer() {
		return id;
	}
	public void setNummer(int nummer) {
		this.id = nummer;
	}
	public int getKategorieId() {
		return kategorie_id;
	}
	public void setKategorieId(int kategorie_id) {
		this.kategorie_id = kategorie_id;
	}
	
}

package model;

public class ProduktModel {
	private int id;
	private String name;
	
	public ProduktModel() {
		super();		
	}	
	
	public ProduktModel(int id, String name) {
		super();		
	}
	
	public int getNummer() {
		return id;
	}
	public void setNummer(int nummer) {
		this.id = nummer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

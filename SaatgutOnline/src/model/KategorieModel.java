package model;

public class KategorieModel
{
	private int kategorieId;
	private String kategorieName;
	private int elternKategorieId;
	
	public int getKategorieId()
	{
		return kategorieId;
	}
	public void setKategorieId(int kategorieId)
	{
		this.kategorieId = kategorieId;
	}
	public String getKategorieName()
	{
		return kategorieName;
	}
	public void setKategorieName(String kategorieName)
	{
		this.kategorieName = kategorieName;
	}
	public int getElternKategorieId()
	{
		return elternKategorieId;
	}
	public void setElternKategorieId(int elternKategorieId)
	{
		this.elternKategorieId = elternKategorieId;
	}		
}

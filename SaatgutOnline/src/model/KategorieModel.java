package model;

/**
 * Die Klasse beinhaltet die Daten einer Kategorie
 * @author Tom
 *
 */
public class KategorieModel
{
	/**
	 * Id der Kategorie als <code>int</code>
	 */
	private int kategorieId;
	/**
	 * Name der Kategorie als <code>String</code>
	 */
	private String kategorieName;
	/**
	 * Eltern-Id der Kategorie als <code>int</code>
	 */
	private int elternKategorieId;
	
	/**
	 * Stellt die Id der Kategorie zur Verfuegung
	 * @return Id der Kategorie als <code>int</code> 
	 */
	public int getKategorieId()
	{
		return kategorieId;
	}
	
	/**
	 * Setzt die Id der Kategorie
	 * @param kategorieId - Id der Kategorie als <code>int</code>
	 */
	public void setKategorieId(int kategorieId)
	{
		this.kategorieId = kategorieId;
	}
	
	/**
	 * Stellt den Namen der Kategorie zur Verfuegung
	 * @return Name der Kategorie als <code>String</code> 
	 */
	public String getKategorieName()
	{
		return kategorieName;
	}
	
	/**
	 * Setzt den Namen der Kategorie
	 * @param kategorieName - Name der Kategorie als <code>String</code>
	 */
	public void setKategorieName(String kategorieName)
	{
		this.kategorieName = kategorieName;
	}
	
	/**
	 * Stellt die Eltern-Id der Kategorie zur Verfuegung
	 * @return Eltern-Id der Kategorie als <code>int</code> 
	 */
	public int getElternKategorieId()
	{
		return elternKategorieId;
	}
	
	/**
	 * Setzt die Eltern-Id der Kategorie
	 * @param elternKategorieId - die Eltern-Id der Kategorie als <code>int</code>
	 */
	public void setElternKategorieId(int elternKategorieId)
	{
		this.elternKategorieId = elternKategorieId;
	}		
}

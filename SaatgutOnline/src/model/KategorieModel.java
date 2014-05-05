package model;

/**
 * <p>Die Klasse <code>KategorieModel</code> beinhaltet die Daten einer Kategorie.</p>
 * @author Tom Weigelt
 * @version 1.0
 * @since 1.7.0_51
 */
public class KategorieModel
{
	private int kategorieId;
	private String kategorieName;
	private int elternKategorieId;
	
	/**
	 * <p>Stellt die Id der Kategorie zur Verfuegung.</p>
	 * @return Id der Kategorie als <code>int</code> 
	 */
	public int getKategorieId()
	{
		return kategorieId;
	}
	
	/**
	 * <p>Setzt die Id der Kategorie.</p>
	 * @param kategorieId - Id der Kategorie als <code>int</code>
	 */
	public void setKategorieId(int kategorieId)
	{
		this.kategorieId = kategorieId;
	}
	
	/**
	 * <p>Stellt den Namen der Kategorie zur Verfuegung.</p>
	 * @return Name der Kategorie als <code>String</code> 
	 */
	public String getKategorieName()
	{
		return kategorieName;
	}
	
	/**
	 * <p>Setzt den Namen der Kategorie.</p>
	 * @param kategorieName - Name der Kategorie als <code>String</code>
	 */
	public void setKategorieName(String kategorieName)
	{
		this.kategorieName = kategorieName;
	}
	
	/**
	 * <p>Stellt die Eltern-Id der Kategorie zur Verfuegung.</p>
	 * @return Eltern-Id der Kategorie als <code>int</code> 
	 */
	public int getElternKategorieId()
	{
		return elternKategorieId;
	}
	
	/**
	 * <p>Setzt die Eltern-Id der Kategorie.</p>
	 * @param elternKategorieId - die Eltern-Id der Kategorie als <code>int</code>
	 */
	public void setElternKategorieId(int elternKategorieId)
	{
		this.elternKategorieId = elternKategorieId;
	}		
}

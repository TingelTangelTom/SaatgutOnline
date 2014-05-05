package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import model.KategorieModel;

/**
 * Die Klasse NavigationsbereichView stellt Html-Ausgabe-Bloecke zur Darstellung des Navigationsbereichs zur Verfuegung
 * @author Tom Weigelt
 *
 */
public class NavigationsbereichView
{
	/**
	 * Objekt der Klasse <code>PrintWriter</code>
	 * @see java.io.PrintWriter
	 */
	private PrintWriter out;
	
	/**
	 * Konstruktor der Klasse <code>NavigationsbereichView</code>
	 * @param response - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public NavigationsbereichView(HttpServletResponse response)
	{
		response.setContentType("text/html");
		try
		{
			this.out = response.getWriter();
		} catch (IOException e)
		{
			System.out.println("PrintWriter nicht erstellt!");
			e.printStackTrace();
		}		
	}

	/**
	 * Stellt die Html-Ausgabe fuer den Anfang des Navigationsbereiches zur Verfuegung
	 */
	public void outNavigationsbereichAnfang()
	{
		this.out.println("<tr>\n<td class=\"navigation\">");		
	}
	
	/**
	 * Stellt die Html-Ausgabe fuer das Ende des Navigationsbereiches zur Verfuegung
	 */
	public void outNavigationsbereichEnde()
	{
		this.out.println("</td>\n<td class=\"inhalt\">");
	}
	
	/**
	 * Stellt die Html-Ausgabe fuer den Anfang der Kategorienliste im Navigationsbereich zur Verfuegung
	 */
	public void outKategorienListeAnfang()
	{
		this.out.println("<table border = '1'>");
	}
	
	/**
	 * Stellt die Html-Ausgabe fuer das Ende der Kategorienliste im Navigationsbereich zur Verfuegung
	 */
	public void outKategorienListeEnde()
	{
		this.out.println("</table>");
	}
	
	/**
	 * Stellt die Html-Ausgabe fuer eine Hauptkategorie in der Kategorienliste des Navigationsbereiches zur Verfuegung
	 * @param kategorieModel - das Objekt der anzuzeigenden Kategorie als <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	public void outHauptKategorieAnzeigen(KategorieModel kategorieModel)
	{		
		this.out.println("<tr>\n<td colspan=\"2\">");
		this.out.println("<a href=\"/SaatgutOnline/Produktliste?kategorie="
				+ kategorieModel.getKategorieId()				
				+ "\">\n"
				+ kategorieModel.getKategorieName()
				+ "\n</a>");
		this.out.println("</td>\n</tr>");
	}
	
	/**
	 * Stellt die Html-Ausgabe fuer eine Unterkategorie in der Kategorienliste des Navigationsbereiches zur Verfuegung
	 * @param kategorieModel - das Objekt der anzuzeigenden Kategorie als <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	public void outUnterKategorieAnzeigen(KategorieModel kategorieModel)
	{			
		this.out.println("<tr>\n<td>");
		
		//TODO remove next line - format properly in CSS!
		this.out.println("&nbsp;");
		
		this.out.println("</td>\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/Produktliste?kategorie="
				+ kategorieModel.getKategorieId()				
				+ "\">\n"
				+ kategorieModel.getKategorieName()
				+ "\n</a>");
		this.out.println("</td>\n</tr>");
	}
	
	/**
	 * Stellt die Html-Ausgabe fuer die aktuell gewaehlte Hauptkategorie in der Kategorienliste des Navigationsbereiches zur Verfuegung
	 * </br>Die aktuelle Kategorie kann per CSS gesondert gestyled werden
	 * @param kategorieModel - das Objekt der anzuzeigenden Kategorie als <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	public void outHauptKategorieAktuellAnzeigen(KategorieModel kategorieModel)
	{		
		this.out.println("<tr class=\"navigation_aktuell\">\n<td colspan=\"2\">");		
		this.out.println("<a href=\"/SaatgutOnline/Produktliste?kategorie="
				+ kategorieModel.getKategorieId()				
				+ "\">\n"
				+ kategorieModel.getKategorieName()
				+ "\n</a>");
		this.out.println("</td>\n</tr>");		
	}
	
	/**
	 * Stellt die Html-Ausgabe fuer die aktuell gewaehlte Unterkategorie in der Kategorienliste des Navigationsbereiches zur Verfuegung
	 * </br>Die aktuelle Kategorie kann per CSS gesondert gestyled werden
	 * @param kategorieModel - das Objekt der anzuzeigenden Kategorie als <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	public void outUnterKategorieAktuellAnzeigen(KategorieModel kategorieModel)
	{			
		this.out.println("<tr class=\"navigation_aktuell\">\n<td>");
		
		//TODO remove next line - format properly in CSS!
		this.out.println("&nbsp;");
		
		this.out.println("</td>\n<td>");				
		this.out.println("<a href=\"/SaatgutOnline/Produktliste?kategorie="
				+ kategorieModel.getKategorieId()				
				+ "\">\n"
				+ kategorieModel.getKategorieName()
				+ "\n</a>");
		this.out.println("</td>\n</tr>");
	}
}

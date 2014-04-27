package view;

public class HtmlAusgabe
{
	/*
	 * Hier mal eine Idee zu einer Html-Ausgabe-Klasse.
	 * 
	 * Ich habe zwei Varianten angedacht:
	 * 
	 * 
	 * 1. Ausgabe per statischer Konstanten:
	 * So kann man die Html-Code-Schnippsel einfach im View per zB.
	 *  
	 * out.println(Html.NEUE_SPALTE);
	 * 
	 * einbinden.
	 * 
	 * 
	 * 
	 * 2. Ausgabe per Methode:
	 * Hier muss zunächst ein HtmlAusgabe-Objekt erzeugt werden,
	 * 
	 * (zB. HtmlAusgabe htmlAusgabe = new HtmlAusgabe;)
	 * 
	 * um auf die Methoden zugreifen zu koennen.
	 * 
	 * Diese returnen dann den Schnippsel als String.
	 * Im View wurde das dann so aussehen:
	 * 
	 * out.println(htmAusgabe.htmlNeueZeile());
	 * 
	 * Denke, sowas wuerde sich eher fuer komplexere Bauereien eignen,
	 * wie zB. in der htmlTabelleOeffnen()-Methode dargestellt.
	 */
	
	
	
	
	/**
	 * Schliesst eine Spalte und oeffnet eine neue Spalte.
	 */
	public static final String NEUE_SPALTE = "</td>\n<td>";
	
	/**
	 * Schliesst eine Zeile und oeffnet eine neue Zeile.
	 */
	public static final String NEUE_ZEILE = "</tr>\n<tr>";
	
	/**
	 * Schliesst eine Zeile, schliesst eine Spalte und</br>
	 * oeffnet eine neue Zeile, oeffnet eine neue Spalte.
	 */
	public static final String NEUE_ZEILE_NEUE_SPALTE = "</td>\n</tr>\n<tr>\n<td>";
	
	/**
	 * Schliesst die letzte Spalte, die letzte Zeile und die Tabelle.
	 */
	public static final String TABELLE_SCHLIESSEN = "</td>\n</tr>\n</table>";
	
	
	
	/**
	 * Schliesst eine Spalte und oeffnet eine neue Spalte.	
	 * @return Der entsprechende Html-Code als String
	 */
	public String htmlNeueSpalte()
	{
		return "</td>\n<td>";
	}

	/**
	 * Schliesst eine Zeile und oeffnet eine neue Zeile.
	 * @return Der Html-Code als String
	 */
	public String htmlNeueZeile()
	{
		return "</tr>\n<tr>";
	}
	
	/**
	 * Schliesst eine Zeile, schliesst eine Spalte und</br>
	 * oeffnet eine neue Zeile, oeffnet eine neue Spalte.
	 * @return Der Html-Code als String
	 */
	public String htmlNeueZeileNeueSpalte()
	{
		return "</td>\n</tr>\n<tr>\n<td>";
	}
	
	/**
	 * Schliesst die letzte Spalte, die letzte Zeile und die Tabelle.
	 * @return Der Html-Code als String
	 */
	public String htmlTabelleSchliessen()
	{
		return "</td>\n</tr>\n</table>";
	}
	
	/**
	 * Oeffnet eine Tabelle mit zugewiesenem CSS-Namen.
	 * </br>(Keine Ahnung wie man das adäquat ausdrueckt...^^)
	 * @param name
	 * Der Name der class als String
	 * @return
	 * Der Html-Code als String
	 */
	public String htmlTabelleOeffnen(String name)
	{
		return "<table class='" + name + "'>\n";
	}
	
}

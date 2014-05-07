package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import controller.DatenbankController;

/**
 * <p>
 * Die Klasse <code>HtmlAusgabe</code> liefert R&uuml;ckgabewerte für die Darstellung und Organisation der
 * Produktliste und der Einzelansicht von Produkten Verf&uuml;gung.
 * </p>
 * 
 * @author Simon Ankele
 * @version 1.0
 * @since 1.7.0_51
 */
public class HtmlAusgabe extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private Locale locale;
	private HttpSession session;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>HtmlAusgabe</code>
	 * </p>
	 * <p>
	 * Der Konstruktor speichert das Session-Attribut <i>spracheId</i> in der Klassenvariablen <i>sprache_id</i>
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public HtmlAusgabe(HttpServletRequest request)
	{
		this.session = request.getSession();
		this.locale = (Locale) session.getAttribute("sprache");
	}

	/**
	 * <p>
	 * Die Methode <code>outPreisformat</code> wandelt den Übergabeparameter <i>double preis</i> in das Preisformat
	 * der aktuell gewählten Sprache um.
	 * </p>
	 * <p>
	 * Beispiel: <i>EUR1.000,00</i> oder <i>$ 1,000.00</i>
	 * 
	 * @param preis
	 *            - <i>double</i>-Wert, welcher als Preis ausgegeben wird
	 * @return <code>String</code> liefert den formatierten Preis zurück
	 * @see model#Produktinfo
	 * @see model#Produktliste
	 */
	public String outPreisformat(double preis)
	{
		NumberFormat waehrungsFormat = NumberFormat.getCurrencyInstance(this.locale);
		waehrungsFormat.setCurrency(Currency.getInstance("EUR"));
		return waehrungsFormat.format(preis);
	}

	/**
	 * <p>
	 * Die Methode <code>outPreisformatEnglischerZusatz</code> wandelt den Übergabeparameter <i>double preis</i> in
	 * das Preisformat US-Dollar um und setzt dieses in Klammern
	 * </p>
	 * <p>
	 * Beispiel: <i>($ 1,000.00)</i>
	 * 
	 * @param preis
	 *            - <i>double</i>-Wert, welcher als Preis ausgegeben wird
	 * @return <code>String</code> liefert den formatierten Preis zurück
	 * @see model#Produktinfo
	 * @see model#Produktliste
	 */
	public String outPreisformatEnglischerZusatz(double preis)
	{
		String englischerZusatz;
		if (!this.locale.equals(Locale.GERMAN))
		{
			NumberFormat waehrungsFormat = NumberFormat.getCurrencyInstance(Locale.US);
			waehrungsFormat.setCurrency(Currency.getInstance("USD"));
			englischerZusatz = "(" + waehrungsFormat.format(preis * 1.34) + ")";
		}
		else
		{
			englischerZusatz = "";
		}
		return englischerZusatz;
	}

	/**
	 * <p>
	 * Die Methode <code>outPreisverordnung</code> gibt einen rechtskonformen Text als <i>String</i> für die
	 * Preisangabe zurück. Er setzt sich aus der Angabe der Mehrwertsteuer und einem sichtbaren Link für die
	 * Versandkosten zusammen.
	 * </p>
	 * 
	 * @param versandkosten_text
	 *            - internationalisierter Name für Versandkosten
	 * @param mwst
	 *            - <i>double</i>-Parameter des Prozentwertes der Mehrwertsteuer
	 * @return <i>String</i>, welcher einen rechtskonformen Text inklusive Versandkosten-Link und Mehrwertsteuer
	 *         enthält
	 * @see model#Produktliste
	 * @see model#Produktinfo
	 */
	public String outPreisverordnung(String versandkosten_text, double mwst)
	{
		NumberFormat prozentFormat = NumberFormat.getPercentInstance(this.locale);
		String prozent = prozentFormat.format(mwst / 100);
		ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("I18N." + this.locale.getLanguage()
				+ ".ProduktinfoView", this.locale); // Pfad muss noch angepasst werden
		return MessageFormat.format(resourceBundle.getString("PREISTEXT"), prozent)
				+ " <a href=\"/SaatgutOnline/VersandInfo\"><b>" + versandkosten_text + "</b></a>";
	}

	/**
	 * <p>
	 * Die Methode <code>outKurzeProduktbeschreibung</code> gibt einen rechtskonformen Text als <i>String</i> für
	 * die Preisangabe zurück. Er setzt sich aus der Angabe der Mehrwertsteuer und einem sichtbaren Link für die
	 * Versandkosten zusammen.
	 * </p>
	 * 
	 * @param mehr_anzeigen
	 *            - Text für den Link zum <code>ProduktinfoView</code>
	 * @param beschreibung
	 *            - Text, welcher gekürzt werden soll
	 * @param zeichen
	 *            - Anzahl der Zeichen, die der Text enthalten darf
	 * @param id
	 *            - Produkt-ID des Textes
	 * @return <i>String</i> des gekürzten Textes
	 * @see model#Produktliste
	 */
	public String outKurzeProduktbeschreibung(String mehr_anzeigen, String beschreibung, int zeichen, int id)
	{
		String kurzeBeschreibung = beschreibung.substring(0, zeichen);
		kurzeBeschreibung += "<a href=\"/SaatgutOnline/Produktinfo?produkt=" + id + "\"><b>..." + mehr_anzeigen
				+ "</b></a>";
		return kurzeBeschreibung;
	}

	/**
	 * <p>
	 * Die Methode <code>outKategoriename</code> gibt den vollen Kategorienamen zurück, welcher sich aus der
	 * Hauptkategorie und der Unterkategorie zusammensetzt und der Datenbank-Tabelle Kategorie in der Spalte
	 * <i>kategorie_schreibung</i> befindet.
	 * </p>
	 * 
	 * @param kategorie_id
	 *            der gewünschten Kategorie
	 * @return <i>String</i> der gewünschten Kategoriebeschreibung
	 * @see model#Produkliste
	 */
	public String outKategoriename(String kategorie_id)
	{
		String kategoriename = null;
		String query = "SELECT kb.kategorie_beschreibung " + "FROM kategorie AS k "
				+ "INNER JOIN kategorie_beschreibung AS kb ON k.kategorie_id = kb.kategorie_id "
				+ "WHERE kb.sprache_id = '" + session.getAttribute("spracheId") + "' AND kb.kategorie_id = '"
				+ kategorie_id + "'";
		try
		{
			ResultSet resultset = DatenbankController.sendeSqlRequest(query);
			while (resultset.next())
			{
				kategoriename = resultset.getString(1);
			}
		}
		catch (SQLException e)
		{
		}
		return kategoriename;
	}

	/**
	 * <p>
	 * Die Methode <code>outLinkProduktinfo</code> gibt den Link inklusive der benötigten <i>GET</i>-Parameter
	 * zurück.
	 * </p>
	 * 
	 * @param linkString
	 *            der <i>GET</i>-Parameter
	 * @param produkt_id
	 *            des aktuellen Produktes
	 * @return <i>String</i> des Links inklusive der benötigten <i>GET</i>-Parameter
	 * @see model#Produktliste
	 */
	public String outLinkProduktinfo(String linkString, int produkt_id)
	{
		return "<a href=\"/SaatgutOnline/Produktinfo?produkt=" + produkt_id + "\">" + linkString + "</a>";
	}

	/**
	 * <p>
	 * Die Methode <code>outParameterLink</code> gibt die <i>GET</i>-Parameter für den Link zurück. Dieses setzt
	 * sich aus den einzelnen Übergabeparametern und <code>Session</code>-Attributen zusammen.
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param erweitertesuche
	 *            - <i>boolean</i>-Wert, ob erweiterte Suche angezeigt werden soll
	 * @param suchen
	 *            - <i>boolean</i>-Wert, ob die Suche gestartet werden soll
	 * @param suchbegriff
	 *            - <i>String</i>-Wert des Suchbegriffs
	 * @return <i>String</i> der Übergabeparameter
	 * @see model#Produktliste
	 */
	public String outParameterLink(HttpServletRequest request, Boolean erweitertesuche, Boolean suchen,
			String suchbegriff)
	{
		String angebote = "false";
		if (request.getParameter("angebote") != null)
		{
			angebote = request.getParameter("angebote");
		}
		if (suchen)
		{
			return "angebote=" + angebote + "&kategorie=" + session.getAttribute("aktuelleKategorie")
					+ "&erweitertesuche=" + erweitertesuche + "&suchen=" + suchen + "&suchbegriff=" + suchbegriff
					+ "&as=" + session.getAttribute("sortierung_reihenfolge") + "&sn="
					+ session.getAttribute("sortierung_sortierspalte_kuerzel") + "&beschreibung="
					+ request.getParameter("beschreibung") + "&preis_von=" + request.getParameter("preis_von")
					+ "&name=" + request.getParameter("name") + "&produktnummer="
					+ request.getParameter("produktnummer") + "&preis_bis=" + request.getParameter("preis_bis");
		}
		else
		{
			return "angebote=" + angebote + "&kategorie=" + session.getAttribute("aktuelleKategorie")
					+ "&erweitertesuche=" + erweitertesuche + "&suchen=" + suchen + "&suchbegriff=" + suchbegriff
					+ "&as=" + session.getAttribute("sortierung_reihenfolge") + "&sn="
					+ session.getAttribute("sortierung_sortierspalte_kuerzel");
		}
	}

	/**
	 * <p>
	 * Die Methode <code>outParameterLink</code> gibt die <i>GET</i>-Parameter für den zurück. Dieses setzt sich
	 * aus den einzelnen Übergabeparametern und <code>Session</code>-Attributen zusammen.
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param sortierspalte
	 *            - Wert für Produktname (pn) oder Produktpreis (pp)
	 * @return <i>String</i> der Übergabeparameter
	 * @see model#Produktliste
	 */
	public String outParameterLink(HttpServletRequest request, String sortierspalte)
	{
		String angebote = "false";
		if (request.getParameter("angebote") != null)
		{
			angebote = request.getParameter("angebote");
		}
		if (request.getParameter("suchen") != null)
		{
			if (request.getParameter("suchen").equalsIgnoreCase("true"))
			{
				return "angebote=" + angebote + "&kategorie=" + session.getAttribute("aktuelleKategorie")
						+ "&erweitertesuche=" + request.getParameter("erweitertesuche") + "&suchen="
						+ request.getParameter("suchen") + "&suchbegriff=gg&as="
						+ session.getAttribute("sortierung_reihenfolge") + "&sn=" + sortierspalte
						+ "&beschreibung=" + request.getParameter("beschreibung") + "&preis_von="
						+ request.getParameter("preis_von") + "&name=" + request.getParameter("name")
						+ "&produktnummer=" + request.getParameter("produktnummer") + "&preis_bis="
						+ request.getParameter("preis_bis");
			}
			else
			{
				return "angebote=" + angebote + "&kategorie=" + session.getAttribute("aktuelleKategorie")
						+ "&erweitertesuche=" + session.getAttribute("erweitertesuche") + "&suchen="
						+ session.getAttribute("suchen") + "&suchbegriff=zz&as="
						+ session.getAttribute("sortierung_reihenfolge") + "&sn=" + sortierspalte;
			}
		}
		else
		{
			return "angebote=" + angebote + "&kategorie=" + session.getAttribute("aktuelleKategorie")
					+ "&erweitertesuche=" + session.getAttribute("erweitertesuche") + "&suchen="
					+ session.getAttribute("suchen") + "&suchbegriff=tt&as="
					+ session.getAttribute("sortierung_reihenfolge") + "&sn=" + sortierspalte;
		}
	}
}
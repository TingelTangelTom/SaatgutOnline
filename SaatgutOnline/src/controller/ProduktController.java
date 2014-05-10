package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.StringUtils;

import model.ProduktModel;

/**
 * <p>
 * Die Klasse <code>ProduktController</code> liefert R&uuml;ckgabewerte für die Darstellung und Organisation der
 * Produktliste und der Einzelansicht von Produkten Verf&uuml;gung.
 * </p>
 * 
 * @author Simon Ankele
 * @version 1.0
 * @since 1.7.0_51
 */
public class ProduktController
{
	private ProduktModel produktModel;
	private double steuersatz;
	private int sprache_id;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>ProduktController</code>
	 * </p>
	 * <p>
	 * Der Konstruktor erstellt ein <code>ProduktModel</code>-Objekt und speichert das Session-Attribut
	 * <i>spracheId</i> in der Klassenvariablen <i>sprache_id</i>
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public ProduktController(HttpServletRequest request)
	{
		super();
		HttpSession session = request.getSession();
		this.produktModel = new ProduktModel();
		this.sprache_id = (int) session.getAttribute("spracheId");
	}

	/**
	 * <p>
	 * Die Methode <code>getProdukt</code> liest aus der Datenbank die Werte für das Produkt &uuml;ber den
	 * &Uuml;bergabeparameter <i>int id</i> aus und speichert die Werte in ein <code>ProduktModel</code>.
	 * </p>
	 * 
	 * @param id
	 *            - ID des Produktes, über die in der Datenbank gesucht wird
	 * @return <code>ProduktModel</code> - Liefert ein <code>ProduktModel</code> mit allen Inhalten zur&uuml;ck
	 * @see model#ProduktModel
	 */
	public ProduktModel getProdukt(int id)
	{
		try
		{
			String query = "SELECT p.produkt_id, p.produkt_bestand, pb.produkt_name, pb.produkt_beschreibung,"
					+ "pb.produkt_suchbegriffe, p.produkt_angesehen, p.produkt_preis, p.produkt_vpe,"
					+ "p.produkt_steuer_id, p.produkt_datum_hinzugefuegt, p.produkt_datum_geaendert, p.produkt_produktnummer, p.produkt_bild "
					+ "FROM produkt AS p "
					+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
					+ "WHERE pb.sprache_id = '" + this.sprache_id + "' AND p.produkt_id = '" + id + "'";
			ResultSet resultset = DatenbankController.sendeSqlRequest(query);
			while (resultset.next())
			{
				this.produktModel = new ProduktModel();
				this.produktModel.setId(resultset.getInt(1));
				this.produktModel.setBestand(resultset.getInt(2));
				this.produktModel.setName(resultset.getString(3));
				this.produktModel.setBeschreibung(resultset.getString(4));
				this.produktModel.setSuchbegriffe(resultset.getString(5));
				this.produktModel.setAngesehen(resultset.getInt(6));
				this.produktModel.setPreisNetto(runden(resultset.getDouble(7), 2));
				this.produktModel.setVpe(resultset.getInt(8));
				this.produktModel.setSteuerBetrag(resultset.getDouble(9));
				this.produktModel.setHinzugefeugt(resultset.getDate(10));
				this.produktModel.setGeaendert(resultset.getDate(11));
				this.produktModel.setProduktnummer(resultset.getString(12));
				this.produktModel.setBild(resultset.getString(13));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		getProduktMerkmale(id);
		getSteuerinformationen(id);
		getAngebot(id);
		return this.produktModel;
	}

	/**
	 * <p>
	 * Die Methode <code>getProduktliste</code> erstellt eine <code>ArrayList&lt;ProduktModel&gt;</code>.
	 * </p>
	 * <p>
	 * Jedes Produkt hat eine Kategorie ID. Dadurch l&auml;sst sich feststellen, ob sich ein Produkt in einer
	 * Unterkategorie oder in der Hauptkategorie befindet. Falls die Kategorie eine Hauptkategorie ist, werden alle
	 * Produkte der Hauptkategorie und der dazugeh&ouml;rigen Unterkategorien &uuml;ber die Methode
	 * <code>getProdukt()</code> in die <i>ArrayList</i> geschrieben.
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param kategorie_id
	 *            - die Kategorie-ID der aktuellen Kategorie
	 * @return <code>ArrayList&lt;ProduktModel&gt;</code> - liefert eine <code>ArrayList&lt;ProduktModel&gt;</code>
	 *         zur&uuml;ck
	 * @see model#ProduktModel
	 */
	public ArrayList<ProduktModel> getProduktliste(HttpServletRequest request, String kategorie_id)
	{
		HttpSession session = ((HttpServletRequest) request).getSession();
		int kategoriesuche_eltern_id = 0;
		String produkt_query = "";
		String kategorie_query = "SELECT eltern_id FROM kategorie WHERE kategorie_id = '" + kategorie_id + "'";
		try
		{
			ResultSet produkt_resultset = DatenbankController.sendeSqlRequest(kategorie_query);
			while (produkt_resultset.next())
			{
				kategoriesuche_eltern_id = produkt_resultset.getInt(1);
			}
		}
		catch (SQLException e)
		{
		}
		/*
		 * Liefert den passenden produkt_query für die Datenbankabfrage. Die Auswahlmöglichkeiten sind:
		 * Angebotsprodukte, Produkte aus Unterkategorien und alle Produkte einer Eltern-Kategorie
		 */
		String sortierung = request.getParameter("sn");
		String sortierspalte = request.getParameter("as");
		if(StringUtils.isNullOrEmpty(sortierung))
		{
			sortierung = "ASC";
		}
		if(StringUtils.isNullOrEmpty(sortierspalte))
		{
			sortierspalte = "pb.produkt_name";
		}
		if (request.getParameter("angebote") != null && request.getParameter("angebote").equals("true"))
		{
			produkt_query = "SELECT produkt_id FROM angebot WHERE gueltig_bis > now()";
		}
		else
		{
			if (kategoriesuche_eltern_id == 0)
			{
				produkt_query = "SELECT p.produkt_id " + "FROM produkt AS p "
						+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "WHERE pb.sprache_id = '" + this.sprache_id + "' " + "AND  p.kategorie_id IN ("
						+ "SELECT kategorie_id FROM kategorie WHERE eltern_id = '" + kategorie_id
						+ "' OR (kategorie_id = '" + kategorie_id + "' AND eltern_id = 0)" + ") " + "ORDER BY "
						+ sortierspalte + " " + sortierung;
				System.out.println(produkt_query);
			}
			else
			{
				produkt_query = "SELECT p.produkt_id " + "FROM produkt AS p "
						+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
						+ "WHERE pb.sprache_id = '" + this.sprache_id + "' " + "AND  p.kategorie_id = '"
						+ kategorie_id + "' " + "ORDER BY " + sortierspalte + " " + sortierung;
				System.out.println(produkt_query);

			}
		}
		ArrayList<ProduktModel> produkte = new ArrayList<>();
		try
		{
			ResultSet produkt_resultset = DatenbankController.sendeSqlRequest(produkt_query);
			while (produkt_resultset.next())
			{
				produkte.add(this.getProdukt(produkt_resultset.getInt(1)));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return produkte;
	}

	/**
	 * <p>
	 * Die Methode <code>getAlleKategorien</code> erstellt eine <i>HashMap&lt;Integer,String&gt;</i> mit allen
	 * Kategorien aus der Datenbank.
	 * </p>
	 * <p>
	 * Der <i>Integer</i>-Wert der <i>HashMap</i> wird mit der jeweiligen Kategorie-ID und der <i>String</i>-Wert
	 * mit dem Kategorienamen gef&uuml;llt.
	 * </p>
	 * 
	 * @return <i>HashMap&lt;Integer,String&gt;</i> - liefert eine <i>HashMap&lt;Integer,String&gt;</i> mit alle
	 *         Kategorien zur&uuml;ck
	 */
	public HashMap<Integer, String> getAlleKategorien()
	{
		HashMap<Integer, String> kategorien = new HashMap<Integer, String>();
		Integer kategorie_id;
		String wert;
		try
		{
			String query = "SELECT k.kategorie_id, k.eltern_id, kb.kategorie_name " + "FROM kategorie AS k "
					+ "INNER JOIN kategorie_beschreibung AS kb ON k.kategorie_id = kb.kategorie_id "
					+ "WHERE kb.sprache_id = '" + this.sprache_id + "'";
			System.out.println(query);
			ResultSet resultset = DatenbankController.sendeSqlRequest(query);
			if(resultset != null)
			{
				while (resultset.next())
				{
					kategorie_id = resultset.getInt(1);
					if (resultset.getInt(2) > 0)
					{
						wert = "- " + resultset.getString(3);
					}
					else
					{
						wert = resultset.getString(3);
					}
					kategorien.put(kategorie_id, wert);
				}
			}
		}
		catch (SQLException e)
		{
		}
		return kategorien;
	}

	/*
	 * Die Methode schreibt in das ProduktModel die Steuerinformation und errechnet den Brutto-Preis. Der
	 * Übergabeparameter ist die jeweilige Produkt-ID
	 */
	private void getSteuerinformationen(int id)
	{
		try
		{
			String query = "SELECT steuersatz FROM steuersatz WHERE steuersatz_id = "
					+ this.produktModel.getSteuerBetrag();
			ResultSet resultset = DatenbankController.sendeSqlRequest(query);
			if (resultset.next())
			{
				steuersatz = resultset.getDouble(1);
			}
			this.produktModel.setSteuerSatz(steuersatz);
		}
		catch (SQLException e)
		{
		}
		this.produktModel.setPreisBrutto(runden(
				this.produktModel.getPreisNetto() * this.produktModel.getSteuerSatz() / 100
						+ this.produktModel.getPreisNetto(), 2));
		this.produktModel.setSteuerBetrag(runden(
				this.produktModel.getPreisBrutto() - this.produktModel.getPreisNetto(), 2));
	}

	/*
	 * Die Methode schreibt in das ProduktModel die Produktmerkmale. Der Übergabeparameter ist die jeweilige
	 * Produkt-ID
	 */
	private void getProduktMerkmale(int id)
	{
		HashMap<String, String> merkmale = new HashMap<String, String>();
		String name;
		String wert;
		String query = "SELECT pf.produktfelder_name AS name, pfz.produktfelder_wert AS wert "
				+ "FROM produktfelder AS pf "
				+ "LEFT JOIN produktfelder_zuordnung AS pfz ON pfz.produktfelder_id = pf.produktfelder_id "
				+ "WHERE pfz.produkt_id = '" + id + "' " + "AND pfz.produktfelder_wert<>'' "
				+ "AND (pf.sprache_id= '" + this.sprache_id + "' AND pf.sprache_id = '" + this.sprache_id + "') "
				+ "ORDER BY pf.sortier_reihenfolge";
		try
		{
			ResultSet resultset = DatenbankController.sendeSqlRequest(query);
			while (resultset.next())
			{
				name = resultset.getString(1);
				wert = resultset.getString(2);
				merkmale.put(name, wert);
			}
		}
		catch (SQLException e)
		{
		}
		this.produktModel.setMerkmale(merkmale);
	}

	/*
	 * Die Methode schreibt in das ProduktModel den Angebotspreis, falls dieser vorhanden und aktuell ist. Der
	 * Übergabeparameter ist die jeweilige Produkt-ID
	 */
	private void getAngebot(int id)
	{
		try
		{
			String count_query = "SELECT COUNT(a.produkt_id) FROM angebot AS a LEFT JOIN produkt AS p ON p.produkt_id = a.produkt_id WHERE a.produkt_id = '"
					+ id + "' AND a.gueltig_bis > now()";
			
			String produkt_query = "SELECT a.angebotspreis, a.gueltig_bis "
									+ "FROM angebot AS a "
									+ "LEFT JOIN produkt AS p ON p.produkt_id = a.produkt_id "
									+ "WHERE a.produkt_id = '" + id + "' "
									+ "AND a.gueltig_bis > now()";
			
			ResultSet resultset_count = DatenbankController.sendeSqlRequest(count_query);
			if (resultset_count != null)
			{
				ResultSet resultset_produkt = DatenbankController.sendeSqlRequest(produkt_query);
				if (resultset_produkt.next())
				{
					this.produktModel.setPreisEhemalsNetto(this.produktModel.getPreisNetto());
					this.produktModel.setPreisEhemalsBrutto(this.produktModel.getPreisBrutto());
					this.produktModel.setPreisNetto(resultset_produkt.getDouble(1));
					getSteuerinformationen(id);
					
					this.produktModel.setGueltig_bis(resultset_produkt.getDate(2));
				}
			}
		}
		catch (SQLException e)
		{
		}
		this.produktModel.setPreisEhemalsBrutto(runden(this.produktModel.getPreisEhemalsNetto()
				* this.produktModel.getSteuerSatz() / 100 + this.produktModel.getPreisEhemalsNetto(), 2));
	}

	/**
	 * <p>
	 * Die Methode <code>setSortierung(HttpServletRequest request)</code> legt fest, wie Produkte in der
	 * Produktliste-Anschau angezeigt werden.
	 * </p>
	 * <p>
	 * Festgelegt wird, welche Sortierreihenfolge sie haben (DESC | ASC) und nach welchem Kriterium die Anzeige
	 * ausgegeben werden soll (Name oder Preis).
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see model#ProduktlisteView
	 */
	public void setSortierung(HttpServletRequest request)
	{
		HttpSession session = ((HttpServletRequest) request).getSession();
		String erweitertesuche_request = null;
		if (request.getParameter("erweitertesuche") != null)
		{
			erweitertesuche_request = request.getParameter("erweitertesuche");
		}
		else
		{
			erweitertesuche_request = "false";
		}

		if (request.getParameter("sn") != null)
		{
			if (request.getParameter("sprache") == null && erweitertesuche_request.equals(session.getAttribute("erweitertesuche")))
			{
			// Wenn sortierung_sortierspalte bereits auf demselben Wert steht, wechselt die Sortierreihenfolge
				if (request.getParameter("sn").equals(
						(String) session.getAttribute("sortierung_sortierspalte_kuerzel")))
				{
					if (session.getAttribute("sortierung_reihenfolge").equals("DESC"))
					{
						session.setAttribute("sortierung_reihenfolge", "ASC");
					}
					else
					{
						session.setAttribute("sortierung_reihenfolge", "DESC");
					}
				}
				else
				{
					switch (request.getParameter("sn"))
					{
					case "pn":
						session.setAttribute("sortierung_sortierspalte", "pb.produkt_name");
						session.setAttribute("sortierung_sortierspalte_kuerzel", "pn");
						break;
					case "pp":
						session.setAttribute("sortierung_sortierspalte", "p.produkt_preis");
						session.setAttribute("sortierung_sortierspalte_kuerzel", "pp");
						break;
					default:
						session.setAttribute("sortierung_sortierspalte", "pb.produkt_name");
						session.setAttribute("sortierung_sortierspalte_kuerzel", "pn");
						break;
					}
				}
			}
			session.setAttribute("erweitertesuche", request.getParameter("erweitertesuche"));
		}
	}

	/*
	 * Die Methode rundet den Übergabeparameter 'wert' auf eine bestimmte Anzahl von Stellen nach dem Komma, welche
	 * durch den Übergabeparameter 'stellen bestimmt wird.
	 */
	private static double runden(double wert, int stellen)
	{
		double gerundet = Math.round(wert * Math.pow(10d, stellen));
		return gerundet / Math.pow(10d, stellen);
	}
}
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ProduktModel;

/**
 * <p>
 * Die Klasse <code>SucheController</code> liefert R&uuml;ckgabewerte für die Darstellung und Organisation der
 * Produktliste zur Verf&uuml;gung.
 * </p>
 * 
 * @author Simon Ankele
 * @version 1.0
 * @since 1.7.0_51
 */
public class SucheController
{
	private ProduktController produktController;
	private int sprache_id;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>SucheController</code>
	 * </p>
	 * <p>
	 * Der Konstruktor erstellt ein <code>ProduktController</code>-Objekt und speichert das Session-Attribut
	 * <i>spracheId</i> in der Klassenvariablen <i>sprache_id</i>
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public SucheController(HttpServletRequest request)
	{
		super();
		HttpSession session = request.getSession();
		this.sprache_id = (int) session.getAttribute("spracheId");
		this.produktController = new ProduktController(request);
	}

	/**
	 * <p>
	 * Die Methode <code>getProduktliste</code> liest aus dem <code>HttpServletRequest</code> die
	 * <i>GET</i>-Parameter aus und f&uuml;gt diese die die <i>Select</i>-Anweisung ein. Die ausgegeben
	 * Datens&auml;tze werden in einer <code>ArrayList&lt;ProduktModel&gt;</code> gespeichert und
	 * zur&uuml;ckgegeben.
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @return <code>ArrayList&lt;ProduktModel&gt;</code> - liefert eine <code>ArrayList&lt;ProduktModel&gt;</code>
	 *         zur&uuml;ck
	 * @see javax.servlet.http.HttpServletRequest
	 * @see model#ProduktModel
	 */
	public ArrayList<ProduktModel> getProduktliste(HttpServletRequest request)
	{
		HttpSession session = ((HttpServletRequest) request).getSession();
		int kategorie = 0;
		if (request.getParameter("kategorie") != null)
		{
			kategorie = Integer.parseInt(request.getParameter("kategorie"));
		}
		int preis_von = 0;
		int preis_bis = 0;
		String name = "";
		String beschreibung = "";
		String produktnummer = "";
		if (request.getParameter("name") != null)
		{
			name = request.getParameter("name");
		}
		else
		{
			if (request.getParameter("suchbegriff") != null)
			{
				name = request.getParameter("suchbegriff");	
			}
		}
		if (!request.getParameter("suchbegriff").equalsIgnoreCase("null"))
		{
			if (request.getParameter("beschreibung") != null)
			{
				beschreibung = request.getParameter("beschreibung");
			}
			if (request.getParameter("produktnummer") != null)
			{
				produktnummer = request.getParameter("produktnummer");
			}
			if (request.getParameter("preis_von") != null)
			{
				if (!request.getParameter("preis_von").equals(""))
				{
					preis_von = Integer.parseInt(request.getParameter("preis_von")); 
				}
			}
			if (request.getParameter("preis_bis") != null)
			{
				if (!request.getParameter("preis_bis").equals(""))
				{
					preis_von = Integer.parseInt(request.getParameter("preis_bis")); 
				}
			}
		}
		String produkt_query;
		ArrayList<ProduktModel> produkte = new ArrayList<>();
		produkt_query = "SELECT DISTINCT p.produkt_id " + "FROM produkt AS p "
				+ "INNER JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
				+ "INNER JOIN kategorie_beschreibung AS kb ON p.kategorie_id = kb.kategorie_id "
				+ "WHERE pb.sprache_id = '" + this.sprache_id + "' ";
		if (kategorie > 0)
		{
			produkt_query += "AND p.kategorie_id  IN (SELECT kategorie_id FROM kategorie WHERE eltern_id = '"
					+ kategorie + "' OR (kategorie_id = '" + kategorie + "' AND eltern_id = 0)) ";
		}
		if (!name.equals(""))
		{
			produkt_query += "AND MATCH (pb.produkt_name) AGAINST ('" + name + "') ";
		}
		if (!beschreibung.equals(""))
		{
			produkt_query += "AND MATCH (pb.produkt_beschreibung) AGAINST ('" + beschreibung + "') ";
		}
		if (!produktnummer.equals(""))
		{
			produkt_query += "AND p.produkt_produktnummer = '" + produktnummer + "' ";
		}
		if (preis_von >= 0 && preis_bis > 0 && preis_von < preis_bis)
		{
			produkt_query += "AND p.produkt_preis BETWEEN " + preis_von + " AND " + preis_bis;
		}
		produkt_query += "ORDER BY " + session.getAttribute("sortierung_sortierspalte") + " "
				+ session.getAttribute("sortierung_reihenfolge") + " " + "LIMIT "
				+ session.getAttribute("sortierung_limit_von") + ","
				+ session.getAttribute("sortierung_produktanzahl") + "";
		try
		{
			ResultSet produkt_resultset = DatenbankController.sendeSqlRequest(produkt_query);
			while (produkt_resultset.next())
			{
				produkte.add(this.produktController.getProdukt(produkt_resultset.getInt(1)));
			}
		}
		catch (SQLException e)
		{
		}
		return produkte;
	}
}
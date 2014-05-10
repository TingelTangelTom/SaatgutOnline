package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.StringUtils;

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

		String kategorie = request.getParameter("kategorie");
		String preis_von = request.getParameter("preis_von");
		String preis_bis = request.getParameter("preis_bis");
		String name = request.getParameter("name");
		String beschreibung = request.getParameter("beschreibung");
		String produktnummer = request.getParameter("produktnummer");
		if(StringUtils.isNullOrEmpty(preis_von) || !Pattern.matches( "[0-9]+", preis_von))
		{
			preis_von = "0";
		}
		if(StringUtils.isNullOrEmpty(preis_bis) || !Pattern.matches( "[0-9]+", preis_bis))
		{
			preis_bis = "0";
		}
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
		String produkt_query;
		ArrayList<ProduktModel> produkte = new ArrayList<>();
		produkt_query = "SELECT DISTINCT p.produkt_id " + "FROM produkt AS p "
				+ "LEFT JOIN produkt_beschreibung AS pb ON p.produkt_id = pb.produkt_id "
				+ "LEFT JOIN kategorie_beschreibung AS kb ON p.kategorie_id = kb.kategorie_id "
				+ "WHERE pb.produkt_name LIKE '%" + name + "%' ";
		if (!StringUtils.isNullOrEmpty(kategorie) && Integer.parseInt(kategorie) > 0)
		{
			produkt_query += "AND p.kategorie_id  IN (SELECT kategorie_id FROM kategorie WHERE eltern_id = '"
					+ kategorie + "' OR (kategorie_id = '" + kategorie + "' AND eltern_id = 0)) ";
		}
		if (!StringUtils.isNullOrEmpty(beschreibung))
		{
			produkt_query += "AND pb.produkt_beschreibung LIKE '%" + beschreibung + "%' ";
		}
		if (!StringUtils.isNullOrEmpty(produktnummer))
		{
			produkt_query += "AND p.produkt_produktnummer LIKE '%" + produktnummer + "%' ";
		}	
		if (Integer.parseInt(preis_von) >= 0 && Integer.parseInt(preis_bis) > 0 && Integer.parseInt(preis_von) < Integer.parseInt(preis_bis))
		{
			produkt_query += "AND p.produkt_preis BETWEEN " + preis_von + " AND " + preis_bis + " ";
		}
		produkt_query += "ORDER BY " + sortierspalte + " " + sortierung;
		try
		{
			ResultSet produkt_resultset = DatenbankController.sendeSqlRequest(produkt_query);
			if(produkt_resultset != null)
			{
				while (produkt_resultset.next())
				{
					produkte.add(this.produktController.getProdukt(produkt_resultset.getInt(1)));
				}
			}
		}
		catch (SQLException e)
		{
		}
		return produkte;
	}
}
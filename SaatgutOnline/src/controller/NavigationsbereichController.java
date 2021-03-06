package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.KategorieModel;
import view.NavigationsbereichView;

/**
 * <p>
 * Die Klasse <code>NavigationsbereichController</code> stellt Kontrollstrukturen zur Darstellung und Organisation
 * des Navigationsbereichs zur Verfuegung.
 * </p>
 * 
 * @author Tom Weigelt
 * @version 1.0
 * @since 1.7.0_51
 */
public class NavigationsbereichController
{
	private NavigationsbereichView navigationsbereichView;
	private HttpSession session;
	private HttpServletRequest request;
	private KategorieModel kategorieModel;
	private ArrayList<Integer> geklickteKategorienSession;
	private int aktuelleKategorieSession;
	private ArrayList<KategorieModel> kategorienArrayList = new ArrayList<KategorieModel>();

	/**
	 * <p>
	 * Konstruktor der Klasse <code>NavigationsbereichController</code>
	 * </p>
	 * <p>
	 * Die aufgerufene Methode <code>geklickteKategorienAktualisieren()</code> organisiert eine in der Session
	 * abgelegte<code>ArrayList</code>, in der die IDs der geklickten Kategorien beim ersten Klick eingetragen und
	 * beim zweiten darauf Klick wieder entfernt werden. Findet sich eine Hauptkategorie in dieser Liste, so werden
	 * in der Kategorien-Liste auch ihre Unterkategorien angezeigt. </br></br>Desweiteren wird der <code>int</code>
	 * <i>aktuelleKategorie</i> in der Session aktualisiert - und zwar immer mit der ID der zuletzt geklickten
	 * Kategorie. Die Kategorie, deren ID diesem Wert gleicht, wird farblich hervorgehoben dargestellt.
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @param ausGet
	 *            - <code>boolean</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 * @see servlets.NavigationsbereichServlet
	 */
	public NavigationsbereichController(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.session = request.getSession();
		this.navigationsbereichView = new NavigationsbereichView(request, response);
		this.kategorienArrayList = kategorienAusDbHolen();
		this.geklickteKategorienAktualisieren();
	}

	/**
	 * <p>
	 * Formatiert durch den Aufruf von Methoden der Klasse <code>NavigationsbereichView</code> die Darstellung des
	 * Navigationsbereichs und gibt diese aus.
	 * </p>
	 * 
	 * @see view.NavigationsbereichView
	 */
	public void navigationsbereichAnzeigen()
	{
		this.navigationsbereichView.outNavigationsbereichAnfang();
		if ((int) this.session.getAttribute("aktuelleKategorie") == 0)
		{
			this.navigationsbereichView.outAngeboteAktuell();
		}
		else
		{
			this.navigationsbereichView.outAngebote();
		}
		this.navigationsbereichView.outKategorienListeAnfang();
		this.kategorienListeAnzeigen();
		this.navigationsbereichView.outKategorienListeEnde();
		this.navigationsbereichView.outNavigationsbereichEnde();
	}

	private void kategorienListeAnzeigen()
	{
		int hauptKategorieId = 0;
		for (int i = 0; i < kategorienArrayList.size(); i++)
		{
			this.kategorieModel = this.kategorienArrayList.get(i);
			if (this.kategorieModel.getElternKategorieId() == 0)
			{
				if (this.aktuelleKategorieSession == this.kategorieModel.getKategorieId())
				{
					this.navigationsbereichView.outHauptKategorieAktuellAnzeigen(kategorieModel);
				}
				else
				{
					this.navigationsbereichView.outHauptKategorieAnzeigen(this.kategorieModel);
				}
				hauptKategorieId = this.kategorieModel.getKategorieId();
				if (this.geklickteKategorienSession.contains(hauptKategorieId))
				{
					for (int j = 0; j < kategorienArrayList.size(); j++)
					{
						this.kategorieModel = this.kategorienArrayList.get(j);
						if (this.kategorieModel.getElternKategorieId() != 0)
						{
							if (this.kategorieModel.getElternKategorieId() == hauptKategorieId)
							{
								if (this.aktuelleKategorieSession == this.kategorieModel.getKategorieId())
								{
									this.navigationsbereichView.outUnterKategorieAktuellAnzeigen(kategorieModel);
								}
								else
								{
									this.navigationsbereichView.outUnterKategorieAnzeigen(this.kategorieModel);
								}
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void geklickteKategorienAktualisieren()
	{
		if (this.session.getAttribute("geklickteKategorien") != null)
		{
			this.geklickteKategorienSession = (ArrayList<Integer>) this.session
					.getAttribute("geklickteKategorien");
		}
		else
		{
			this.geklickteKategorienSession = new ArrayList<Integer>();
		}
		if (this.session.getAttribute("aktuelleKategorie") != null)
		{
			this.aktuelleKategorieSession = (Integer) this.session.getAttribute("aktuelleKategorie");
		}
		else
		{
			this.aktuelleKategorieSession = 0;
		}
		if ((this.request.getParameter("angebote") != null)
				&& this.request.getParameter("angebote").contains("true"))
		{
			this.aktuelleKategorieSession = 0;
		}
		if ((this.request.getParameter("kategorie") != null)
				&& (this.request.getParameter("erweitertesuche") == null)
				&& (this.request.getParameter("sprache") == null))
		{
			Integer geklickteKategorie = Integer.parseInt(this.request.getParameter("kategorie"));
			if ((this.geklickteKategorienSession.contains(geklickteKategorie))
					&& (this.aktuelleKategorieSession == geklickteKategorie))
			{
				this.geklickteKategorienSession
						.remove(this.geklickteKategorienSession.indexOf(geklickteKategorie));
			}
			else
			{
				if (!this.geklickteKategorienSession.contains(geklickteKategorie))
				{
					this.geklickteKategorienSession.add(geklickteKategorie);
				}
			}
			this.aktuelleKategorieSession = geklickteKategorie;
		}
		this.session.setAttribute("geklickteKategorien", this.geklickteKategorienSession);
		this.session.setAttribute("aktuelleKategorie", this.aktuelleKategorieSession);
	}

	private ArrayList<KategorieModel> kategorienAusDbHolen()
	{
		int spracheId = (int) this.session.getAttribute("spracheId");
		String query = "SELECT k.kategorie_id, k.eltern_id, kb.kategorie_name "
				+ "FROM kategorie AS k INNER JOIN kategorie_beschreibung AS kb "
				+ "ON k.kategorie_id = kb.kategorie_id " + "WHERE sprache_id = '" + spracheId + "' "
				+ "ORDER BY k.sortier_reihenfolge";
		// Die nachfolgende Zeile nutzt Code von Christof Weigand
		ResultSet resultSet = DatenbankController.sendeSqlRequest(query);
		try
		{
			while (resultSet.next())
			{
				this.kategorieModel = new KategorieModel();
				this.kategorieModel.setKategorieId(resultSet.getInt("kategorie_id"));
				this.kategorieModel.setKategorieName(resultSet.getString("kategorie_name"));
				this.kategorieModel.setElternKategorieId(resultSet.getInt("eltern_id"));
				this.kategorienArrayList.add(this.kategorieModel);
			}
		}
		catch (SQLException e)
		{
		}
		return this.kategorienArrayList;
	}
}
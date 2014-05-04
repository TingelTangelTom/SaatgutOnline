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
 * Die Klasse Navigationsbereich stellt Kontrollstrukturen fuer die Darstellung
 * und Organisation des Navigationsbereichs zur Verfuegung
 * @author Tom Weigelt
 *
 */
public class NavigationsbereichController
{
	/**
	 * Objekt der Klasse <code>NavigationsbereichView</class>
	 * @see view.NavigationsbereichView
	 */
	private NavigationsbereichView navigationsbereichView;
	
	/**
	 * aktuelle <code>HttpSession</code>
	 * @see javax.servlet.http.HttpSession
	 */
	private HttpSession session;
	
	/**
	 * Objekt der Klasse <code>HttpServletRequest</code>
	 * @see javax.servlet.http.HttpServletRequest
	 */
	private HttpServletRequest request;		
	
	/**
	 * Objekt der Klasse <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	private KategorieModel kategorieModel;
	
	/**
	 * Sammlung von Kategorie-Ids der geklickten Kategorien als <code>ArrayList</code> zur Ablage in der <code>HttpSession</code>
	 */
	private ArrayList<Integer> geklickteKategorienSession;
	
	/**
	 * aktuell gelickte Kategorie als <code>int</code> zur Ablage in der Session
	 */
	private int aktuelleKategorieSession;
	
	/**
	 * Sammlung des Formats <code>ArrayList</code>
	 * </br>Enthaelt Objekte der Klasse <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	private ArrayList<KategorieModel> kategorienArrayList = new ArrayList<KategorieModel>();

	//TODO ausGet checken!
	/**
	 * Konstruktor der Klasse <code>NavigationsbereichController</code>
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @param response - die aktuelle <code>HttpServletResponse</code>
	 * @param ausGet - <code>boolean</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public NavigationsbereichController(HttpServletRequest request, HttpServletResponse response, boolean ausGet)
	{
		this.request = request;
		this.session = request.getSession();
		this.navigationsbereichView = new NavigationsbereichView(response);
		this.kategorienArrayList = getKategorienAusDB();
		this.geklickteKategorienAktualisieren(ausGet);
	}

	/**
	 * Gibt die Darstellung fuer den Navigationsbereich aus
	 */
	public void navigationsbereichAnzeigen()
	{
		this.navigationsbereichView.outNavigationsbereichAnfang();
		this.navigationsbereichView.outKategorienListeAnfang();
		
		this.kategorienListeAnzeigen();

		this.navigationsbereichView.outKategorienListeEnde();
		this.navigationsbereichView.outNavigationsbereichEnde();
	}
	
	/**
	 * Formatiert die Darstellung fuer die Kategorienliste im Kopfbereich und gibt diese aus
	 */
	private void kategorienListeAnzeigen()
	{			
		int hauptKategorieId = 0;
		
		for (int i = 0; i < kategorienArrayList.size(); i++)
		{			
			this.kategorieModel = this.kategorienArrayList.get(i);
			
			if(this.kategorieModel.getElternKategorieId() == 0)
			{

				if(this.aktuelleKategorieSession == this.kategorieModel.getKategorieId())
				{
					this.navigationsbereichView.outHauptKategorieAktuellAnzeigen(kategorieModel);
				}
				else
				{				
					this.navigationsbereichView.outHauptKategorieAnzeigen(this.kategorieModel);
				}
		
				hauptKategorieId = this.kategorieModel.getKategorieId();
				
				if(this.geklickteKategorienSession.contains(hauptKategorieId))
				{
					for (int j = 0; j < kategorienArrayList.size(); j++)
					{
						this.kategorieModel = this.kategorienArrayList.get(j);
						
						if(this.kategorieModel.getElternKategorieId() != 0)
						{
							if(this.kategorieModel.getElternKategorieId() == hauptKategorieId)
							{				
								if(this.aktuelleKategorieSession == this.kategorieModel.getKategorieId())
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
	
	/**
	 * Aktualisiert die Sammlung geklickter Kategorien in der <code>HttpSession</code>
	 * @param ausGet - boolean
	 */
	@SuppressWarnings("unchecked")
	private void geklickteKategorienAktualisieren(boolean ausGet)
	{				
		Integer geklickteKategorie;
		
		if(this.session.getAttribute("geklickteKategorien") != null)
		{
			this.geklickteKategorienSession = (ArrayList<Integer>) this.session.getAttribute("geklickteKategorien");			
		}
		else
		{
			this.geklickteKategorienSession = new ArrayList<Integer>();	
		}
		
		if(this.session.getAttribute("aktuelleKategorie") != null)
		{
			this.aktuelleKategorieSession = (int) this.session.getAttribute("aktuelleKategorie");
		}
		else
		{
			this.aktuelleKategorieSession = 0;			
		}
		
		
		//FIXME Aufklapp-BUG
		if(ausGet							
				&& (this.request.getParameter("kategorie") != null)				
				&& (this.request.getParameter("p_anzeige") == null))
		{			
			geklickteKategorie = Integer.parseInt(this.request.getParameter("kategorie"));
			this.aktuelleKategorieSession = geklickteKategorie;
			
			if(this.geklickteKategorienSession.contains(geklickteKategorie))
			{
				this.geklickteKategorienSession.remove(geklickteKategorie);				
			}
			else
			{
				this.geklickteKategorienSession.add(geklickteKategorie);
			}
			
			this.session.setAttribute("geklickteKategorien", this.geklickteKategorienSession);			
			this.session.setAttribute("aktuelleKategorie", this.aktuelleKategorieSession);
		}
	}
	

	/**
	 * Holt die verfuegbaren <code>KategorieModel</code> aus der Datenbank und legt eine Sammlung vom Typ <code>ArrayList</code> an.
	 * @return ArrayList - beinhaltet alle verfuegbaren <code>KategorieModel</code>
	 * @see model.KategorieModel
	 */
	private ArrayList<KategorieModel> getKategorienAusDB()
	{
			int spracheId = (int) this.session.getAttribute("spracheId");
						
			String query = "SELECT k.kategorie_id, k.eltern_id, kb.kategorie_name "
					+ "FROM kategorie AS k INNER JOIN kategorie_beschreibung AS kb "
					+ "ON k.kategorie_id = kb.kategorie_id "
					+ "WHERE sprache_id = '" + spracheId + "' "
					+ "ORDER BY k.sortier_reihenfolge";
			
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
			} catch (SQLException e)
			{
				System.out.println("DB-Fehler: ResultSet NavigationsbereichController");
				e.printStackTrace();
			}
		
		return this.kategorienArrayList;
	}
}

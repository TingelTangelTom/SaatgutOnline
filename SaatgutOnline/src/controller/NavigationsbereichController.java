package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.KategorieModel;
import view.NavigationsbereichView;

public class NavigationsbereichController
{
	private NavigationsbereichView navigationsbereichView;
	private HttpSession session;
	private HttpServletRequest request;
	private int hauptKategorieId = 0;	
	private KategorieModel kategorieModel;	
	private ArrayList<Integer> geklickteKategorienSession;
	private Integer geklickteKategorieGet;
	private int aktuelleKategorieSession;
	private ArrayList<KategorieModel> kategorienArrayList = new ArrayList<KategorieModel>();
	private boolean getMethode;
	


	public NavigationsbereichController(HttpServletRequest request, HttpServletResponse response, boolean getMethode)
	{
		this.getMethode = getMethode;
		this.request = request;
		this.session = request.getSession();
		this.navigationsbereichView = new NavigationsbereichView(response);
		this.kategorienArrayList = kategorienAusDB();
		this.geklickteKategorienOrganisieren();
	}

	public void navigationsbereichAnzeigen()
	{
		this.navigationsbereichView.outNavigationsbereichAnfang();
		this.navigationsbereichView.outKategorienListeAnfang();
		
		this.kategorienListeAnzeigen();

		this.navigationsbereichView.outKategorienListeEnde();
		this.navigationsbereichView.outNavigationsbereichEnde();
	}
	
	private void kategorienListeAnzeigen()
	{			
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
		
				this.hauptKategorieId = this.kategorieModel.getKategorieId();
				
				if(this.geklickteKategorienSession.contains(this.hauptKategorieId))
				{
					for (int j = 0; j < kategorienArrayList.size(); j++)
					{
						this.kategorieModel = this.kategorienArrayList.get(j);
						
						if(this.kategorieModel.getElternKategorieId() != 0)
						{
							if(this.kategorieModel.getElternKategorieId() == this.hauptKategorieId)
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
	
	
	@SuppressWarnings("unchecked")
	private void geklickteKategorienOrganisieren()
	{		
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
		
		
		
		if(getMethode && this.request.getParameter("kategorie") != null)
		{			
			this.geklickteKategorieGet = Integer.parseInt(this.request.getParameter("kategorie"));
			this.aktuelleKategorieSession = this.geklickteKategorieGet;
			
			if(this.geklickteKategorienSession.contains(this.geklickteKategorieGet))
			{
				this.geklickteKategorienSession.remove(this.geklickteKategorieGet);				
			}
			else
			{
				this.geklickteKategorienSession.add(this.geklickteKategorieGet);
			}
			
			this.session.setAttribute("geklickteKategorien", this.geklickteKategorienSession);			
			this.session.setAttribute("aktuelleKategorie", this.aktuelleKategorieSession);
		}
		
	}
	

	private ArrayList<KategorieModel> kategorienAusDB()
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
					
					kategorienArrayList.add(this.kategorieModel);
				}
			} catch (SQLException e)
			{
				System.out.println("DB-Fehler: ResultSet NavigationsbereichController");
				e.printStackTrace();
			}
		

		return kategorienArrayList;
	}
}

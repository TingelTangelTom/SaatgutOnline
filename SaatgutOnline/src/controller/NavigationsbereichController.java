package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
//TODO Debug-Ausgabe loeschen!!		
System.out.println("\n---NavigationsbereichController---");
		
		this.getMethode = getMethode;
		this.request = request;
		this.session = request.getSession();
		this.navigationsbereichView = new NavigationsbereichView(request, response);
		this.kategorienArrayList = kategorienAusDB();
		this.geklickteKategorienOrganisieren();

		
//TODO Debug-Ausgabe loeschen!!
System.out.println("----------------------------------");
	}

	public void outNavigationsbereichAnzeigen()
	{
		this.navigationsbereichView.outNavigationsbereichAnfang();
		this.navigationsbereichView.outKategorienListeAnfang();
		
		this.outKategorienListeAnzeigen();

		this.navigationsbereichView.outKategorienListeEnde();
		this.navigationsbereichView.outNavigationsbereichEnde();
	}
	
	private void outKategorienListeAnzeigen()
	{			
		for (int i = 0; i < kategorienArrayList.size(); i++)
		{			
			this.kategorieModel = this.kategorienArrayList.get(i);
			
			if(this.kategorieModel.getElternKategorieId() == 0)
			{
				//TODO remove
				System.out.println("Haupt: "+this.aktuelleKategorieSession +" == "+ this.kategorieModel.getKategorieId());
				if(this.aktuelleKategorieSession == this.kategorieModel.getKategorieId())
				{
					this.navigationsbereichView.outHauptKategorieAktuellAnzeigen(kategorieModel);
					//TODO remove
					System.out.println("****JA haupt****" + kategorieModel.getKategorieId());
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
								//TODO remove
								System.out.println("Unter: "+this.aktuelleKategorieSession +" == "+ this.kategorieModel.getKategorieId());
								if(this.aktuelleKategorieSession == this.kategorieModel.getKategorieId())
								{
									this.navigationsbereichView.outUnterKategorieAktuellAnzeigen(kategorieModel);
									//TODO remove
									System.out.println("****JA unter****" + kategorieModel.getKategorieId());
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
		if(this.session.getAttribute("geklickteKategorien") == null)
		{
			this.geklickteKategorienSession = new ArrayList<Integer>();	
		}
		else
		{
			this.geklickteKategorienSession = (ArrayList<Integer>) this.session.getAttribute("geklickteKategorien");			
		}
		
		if(this.session.getAttribute("aktuelleKategorie") == null)
		{
			this.aktuelleKategorieSession = 0;
		}
		else
		{
			this.aktuelleKategorieSession = (int) this.session.getAttribute("aktuelleKategorie");
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
			
			// letzte geklickte Kategorie in Session hinterlegen			
			this.session.setAttribute("aktuelleKategorie", this.aktuelleKategorieSession);
		}
		

		
		
		
		
	}
	

	
	private ArrayList<KategorieModel> kategorienAusDB()
	{
		DatenbankController.getVerbindung();

		try
		{
			int spracheId = (int) this.session.getAttribute("spracheId");
			
			String query = "SELECT k.kategorie_id, k.eltern_id, kb.kategorie_name "
					+ "FROM kategorie AS k INNER JOIN kategorie_beschreibung AS kb "
					+ "ON k.kategorie_id = kb.kategorie_id "
					+ "WHERE sprache_id = '" + spracheId + "' "
					+ "ORDER BY k.sortier_reihenfolge";
			
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			while (resultset.next())
			{
				this.kategorieModel = new KategorieModel();
				
				this.kategorieModel.setKategorieId(resultset.getInt("kategorie_id"));
				this.kategorieModel.setKategorieName(resultset.getString("kategorie_name"));
				this.kategorieModel.setElternKategorieId(resultset.getInt("eltern_id"));
				
				
				kategorienArrayList.add(this.kategorieModel);
			}
		} catch (SQLException e)
		{
			System.out.println("Datenbankzugriff nicht erfolgt!");
			e.printStackTrace();
		}

		return kategorienArrayList;
	}
	
	

	


}

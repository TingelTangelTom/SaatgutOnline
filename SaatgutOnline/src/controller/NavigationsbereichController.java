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
	private ArrayList<Integer> geklickteKategorienInSession;
	private ArrayList<KategorieModel> kategorienArrayList = new ArrayList<KategorieModel>();
	private boolean getMethode;


	public NavigationsbereichController(HttpServletRequest request, HttpServletResponse response, boolean postMethode)
	{
//TODO Debug-Ausgabe loeschen!!		
System.out.println("\n---NavigationsbereichController---");
		
		this.getMethode = postMethode;
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
				this.navigationsbereichView.outHauptKategorieAnzeigen(this.kategorieModel);
				
				this.hauptKategorieId = this.kategorieModel.getKategorieId();
				
				if(this.geklickteKategorienInSession.contains(this.hauptKategorieId))
				{
					for (int j = 0; j < kategorienArrayList.size(); j++)
					{
						this.kategorieModel = this.kategorienArrayList.get(j);
						
						if(this.kategorieModel.getElternKategorieId() != 0)
						{
							if(this.kategorieModel.getElternKategorieId() == this.hauptKategorieId)
							{													
								this.navigationsbereichView.outUnterKategorieAnzeigen(this.kategorieModel);					
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
			this.geklickteKategorienInSession = new ArrayList<Integer>();
		}
		else
		{
			this.geklickteKategorienInSession = (ArrayList<Integer>) this.session.getAttribute("geklickteKategorien");
		}
		
		if(getMethode && this.request.getParameter("kategorie") != null)
		{			
			Integer geklickteKategorieAusGetMethode = Integer.parseInt(this.request.getParameter("kategorie"));
						
			if(this.geklickteKategorienInSession.contains(geklickteKategorieAusGetMethode))
			{
				this.geklickteKategorienInSession.remove(geklickteKategorieAusGetMethode);				
			}
			else
			{
				this.geklickteKategorienInSession.add(geklickteKategorieAusGetMethode);
			}
			
			this.session.setAttribute("geklickteKategorien", this.geklickteKategorienInSession);
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

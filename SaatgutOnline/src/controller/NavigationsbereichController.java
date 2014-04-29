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
	private int hauptKategorie = 0;	
	private KategorieModel kategorieModel;	
	private ArrayList<Integer> geklickteKategorien;
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
		this.navigationsbereichView.outNavigationsabereichAnfang();
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
				
				this.hauptKategorie = this.kategorieModel.getKategorieId();
				
				if(this.geklickteKategorien.contains(this.hauptKategorie))
				{
					for (int j = 0; j < kategorienArrayList.size(); j++)
					{
						this.kategorieModel = this.kategorienArrayList.get(j);
						
						if(this.kategorieModel.getElternKategorieId() != 0)
						{
							if(this.kategorieModel.getElternKategorieId() == this.hauptKategorie)
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
			this.geklickteKategorien = new ArrayList<Integer>();
		}
		else
		{
			this.geklickteKategorien = (ArrayList<Integer>) this.session.getAttribute("geklickteKategorien");
		}
		
		if(getMethode && this.request.getParameter("kategorie") != null)
		{
			Integer geklickteKategorie = Integer.parseInt(this.request.getParameter("kategorie"));
						
			if(this.geklickteKategorien.contains(geklickteKategorie))
			{
				this.geklickteKategorien.remove(geklickteKategorie);
				
				//TODO remove
				System.out.println("remove geklickte Kategorie");
			}
			else
			{
				this.geklickteKategorien.add(geklickteKategorie);

				//TODO remove
				System.out.println("add geklickte Kategorie");
			}
			
			this.session.setAttribute("geklickteKategorien", this.geklickteKategorien);
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
//					+ "WHERE sprache_id = '2' "
					+ "ORDER BY k.sortier_reihenfolge";
			
//TODO Debug-Ausgabe loeschen!!			
System.out.println(query);
			
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

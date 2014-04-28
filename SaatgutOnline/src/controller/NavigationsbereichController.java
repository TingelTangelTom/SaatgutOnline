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
	private ArrayList<String> geklickteKategorien;
	private ArrayList<KategorieModel> kategorienArrayList = new ArrayList<KategorieModel>();


	public NavigationsbereichController(HttpServletRequest request, HttpServletResponse response)
	{
//TODO Debug-Ausgabe loeschen!!		
System.out.println("\n---NavigationsbereichController---");
		
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
				this.navigationsbereichView.outHauptKategorieAnzeigen(kategorieModel);
				this.hauptKategorie = this.kategorieModel.getKategorieId();
				
				if(this.geklickteKategorien.contains(this.kategorieModel.getKategorieName()))
				{
					for (int j = 0; j < kategorienArrayList.size(); j++)
					{
						this.kategorieModel = this.kategorienArrayList.get(j);
						if(this.kategorieModel.getElternKategorieId() != 0)
						{
							if(this.kategorieModel.getElternKategorieId() == hauptKategorie)
							{													
								this.navigationsbereichView.outUnterKategorieAnzeigen(kategorieModel);					
							}
						}
					}
				}
			}			
		}		
	}
	
	

	
	private ArrayList<KategorieModel> kategorienAusDB()
	{
		DatenbankController.getVerbindung();

		try
		{
//			int spracheId = (int) this.session.getAttribute("spracheId");
			
			
			String query = "SELECT kategorie_id, kategorie_name, eltern_id, sortier_reihenfolge "
					+ "FROM kategorie "
//					+ "WHERE sprache_id = '" + spracheId + "'";
					+ "WHERE sprache_id = '2'";
			
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
				this.kategorieModel.setSortierReihenfolge(resultset.getInt("sortier_reihenfolge"));
				
				kategorienArrayList.add(this.kategorieModel);
			}
		} catch (SQLException e)
		{
			System.out.println("Datenbankzugriff nicht erfolgt!");
			e.printStackTrace();
		}

		return kategorienArrayList;
	}
	
	
	@SuppressWarnings("unchecked")
	private void geklickteKategorienOrganisieren()
	{
		if(this.session.getAttribute("kategorien") == null)
		{
			this.geklickteKategorien = new ArrayList<String>();
		}
		else
		{
			this.geklickteKategorien = (ArrayList<String>) this.session.getAttribute("kategorien");
		}
		
		if(this.request.getParameter("hauptkategorie") != null)
		{
			String kategorie = this.request.getParameter("hauptkategorie");
			if(geklickteKategorien.contains(kategorie))
			{
				geklickteKategorien.remove(kategorie);
				
				//TODO remove
				System.out.println("remove geklickte Kategorie");
			}
			else
			{
				geklickteKategorien.add(kategorie);

				//TODO remove
				System.out.println("add geklickte Kategorie");
			}
			
			this.session.setAttribute("kategorien", geklickteKategorien);
		}
	}

}

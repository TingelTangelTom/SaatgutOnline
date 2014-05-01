package controller;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProduktModel;
import view.WarenkorbView;

public class WarenkorbController
{	
	private HttpSession session;	
	private Hashtable<ProduktModel, Integer> warenkorb;
	private WarenkorbView warenkorbView;
	
	// GET-Kostruktor
	public WarenkorbController(HttpServletRequest request, HttpServletResponse response)
	{		
		//TODO remove
		System.out.println("---WarenkorbController---");
		
		this.session = request.getSession();
		this.warenkorbView = new WarenkorbView(request, response);
		this.warenkorbAusSessionHolen();
		this.warenkorbAktualisieren(request);
		this.warenkorbInSessionSchreiben();		

		//TODO remove
		System.out.println("-------------------------");				
	}
	
	
	public void warenkorbAnzeigen()
	{
		double gesamtgewicht = 0;
		double zwischensumme = 0;
		
		this.warenkorbView.outWarenkorbAnfang();
				
		if(! this.warenkorb.isEmpty())
		{
			Enumeration<ProduktModel> produkte = this.warenkorb.keys();
			while(produkte.hasMoreElements())
			{
				ProduktModel anzuzeigendesProduktModel = produkte.nextElement();				
				int menge = this.warenkorb.get(anzuzeigendesProduktModel);
				
				double gesamtpreisPosition = menge * anzuzeigendesProduktModel.getPreisBrutto();
				
				BigDecimal bdGesamtpreisPosition = new BigDecimal(gesamtpreisPosition);		
				bdGesamtpreisPosition = bdGesamtpreisPosition.setScale(2,BigDecimal.ROUND_HALF_UP);
				
				this.warenkorbView.outWarenkorbInhalt(anzuzeigendesProduktModel, menge, bdGesamtpreisPosition);
			
				zwischensumme += gesamtpreisPosition;
				gesamtgewicht += anzuzeigendesProduktModel.getGewicht();
			}			
		}
		else
		{
			this.warenkorbView.outLeererWarenkorb();
		}
		
		BigDecimal bdZwischensumme = new BigDecimal(zwischensumme);
		BigDecimal bdGesamtgewicht = new BigDecimal(gesamtgewicht);
		
		bdZwischensumme = bdZwischensumme.setScale(2,BigDecimal.ROUND_HALF_UP);
		bdGesamtgewicht = bdGesamtgewicht.setScale(2,BigDecimal.ROUND_HALF_UP);

		this.warenkorbView.outWarenkorbEnde(bdGesamtgewicht, bdZwischensumme);
	}	
	
	
	@SuppressWarnings("unchecked")
	private void warenkorbAusSessionHolen()
	{
		if(this.session.getAttribute("warenkorb") != null)
		{
			this.warenkorb = (Hashtable<ProduktModel, Integer>) this.session.getAttribute("warenkorb");
		}
		else
		{
			this.warenkorb = new Hashtable<ProduktModel, Integer>();
		}				
	}
	
	private void warenkorbInSessionSchreiben()
	{
		this.session.setAttribute("warenkorb", warenkorb);
	}
	
	
	private void warenkorbAktualisieren(HttpServletRequest request)
	{
		// Wurde Produkt an WK übergeben?
		if(request.getParameter("produkt") != null)
		{
			// Produkt aus DB holen
			int id = Integer.parseInt(request.getParameter("produkt"));			
			ProduktController produktController = new ProduktController(request);
			ProduktModel produktModelAusDatenbank = new ProduktModel();
			produktModelAusDatenbank = produktController.getProdukt(id);
			
			// Liegt Produkt noch nicht im WK?
			Enumeration<ProduktModel> warenkorbInhalt = this.warenkorb.keys();
			if(warenkorbInhalt.hasMoreElements())
			{
				boolean produktNichtImWarenkorb = true;
				
				// WK-Inhalte mit Produkt vergleichen
				while(warenkorbInhalt.hasMoreElements())
				{
					ProduktModel produktImWarenkorb = warenkorbInhalt.nextElement();
					if(produktModelAusDatenbank.getId() == produktImWarenkorb.getId())
					{						
						produktNichtImWarenkorb = false;
					}					
				}
				
				if(produktNichtImWarenkorb)
				{
					this.warenkorb.put(produktModelAusDatenbank, Integer.parseInt(request.getParameter("menge")));
				}				
			}
			else
			{
				//in WK legen wenn WK leer
				this.warenkorb.put(produktModelAusDatenbank, Integer.parseInt(request.getParameter("menge")));
			}
		}
		else
		{					
			// Wurde 'aktualisieren' gewaehlt?
			if(request.getParameter("aktualisieren") != null)
			{
				//TODO remove
				System.out.println("Warenkorb wird aktualisert...");
				
				ProduktModel produktModelImWarenkorb;
				Enumeration<ProduktModel> produktModelsImWarenkorb;
				Enumeration<String> parameters;
				String[] splittedParameter;
				String parameter;
				
				// Menge aktualisieren
				parameters = request.getParameterNames();			
				while (parameters.hasMoreElements())
				{
					parameter = parameters.nextElement();
					String value = request.getParameter(parameter);
					
					if(parameter.startsWith("menge"))
					{							
						splittedParameter = parameter.split("[_]");
					
						produktModelsImWarenkorb = this.warenkorb.keys();
						while(produktModelsImWarenkorb.hasMoreElements())
						{
							produktModelImWarenkorb = produktModelsImWarenkorb.nextElement();
							if(produktModelImWarenkorb.getId() == Integer.parseInt(splittedParameter[1]))
							{
								// entfernen wenn Menge = 0
								if(Integer.parseInt(value) == 0)
								{
									this.warenkorb.remove(produktModelImWarenkorb);
								}
								else
								{
									this.warenkorb.put(produktModelImWarenkorb, Integer.parseInt(value));
								}
							}
						}
					}										
				}
				
				// Bestellpostitionen entfernen
				parameters = request.getParameterNames();
				while (parameters.hasMoreElements())
				{
					parameter = parameters.nextElement();
					
					if(parameter.startsWith("entfernen"))
					{
						splittedParameter = parameter.split("[_]");
						
						produktModelsImWarenkorb = this.warenkorb.keys();
						while(produktModelsImWarenkorb.hasMoreElements())
						{
							produktModelImWarenkorb = produktModelsImWarenkorb.nextElement();
							if(produktModelImWarenkorb.getId() == Integer.parseInt(splittedParameter[1]))
							{
								this.warenkorb.remove(produktModelImWarenkorb);
							}
						}
					}					
				}
			}
			else
			{			
				// Wurde 'leeren' gewaehlt?
				if(request.getParameter("leeren") != null)
				{
					//TODO remove
					System.out.println("Warenkorb wird geleert");
					
					this.warenkorb.clear();						
				}
			}					
		}		
	}
	
	private void warenkorbPreviewAktualisieren()
	{
		
	}
	
}

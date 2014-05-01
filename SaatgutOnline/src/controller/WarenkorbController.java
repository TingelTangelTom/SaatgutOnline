package controller;

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
	
	//TODO remove
	private ProduktModel produktModel;
	private int bestellmenge;
	
	
	// GET-Kostruktor
	public WarenkorbController(HttpServletRequest request, HttpServletResponse response)
	{
		this.session = request.getSession();
		
		//TODO remove			
		this.warenkorbAusSessionHolen();
		this.warenkorbAktualisieren(request);
		if(this.warenkorb.isEmpty()  && request.getParameter("leeren") == null)
		{
			System.out.println("***jap");
			this.produktModel = new ProduktModel();
			this.produktModel.setId(325);
			this.produktModel.setKategorieId(4);
			this.produktModel.setBestand(69);
			this.produktModel.setName("Produkt-Dummy");
			this.produktModel.setBestellnummer("987654321");
			this.produktModel.setPreisNetto(123.45);
			this.produktModel.setPreisBrutto(234.56);
			this.produktModel.setGewicht(9.87);	
			
			bestellmenge = 7;
			warenkorb.put(produktModel, bestellmenge);				
		}
		this.warenkorbInSessionSchreiben();
		
		

//		this.warenkorbAusSessionHolen();
		
		
		
		this.warenkorbView = new WarenkorbView(request, response);
		
		
		
		
	}
	
	// POST-Konstruktor
	public WarenkorbController(HttpServletRequest request, HttpServletResponse response, ProduktModel produktModel, int bestellmenge)
	{
		
		//TODO remove
		this.produktModel = produktModel;		
		this.bestellmenge = bestellmenge;
		
		this.session = request.getSession();
		this.warenkorbAusSessionHolen();		
		this.warenkorb.put(produktModel, bestellmenge);		
		this.warenkorbView = new WarenkorbView(request, response);
		this.warenkorbInSessionSchreiben();
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
				ProduktModel produkt = produkte.nextElement();				
				int menge = this.warenkorb.get(produkt);
				
				double gesamtpreis_position = menge * produkt.getPreisBrutto();
				
				this.warenkorbView.outWarenkorbInhalt(produkt, menge, gesamtpreis_position);
			
				zwischensumme += gesamtpreis_position;
				gesamtgewicht += produkt.getGewicht();
				
				

			}			
		}
		else
		{
			this.warenkorbView.outLeererWarenkorb();
		}

		this.warenkorbView.outWarenkorbEnde(Double.valueOf(Math.round((gesamtgewicht)*100)/100.0), Double.valueOf(Math.round((zwischensumme)*100)/100.0));
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
		
		if(request.getParameter("leeren") != null)
		{
			//TODO remove
			System.out.println("Warenkorb wird geleert");
			
			this.warenkorb.clear();						
		}
		else
		{		
			if(request.getParameter("aktualisieren") != null)
			{
				//TODO remove
				System.out.println("Warenkorb wird aktualisert...");
				
				Enumeration<String> parameters = request.getParameterNames();			
				while (parameters.hasMoreElements())
				{
					String parameter = parameters.nextElement();
					String value = request.getParameter(parameter);
					
					if(parameter.startsWith("menge"))
					{							
						String[] splittedParameter = parameter.split("[_]");
					
						Enumeration<ProduktModel> produkte = this.warenkorb.keys();
						while(produkte.hasMoreElements())
						{
							ProduktModel produkt = produkte.nextElement();
							if(produkt.getId() == Integer.parseInt(splittedParameter[1]))
							{
								this.warenkorb.put(produkt, Integer.parseInt(value));
							}
						}
					}						
				}
			}
		}
	}
	
	

	
	
		
}

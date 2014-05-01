package controller;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.WarenkorbView;
import model.ProduktModel;

public class WarenkorbController
{	
	private HttpSession session;	
	private Hashtable<ProduktModel, Integer> warenkorb;
	private WarenkorbView warenkorbView;
	
	
	
	public WarenkorbController(HttpServletRequest request, HttpServletResponse response)
	{
		this.session = request.getSession();
		this.warenkorbAusSessionHolen();
		this.warenkorbView = new WarenkorbView(request, response);
	}
	
	public WarenkorbController(HttpServletRequest request, HttpServletResponse response, ProduktModel produktModel, int bestellmenge)
	{
		this.session = request.getSession();
		this.warenkorbAusSessionHolen();		
		this.warenkorb.put(produktModel, bestellmenge);		
		this.warenkorbView = new WarenkorbView(request, response);
		this.warenkorbInSessionSchreiben();
	}
	
	
	public void warenkorbAnzeigen()
	{
		this.warenkorbView.outWarenkorbAnfang();
		
		
		//TODO remove	
		ProduktModel produktModel = new ProduktModel();
		produktModel.setId(325);
		produktModel.setKategorieId(4);
		produktModel.setBestand(69);
		produktModel.setName("Produkt-Dummy");
		produktModel.setBestellnummer("987654321");
		produktModel.setPreisNetto(123.45);
		produktModel.setPreisBrutto(234.56);
		produktModel.setGewicht(9.87);	
		int menge = 7;
		double gesamtgewicht = 3.45;
		double gesamtpreis = 9.99;
		double zwischensumme = 59.99;
		
		ProduktModel produktModel2 = new ProduktModel();
		produktModel2.setId(4);
		produktModel2.setKategorieId(9);
		produktModel2.setBestand(69);
		produktModel2.setName("Produkt-Dummy2");
		produktModel2.setBestellnummer("987654321");
		produktModel2.setPreisNetto(123.45);
		produktModel2.setPreisBrutto(234.56);
		produktModel2.setGewicht(9.87);
				
				
		this.warenkorbView.outWarenkorbInhalt(produktModel, menge, gesamtpreis);
		this.warenkorbView.outWarenkorbInhalt(produktModel2, menge, gesamtpreis);

		this.warenkorbView.outWarenkorbEnde(gesamtgewicht, zwischensumme);
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
	
	

	
	
		
}

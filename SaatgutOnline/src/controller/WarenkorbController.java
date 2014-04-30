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
	}
	
	
	public void warenkorbAnzeigen()
	{
		this.warenkorbView.outWarenkorbAnfang();
		
		
		
		this.warenkorbView.outWarenkorbEnde();
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
	
	

	
	
		
}

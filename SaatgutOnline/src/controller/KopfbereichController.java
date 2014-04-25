package controller;

import javax.servlet.http.HttpServletResponse;

import view.KopfbereichView;

public class KopfbereichController
{
	private KopfbereichView kopfbereichView;
	private HttpServletResponse response;
	
	public KopfbereichController(HttpServletResponse response)
	{
		this.response = response;
		this.kopfbereichView = new KopfbereichView(this.response, this);
	}

	
	public void outKopfbereichAnzeigen()
	{	
		// Kopfbereich oeffnen
		kopfbereichView.outKopfbereichAnfang();
		
		// Inhalt des Kopfbereiches ausgeben
		kopfbereichView.outKopfbereichInhalt();
		
		// Kopfbereich schließen
		kopfbereichView.outKopfbereichEnde();		
	}

	
	
	
}

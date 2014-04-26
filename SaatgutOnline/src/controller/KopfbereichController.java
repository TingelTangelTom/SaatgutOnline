package controller;

import javax.servlet.http.HttpServletResponse;

import view.KopfbereichView;

public class KopfbereichController
{
	private KopfbereichView kopfbereichView;
	
	public KopfbereichController(HttpServletResponse response)
	{
		/*
		 * SCHRITT 2:
		 * 
		 * View erzeugen.
		 * Die response wird einfach durchgeschliffen und
		 * direkt an den View-Konstruktor weitergegeben.
		 * 
		 *  --> weiter im KopfbereichView
		 */
		this.kopfbereichView = new KopfbereichView(response);
	}

	/*
	 * SCHRITT 5:
	 * 
	 * Nun die Module zusammenfuegen zur Gesamtausgabe.
	 * 
	 * Hier kommen dann auch die ganzen Switches, ifs und elsen rein.
	 * Der Controller entscheidet, welche Module in das auszugebende Patchwork kommen.
	 * 
	 * -->weiter im KopfbereichServlet
	 */
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

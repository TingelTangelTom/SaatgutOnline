package controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import view.KopfbereichView;

public class KopfbereichController
{
	private KopfbereichView kopfbereichView;
	private Locale locale;
		
	public KopfbereichController(HttpServletRequest request, HttpServletResponse response)
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
		this.kopfbereichView = new KopfbereichView(request, response);
		
		HttpSession session = request.getSession();
		this.locale = (Locale)session.getAttribute("sprache");
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
		this.kopfbereichView.outKopfbereichAnfang();
		this.kopfbereichView.outInhaltsframeAnfang();
		
		this.kopfbereichView.outLogo();
		
		this.kopfbereichView.outInhaltsframeNeueSpalte();
		
		this.kopfbereichView.outSchriftzug();
		
		this.kopfbereichView.outInhaltsframeNeueSpalte();
		
		//TODO LogoutBereich-Verzweigung einfuegen
			this.kopfbereichView.outLoginBereich();
		
		this.kopfbereichView.outInhaltsframeNeueSpalte();
		
		// Sprache ermitteln und entsprechende Sprachwahl anbieten
		if(this.locale.getLanguage() != null && this.locale.getLanguage() == "de")
		{
			this.kopfbereichView.outSprachwahlEn();
		}
		else
		{
			this.kopfbereichView.outSprachwahlDe();
		}
		
		this.kopfbereichView.outInhaltsframeEnde();
		this.kopfbereichView.outKopfbereichEnde();	
	}
	
	
//	/**
//	 * Inhalte des Kopfbereichs
//	 */
//	public void outKopfbereichInhalt()
//	{
//		this.outKopfbereichAnfang();
//		
//				
//		this.outLogo();			
//		out.println("</td>\n<td>");		
//		this.outSchriftzug();		
//		out.println("</td>\n<td>");		
//		this.outLoginBereich();		
//		
//		
//		this.outKopfbereichEnde();
//	}

	
	
	
}

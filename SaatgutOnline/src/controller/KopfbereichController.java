package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.KopfbereichView;

public class KopfbereichController
{
	private KopfbereichView kopfbereichView;	
		
	public KopfbereichController(HttpServletRequest request, HttpServletResponse response)
	{	
		this.kopfbereichView = new KopfbereichView(request, response);
	}

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
		
		this.kopfbereichView.outWarenkorbPreview();
		
		this.kopfbereichView.outInhaltsframeNeueSpalte();
		
		this.kopfbereichView.outSprachwahl();
		
		this.kopfbereichView.outInhaltsframeEnde();
		this.kopfbereichView.outKopfbereichEnde();	
	}


	
	
	
}

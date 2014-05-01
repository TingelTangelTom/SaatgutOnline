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
		
		this.kopfbereichView.outInhaltsRahmenAnfang();
		
		this.kopfbereichView.outLogo();
		
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		
		this.kopfbereichView.outSchriftzug();
		
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		
		//TODO LogoutBereich-Verzweigung einfuegen
			this.kopfbereichView.outLoginBereich();
		
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		
		this.kopfbereichView.outWarenkorbLink();
						
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		
		this.kopfbereichView.outSuchfeld();
		
		this.kopfbereichView.outInhaltsRahmenNeueSpalte();
		
		this.kopfbereichView.outSprachwahl();
		
		this.kopfbereichView.outInhaltsRahmenEnde();
		
		this.kopfbereichView.outKopfbereichEnde();	
	}


	
	
	
}

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.KontaktFormularView;

public class KontaktFormularController {
	
	public KontaktFormularController(HttpServletRequest request, HttpServletResponse response) {
		
		KontaktFormularView kontaktFormularView = new KontaktFormularView(request, response);
		
		kontaktFormularView.outKontaktFormular(request, response);
		
	}

}

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.KasseView;

public class KasseController
{

	KasseView kasseView;
	
	public KasseController(HttpServletRequest request, HttpServletResponse response)
	{
		this.kasseView = new KasseView(request, response);
	}
	
	public void outKasseAnzeigen()
	{
		this.kasseView.outKasseKomplett();
	}
}

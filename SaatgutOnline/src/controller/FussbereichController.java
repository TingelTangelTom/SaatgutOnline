package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.FussbereichView;

public class FussbereichController {
	private FussbereichView fussbereichView;

	public FussbereichController(HttpServletRequest request, HttpServletResponse response) {
		this.fussbereichView = new FussbereichView(request, response);
	}

	public void outFussbereichAnzeigen() {
		this.fussbereichView.outFussbereichAnfang();
		this.fussbereichView.outFussbereichInhalt();
		this.fussbereichView.outFussbereichEnde();
	}

}

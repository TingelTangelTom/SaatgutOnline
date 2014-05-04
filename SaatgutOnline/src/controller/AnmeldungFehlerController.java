package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.AnmeldungFehlerView;
import view.RegistrierungFehlerView;

public class AnmeldungFehlerController {

	public AnmeldungFehlerController (HttpServletRequest request, HttpServletResponse response) {
		AnmeldungFehlerView anmeldungFehlerView = new AnmeldungFehlerView(request, response);
		anmeldungFehlerView.outAnmeldungFehler(request, response);
	}
}

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.RegistrierungFehlerView;

public class RegistrierungFehlerController {

	public RegistrierungFehlerController (HttpServletRequest request, HttpServletResponse response) {
		RegistrierungFehlerView registrierungFehlerView = new RegistrierungFehlerView(request, response);
		registrierungFehlerView.outRegistrierungFehler(request, response);
	}
	
}

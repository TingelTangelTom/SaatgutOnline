package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.RegistrierungErfolgreichView;
import view.RegistrierungView;

public class RegistrierungErfolgreichController {

	public RegistrierungErfolgreichController (HttpServletRequest request, HttpServletResponse response) {
		RegistrierungErfolgreichView registrierungErfoglreichView = new RegistrierungErfolgreichView(request, response);
		registrierungErfoglreichView.outRegistrierungErfolgreich(request, response);
	}
	
	
}

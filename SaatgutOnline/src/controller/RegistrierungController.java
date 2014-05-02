package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.RegistrierungView;
	
public class RegistrierungController {
	
	public RegistrierungController (HttpServletRequest request, HttpServletResponse response) {
		RegistrierungView registrierungView = new RegistrierungView(request, response);
		registrierungView.outResistrierungFormular(request, response);
	}
}

package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UrlController;

public class AGBView {
	
	public AGBView(HttpServletRequest request, HttpServletResponse response, String agbText) {
		
		UrlController urlController = new UrlController(request);
		
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println(agbText);
		out.println("<br><br><a href=\"" + urlController.urlAusSessionHolen("Fussbereich") + "\">&#11013 Zurück</a>");
		//FIXME : Richtigen Link einsetzen
		//TODO : Ich hätte den Link gerne in schwarz :)
	}
}
